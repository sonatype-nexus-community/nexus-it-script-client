package org.sonatype.nexus.itscriptclient.blobs;

import org.sonatype.nexus.itscriptclient.script.ScriptResult;
import org.sonatype.nexus.itscriptclient.script.ScriptRunner;
import org.sonatype.nexus.itscriptclient.testsupport.ScriptITSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

public class BlobStoreIT
    extends ScriptITSupport
{
  @Test
  public void listBlobs() throws Exception {
    ScriptRunner scriptRunner = new ScriptRunner(nexus);
    BlobStore blobStore = new BlobStore(scriptRunner);

    assertThat(blobStore.getBlobs().getScriptName(), is(not(isEmptyOrNullString())));
  }

  @Test
  public void createsBlobStore() throws Exception {
    ScriptRunner scriptRunner = new ScriptRunner(nexus);
    BlobStore blobStore = new BlobStore(scriptRunner);

    ScriptResult fooBar = blobStore.createBlobstore("fooBar");

    assertThat(blobStore.getBlobs().getScriptName(), is(not(isEmptyOrNullString())));
  }
}
