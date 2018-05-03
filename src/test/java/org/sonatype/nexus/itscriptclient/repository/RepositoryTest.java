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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.itscriptclient.repository.Repository.GROUP_SCRIPT_PATTERN;
import static org.sonatype.nexus.itscriptclient.repository.Repository.HOSTED_SCRIPT_PATTERN;
import static org.sonatype.nexus.itscriptclient.repository.Repository.PROXY_SCRIPT_PATTERN;
import static org.sonatype.nexus.itscriptclient.repository.Repository.WRITE_POLICY;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest
{
  @Mock
  ScriptRunner scriptRunner;

  @Mock
  ScriptResult scriptResult;

  @Before
  public void setup() throws Exception {
    when(scriptRunner.execute(anyString())).thenReturn(scriptResult);
  }

  @Test
  public void createProxyRepository() throws Exception {
    Repository repository = new Repository(scriptRunner);

    String name = "repo";
    String recipeName = "recipe";
    String remoteUrl = "http://remote.url";
    String blobStoreName = "default";
    boolean strictContentTypeValidation = false;

    ScriptResult scriptResult = repository
        .createProxy(name, recipeName, remoteUrl, blobStoreName, strictContentTypeValidation);

    assertTrue(scriptResult != null);

    String script =
        format(PROXY_SCRIPT_PATTERN, name, recipeName, remoteUrl, blobStoreName, strictContentTypeValidation);

    verify(scriptRunner).execute(script);
  }

  @Test
  public void createHostedRepository() throws Exception {
    Repository repository = new Repository(scriptRunner);

    String name = "repo";
    String recipeName = "recipe";
    String blobStoreName = "default";
    boolean strictContentTypeValidation = false;

    ScriptResult scriptResult = repository
        .createHosted(name, recipeName, blobStoreName, strictContentTypeValidation);

    assertTrue(scriptResult != null);

    String script =
        format(HOSTED_SCRIPT_PATTERN, name, recipeName, blobStoreName, WRITE_POLICY, strictContentTypeValidation);

    verify(scriptRunner).execute(script);
  }

  @Test
  public void createGroupRepository() throws Exception {
    Repository repository = new Repository(scriptRunner);

    String name = "repo";
    String recipeName = "recipe";
    String blobStoreName = "default";

    ScriptResult scriptResult = repository
        .createGroup(name, recipeName, blobStoreName, "member1", "member2");

    assertTrue(scriptResult != null);

    String script =
        format(GROUP_SCRIPT_PATTERN, name, recipeName, blobStoreName, "'member1','member2'");

    verify(scriptRunner).execute(script);
  }
}