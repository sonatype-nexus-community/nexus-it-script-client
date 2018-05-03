package org.sonatype.nexus.itscriptclient.realms;

import org.sonatype.nexus.itscriptclient.script.ScriptRunner;

public class Realm
{
  static final String ENABLE_REALM =
      "import org.sonatype.nexus.security.realm.RealmManager" +
      "\n" +
      "\nrealmManager = container.lookup(RealmManager.class.getName())" +
      "\nrealmManager.enableRealm('%s', %b)";

  private final ScriptRunner scriptRunner;

  public Realm(final ScriptRunner scriptRunner) {
    this.scriptRunner = scriptRunner;
  }

  public void enable(final String realmName, final boolean enable) {
    scriptRunner.execute(String.format(ENABLE_REALM, realmName, enable));
  }
}
