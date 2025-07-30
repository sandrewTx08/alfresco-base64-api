package com.sandrewtx08.alfresco.base64.dto;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AlfrescoCreateNodeChildBase64RequestTest {
    @Test
    void testGetFiledataByteArrayResourceWithVariousFiles() throws Exception {
        String[] testFiles = {
                "src/test/resources/test.rar",
                "src/test/resources/test.zip",
                "src/test/resources/test.txt",
                "src/test/resources/test.pdf",
                "src/test/resources/test.docx"
        };

        for (String filePath : testFiles) {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            String base64 = Base64.encodeBase64String(fileBytes);

            AlfrescoCreateNodeChildBase64Request req = new AlfrescoCreateNodeChildBase64Request(
                    base64,
                    Paths.get(filePath).getFileName().toString(),
                    null,
                    "dummyNodeId",
                    null,
                    null,
                    null,
                    null
            );

            ByteArrayResource resource = req.getFiledataByteArrayResource();
            assertNotNull(resource);
            assertEquals(fileBytes.length, resource.contentLength());
            assertEquals(Paths.get(filePath).getFileName().toString(), resource.getFilename());
        }
    }
}