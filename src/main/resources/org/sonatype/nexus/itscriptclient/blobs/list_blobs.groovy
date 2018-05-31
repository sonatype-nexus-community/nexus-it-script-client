import org.sonatype.nexus.blobstore.api.BlobStore
import groovy.json.JsonOutput

List<String> urls = []
blobStore.getBlobStoreManager().browse().each { BlobStore blob ->
  urls.add(blob.blobStoreConfiguration.name)
}

def result = JsonOutput.toJson([
    blobs  : urls
])
return result