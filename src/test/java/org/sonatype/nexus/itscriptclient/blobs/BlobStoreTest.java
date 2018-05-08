package org.sonatype.nexus.itscriptclient.blobs;

import org.sonatype.nexus.itscriptclient.script.ScriptResult;
import org.sonatype.nexus.itscriptclient.script.ScriptRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.itscriptclient.blobs.BlobStore.CREATE_BLOB;

@RunWith(MockitoJUnitRunner.class)
public class BlobStoreTest
{

  private static final String BLOBNAME = "fooBar";

  @Mock
  ScriptRunner scriptRunner;

  @Mock
  ScriptResult scriptResult;

  @Before
  public void setup() throws Exception {
    when(scriptRunner.execute(anyString())).thenReturn(scriptResult);
  }

  @Test
  public void createBlobStore() throws Exception {
    BlobStore blobStore = new BlobStore(scriptRunner);

    blobStore.createBlobstore(BLOBNAME);

    String script = format(CREATE_BLOB, BLOBNAME);

    verify(scriptRunner).execute(script);
  }
}