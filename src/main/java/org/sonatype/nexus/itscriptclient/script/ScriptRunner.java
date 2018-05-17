/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2018-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.itscriptclient.script;

import java.io.IOException;
import java.util.UUID;

import org.sonatype.nexus.it.support.NexusContainer;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONObject;

import static java.lang.String.format;
import static okhttp3.Credentials.basic;

public class ScriptRunner
{
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private static final MediaType TEXT = MediaType.parse("text/plain");

  private final HttpUrl url;

  private final String credentialsHeader;

  public ScriptRunner(final NexusContainer nexus) {
    this(nexus.getContainerIpAddress(), nexus.getPort(), nexus.getUsername(), nexus.getPassword());
  }
  
  public ScriptRunner(final String host, final int port, final String username, final String password) {
    this.credentialsHeader = basic(username, password);
    
    this.url = new HttpUrl.Builder()
        .scheme("http")
        .host(host)
        .port(port)
        .addPathSegment("service")
        .addPathSegment("rest")
        .addPathSegment("v1")
        .addPathSegment("script")
        .build();
  }

  public ScriptResult execute(final String script) {
    OkHttpClient client = new OkHttpClient.Builder().build();

    String scriptName = addScript(script, client);

    String response = runScript(scriptName, client);

    return new ScriptResult(scriptName, response);
  }

  private String addScript(final String script, final OkHttpClient client) {
    String scriptName = UUID.randomUUID().toString();

    JSONObject json = new JSONObject();
    json.put("name", scriptName);
    json.put("content", script);
    json.put("type", "groovy");

    RequestBody body = RequestBody.create(JSON, json.toJSONString());
    Request request = new Request.Builder()
        .header("Authorization", credentialsHeader)
        .url(url)
        .post(body)
        .build();

    executeCall(client, request);
    return scriptName;
  }

  private String runScript(final String scriptName, final OkHttpClient client) {
    String runPath = format("%s/%s/run", url, scriptName);

    RequestBody body = RequestBody.create(TEXT, "");
    Request request = new Request.Builder()
        .header("Authorization", credentialsHeader)
        .url(runPath)
        .post(body)
        .build();

    return executeCall(client, request);
  }

  private String executeCall(final OkHttpClient client, final Request request) {
    try {
      Response response = client.newCall(request)
          .execute();
      String responseMessage = response.body().string();
      response.close();
      validateResponse(response);
      return responseMessage;
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void validateResponse(final Response response) {
    if (!response.isSuccessful()) {
      throw new RuntimeException("Invalid response " + response);
    }
  }

}
