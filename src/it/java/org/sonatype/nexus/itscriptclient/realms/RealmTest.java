package org.sonatype.nexus.itscriptclient.realms;


import org.sonatype.nexus.itscriptclient.script.ScriptResult;
import org.sonatype.nexus.itscriptclient.script.ScriptRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.lang.String.format;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.itscriptclient.realms.Realm.ENABLE_REALM;

@RunWith(MockitoJUnitRunner.class)
public class RealmTest
{
  @Mock
  ScriptRunner scriptRunner;

  @Mock
  ScriptResult scriptResult;

  @Before
  public void setUp() throws Exception {
    when(scriptRunner.execute(anyString())).thenReturn(scriptResult);
  }

  @Test
  public void enableRealm() throws Exception {
    Realm realm = new Realm(scriptRunner);

    String realmName = "realmName";
    realm.enable(realmName, true);

    String script = format(ENABLE_REALM, realmName, true);

    verify(scriptRunner).execute(script);
  }
}