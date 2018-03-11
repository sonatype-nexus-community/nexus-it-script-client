package org.sonatype.nexus.itscriptclient.script;

import org.sonatype.nexus.itscriptclient.script.org.sonatype.nexus.itscriptclient.test.ScriptTestSupport;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ScriptTest
    extends ScriptTestSupport
{
  @Test
  public void executeScript() throws Exception {
    server.enqueue(new MockResponse().setBody("200"));
    server.enqueue(new MockResponse().setBody("200"));

    String scriptPath = "/v1/script";
    HttpUrl url = server.url(scriptPath);

    String script = "println 'hello world'";
    Script underTest = new Script(url);
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