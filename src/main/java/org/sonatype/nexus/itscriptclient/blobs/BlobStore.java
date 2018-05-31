package org.sonatype.nexus.itscriptclient.blobs;

import java.io.IOException;

import org.sonatype.nexus.itscriptclient.script.ScriptResult;
import org.sonatype.nexus.itscriptclient.script.ScriptRunner;

import org.testcontainers.shaded.com.google.common.io.Resources;

import static java.nio.charset.Charset.defaultCharset;

public class BlobStore
{
  static final String CREATE_BLOB;

  static final String LIST_BLOBS;

  final ScriptRunner scriptRunner;

  static {
    try {
      CREATE_BLOB = Resources.toString(BlobStore.class.getResource("create_blob.groovy"), defaultCharset());
      LIST_BLOBS = Resources.toString(BlobStore.class.getResource("list_blobs.groovy"), defaultCharset());
    }
    catch (IOException e) {
      throw new RuntimeException("Unable to read in resource");
    }
  }

  public BlobStore(final ScriptRunner scriptRunner) {
    this.scriptRunner = scriptRunner;
  }

  public ScriptResult createBlobstore(final String name) {
    String blob = String.format(CREATE_BLOB, name);

    return scriptRunner.execute(blob);
  }

  public ScriptResult getBlobs() {
    return scriptRunner.execute(LIST_BLOBS);
  }
}
