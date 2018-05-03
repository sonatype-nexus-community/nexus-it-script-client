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

import org.sonatype.nexus.itscriptclient.script.org.sonatype.nexus.itscriptclient.test.ScriptRunnerTestSupport;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ScriptRunnerTest
    extends ScriptRunnerTestSupport
{
  @Test
  public void executeScript() throws Exception {
    server.enqueue(new MockResponse().setBody("200"));
    server.enqueue(new MockResponse().setBody("200"));

    String scriptPath = "/service/rest/v1/script";
    server.url(scriptPath);

    String script = "println 'hello world'";
    ScriptRunner underTest = new ScriptRunner(server.getHostName(), server.getPort(), "admin", "admin123");
    ScriptResult scriptResult = underTest.execute(script);

    RecordedRequest addScriptRequest = server.takeRequest(10, MILLISECONDS);

    assertThat(addScriptRequest.getPath(), is(equalTo(scriptPath)));
    assertThat(addScriptRequest.getMethod(), is(equalTo(POST)));

    RecordedRequest runScriptRequest = server.takeRequest(10, MILLISECONDS);
    String runPath = format("%s/%s/run", scriptPath, scriptResult.getScriptName());
    assertThat(runScriptRequest.getPath(), is(equalTo(runPath)));
    assertThat(runScriptRequest.getMethod(), is(equalTo(POST)));
  }
}