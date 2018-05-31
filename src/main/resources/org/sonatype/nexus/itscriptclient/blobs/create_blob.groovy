import org.sonatype.nexus.blobstore.api.BlobStore

blobName = "%s"
existingBlobStore = blobStore.getBlobStoreManager().get(blobName)
if (existingBlobStore == null) {
  blobStore.createFileBlobStore(blobName, "/tmp")
}