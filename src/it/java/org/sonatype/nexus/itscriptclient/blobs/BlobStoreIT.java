package org.sonatype.nexus.itscriptclient.blobs;

import org.sonatype.nexus.itscriptclient.script.ScriptRunner;
import org.sonatype.nexus.itscriptclient.testsupport.ScriptITSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class BlobStoreIT
    extends ScriptITSupport
{
  @Test
  public void listBlobs() throws Exception {
    ScriptRunner scriptRunner = new ScriptRunner(nexus);
    BlobStore blobStore = new BlobStore(scriptRunner);

    assertThat(blobStore.getBlobs().getResponse(), containsString("default"));
  }

  @Test
  public void createsBlobStore() throws Exception {
    ScriptRunner scriptRunner = new ScriptRunner(nexus);
    BlobStore blobStore = new BlobStore(scriptRunner);

    blobStore.createBlobstore("fooBar");

    String actual = blobStore.getBlobs().getResponse();
    assertThat(actual, containsString("default"));
    assertThat(actual, containsString("fooBar"));
  }
}
