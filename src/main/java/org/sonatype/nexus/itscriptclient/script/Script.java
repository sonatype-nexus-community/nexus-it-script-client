package org.sonatype.nexus.itscriptclient.script;

import java.io.IOException;
import java.util.UUID;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONObject;

import static java.lang.String.format;

public class Script
{
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private final HttpUrl url;

  public Script(final HttpUrl url) {
    this.url = url;
  }

  public ScriptResult execute(final String script) {
    OkHttpClient client = new OkHttpClient();

    String scriptName = addScript(script, client);

    runScript(scriptName, client);

    return new ScriptResult(scriptName);
  }

  private String addScript(final String script, final OkHttpClient client) {
    String scriptName = UUID.randomUUID().toString();

    JSONObject json = new JSONObject();
    json.put("name", scriptName);
    json.put("content", script);
    json.put("type", "groovy");

    RequestBody body = RequestBody.create(JSON, json.toJSONString());
    Request request = new Request.Builder()
        .url(url)
        .post(body)
        .build();

    try {
      Response response = client.newCall(request).execute();
      response.close();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    return scriptName;
  }

  private void runScript(final String scriptName, final OkHttpClient client) {
    String runPath = format("%s/%s/run", url, scriptName);

    RequestBody body = RequestBody.create(JSON, "");
    Request request = new Request.Builder()
        .url(runPath)
        .post(body)
        .build();

    try {
      Response response = client.newCall(request).execute();
      response.close();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
