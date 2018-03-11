package org.sonatype.nexus.itscriptclient.script;

public class ScriptResult
{
  private final String scriptName;

  public ScriptResult(final String scriptName) {
    this.scriptName = scriptName;
  }

  public String getScriptName() {
    return scriptName;
  }
}
