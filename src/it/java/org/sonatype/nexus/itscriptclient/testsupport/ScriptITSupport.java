package org.sonatype.nexus.itscriptclient.testsupport;

import org.sonatype.nexus.it.support.NexusContainer;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestName;

public class ScriptITSupport
{
  @Rule
  public TestName name = new TestName();

  @ClassRule
  public static NexusContainer nexus = new NexusContainer();
}
