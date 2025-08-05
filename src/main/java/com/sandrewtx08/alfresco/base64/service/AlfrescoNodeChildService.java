package com.sandrewtx08.alfresco.base64.service;

import com.sandrewtx08.alfresco.base64.dto.AlfrescoCreateNodeChildBase64Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlfrescoNodeChildService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AlfrescoTagService alfrescoTagService;

    public Map<String, Object> createNodeChild(AlfrescoCreateNodeChildBase64Request request) {
        String url = "/nodes/" + request.getNodeId() + "/children";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String safeFilename = request.getName() != null && !request.getName().isBlank()
                ? request.getName()
                : "file";

        HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(
                request.getFiledataByteArrayResource(),
                createFileHeaders(safeFilename));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        if (request.getNodeType() != null && !request.getNodeType().isBlank()) {
            body.add("nodeType", request.getNodeType());
        }

        if (request.getRelativePath() != null && !request.getRelativePath().isBlank()) {
            body.add("relativePath", request.getRelativePath());
        }

        body.add("filedata", filePart);
        body.add("overwrite", true);

        if (request.getProperties() != null) {
            request.getProperties().forEach((key, value) -> {
                if (value != null) {
                    body.add(key, extractPropertyValue(value.toString()));
                }
            });
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Object.class);

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        Map<String, Object> entry = responseBody != null
                ? (Map<String, Object>) responseBody.getOrDefault("entry", new HashMap<>())
                : new HashMap<>();

        String nodeId = (String) entry.getOrDefault("id", "");

        Map<String, Object> result = new HashMap<>();
        result.put("node", response.getBody());

        List<String> tags = request.getTags();

        if (tags != null && !tags.isEmpty()) {
            result.put("tags", alfrescoTagService.createTag(nodeId, tags));
        }

        return result;
    }

    private HttpHeaders createFileHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .builder("form-data")
                .name("filedata")
                .filename(filename)
                .build());
        return headers;
    }

    private String extractPropertyValue(String value) {
        int startIndex = -1;

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '[') {
                startIndex = i;
            } else if (c == ']' && startIndex != -1) {
                return value.substring(startIndex + 1, i);
            }
        }

        return value;
    }
}
