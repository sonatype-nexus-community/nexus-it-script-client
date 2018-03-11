package org.sonatype.nexus.itscriptclient.script.org.sonatype.nexus.itscriptclient.test;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;

public class ScriptTestSupport
{
  protected static final String POST = "POST";

  protected MockWebServer server;

  @Before
  public void setup() throws Exception {
    server = new MockWebServer();

    server.start();
  }

  @After
  public void tearDown() throws Exception {
    server.shutdown();
  }
}
