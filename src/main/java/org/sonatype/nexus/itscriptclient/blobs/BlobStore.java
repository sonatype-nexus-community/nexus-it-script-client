package org.sonatype.nexus.itscriptclient.blobs;

import org.sonatype.nexus.itscriptclient.script.ScriptResult;
import org.sonatype.nexus.itscriptclient.script.ScriptRunner;

public class BlobStore
{
  static final String CREATE_BLOB = "import org.sonatype.nexus.blobstore.api.BlobStore\n" +
      "\nblobName = %s\n" +
      "existingBlobStore = blobStore.getBlobStoreManager().get(blobName)\n" +
      "if (existingBlobStore == null) {\n" +
      "    blobStore.createFileBlobStore(blobName, \"/tmp\")\n" +
      "}";

  static final String LIST_BLOBS = "import org.sonatype.nexus.blobstore.api.BlobStore\n" +
      "List<String> urls = []\n" +
      "blobStore.getBlobStoreManager().browse().each { BlobStore blob ->\n" +
      "    log.info(\"BlobStore: $blob\")\n" +
      "    urls.add(blob.name)\n" +
      "}\n" +
      "return JsonOutput.toJson(urls)";

  private final ScriptRunner scriptRunner;

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
