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
package org.sonatype.nexus.itscriptclient.repository;

import org.sonatype.nexus.itscriptclient.script.ScriptResult;
import org.sonatype.nexus.itscriptclient.script.ScriptRunner;
import org.sonatype.nexus.itscriptclient.testsupport.ScriptITSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

public class RepositoryIT
    extends ScriptITSupport
{
  @Test
  public void createRepositories() throws Exception {
    ScriptRunner scriptRunner = new ScriptRunner(nexus);
    Repository repository = new Repository(scriptRunner);

    String hostedName = name.getMethodName() + "-hosted";
    String proxyName = name.getMethodName() + "-proxy";
    String groupName = name.getMethodName() + "-group";

    ScriptResult hosted = repository.createHosted(hostedName, "raw-hosted", "default", false);
    assertThat(hosted.getScriptName(), is(not(isEmptyOrNullString())));

    ScriptResult proxy = repository.createProxy(proxyName, "raw-proxy", "http://example:8080", "default", false);
    assertThat(proxy.getScriptName(), is(not(isEmptyOrNullString())));

    ScriptResult group = repository.createGroup(groupName, "raw-group", "default", hostedName, proxyName);
    assertThat(group.getScriptName(), is(not(isEmptyOrNullString())));
  }
}
