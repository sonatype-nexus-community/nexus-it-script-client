package org.sonatype.nexus.itscriptclient.realms;

import java.io.IOException;

import org.sonatype.nexus.itscriptclient.script.ScriptRunner;

import org.testcontainers.shaded.com.google.common.io.Resources;

import static java.nio.charset.Charset.defaultCharset;

public class Realm
{
  static final String ENABLE_REALM;

  private final ScriptRunner scriptRunner;

  static {
    try {
      ENABLE_REALM = Resources.toString(Realm.class.getResource("enable_realm.groovy"), defaultCharset());
    }
    catch (IOException e) {
      throw new RuntimeException("Unable to read in resource");
    }
  }

  public Realm(final ScriptRunner scriptRunner) {
    this.scriptRunner = scriptRunner;
  }

  public void enable(final String realmName, final boolean enable) {
    scriptRunner.execute(String.format(ENABLE_REALM, realmName, enable));
  }
}
