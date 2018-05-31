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

import java.io.IOException;

import org.sonatype.nexus.itscriptclient.script.ScriptResult;
import org.sonatype.nexus.itscriptclient.script.ScriptRunner;

import org.testcontainers.shaded.com.google.common.io.Resources;

import static java.lang.String.format;
import static java.nio.charset.Charset.defaultCharset;

/**
 * Scripts for interacting with to RepositoryApi 
 */
public class Repository
{
  static final String PROXY_SCRIPT_PATTERN;

  static final String HOSTED_SCRIPT_PATTERN;

  static final String GROUP_SCRIPT_PATTERN;

  static final String WRITE_POLICY = "WritePolicy.ALLOW";

  private final ScriptRunner scriptRunner;

  static {
    try {
      PROXY_SCRIPT_PATTERN = Resources.toString(Repository.class.getResource("create_proxy.groovy"), defaultCharset());
      HOSTED_SCRIPT_PATTERN = Resources.toString(Repository.class.getResource("create_hosted.groovy"), defaultCharset());
      GROUP_SCRIPT_PATTERN = Resources.toString(Repository.class.getResource("create_group.groovy"), defaultCharset());
    }
    catch (IOException e) {
      throw new RuntimeException("Unable to read in resource");
    }
  }

  public Repository(final ScriptRunner scriptRunner) {
    this.scriptRunner = scriptRunner;
  }

  public ScriptResult createProxy(final String name,
                                  final String recipeName,
                                  final String remoteUrl,
                                  final String blobStoreName,
                                  final boolean strictContentTypeValidation)
  {
    String script =
        format(PROXY_SCRIPT_PATTERN, name, recipeName, remoteUrl, blobStoreName, strictContentTypeValidation);

    return scriptRunner.execute(script);
  }

  public ScriptResult createHosted(final String name,
                                   final String recipeName,
                                   final String blobStoreName,
                                   final boolean strictContentTypeValidation)
  {
    String script =
        format(HOSTED_SCRIPT_PATTERN, name, recipeName, blobStoreName, WRITE_POLICY, strictContentTypeValidation);

    return scriptRunner.execute(script);
  }
  
  public ScriptResult createGroup(final String name,
                                  final String recipeName,
                                  final String blobStoreName,
                                  final String... members) 
  {
   StringBuilder membersString = new StringBuilder();

    for (int i = 0; i < members.length; i++) {
      membersString.append("'").append(members[i]).append("'");
      if (i < members.length - 1) {
        membersString.append(",");
      }
    }
    
    String script =
        format(GROUP_SCRIPT_PATTERN, name, recipeName, blobStoreName, membersString.toString());

    return scriptRunner.execute(script);
  }
}
