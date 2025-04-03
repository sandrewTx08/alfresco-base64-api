package com.sandrewtx08.alfresco.base64.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sandrewtx08.alfresco.base64.dto.AlfrescoCreateNodeChildBase64Request;

@Service
public class AlfrescoNodeService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AlfrescoTagService alfrescoTagService;

    public void createNodeChild(AlfrescoCreateNodeChildBase64Request request) {
        String url = "/nodes/" + request.getNodeId() + "/children";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // File part remains the same
        HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(
                request.getFiledataByteArrayResource(),
                createFileHeaders(request.getName()));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Add standard fields
        if (request.getNodeType() != null && !request.getNodeType().isBlank())
            body.add("nodeType", request.getNodeType());
        if (request.getRelativePath() != null && !request.getRelativePath().isBlank())
            body.add("relativePath", request.getRelativePath());
        body.add("filedata", filePart);
        body.add("overwrite", true);

        if (request.getProperties() != null)
            request.getProperties().forEach((key, value) -> body.add(key, extractPropertieValue(value.toString())));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Object.class);

        Map<String, Object> entry = (Map<String, Object>) ((Map<String, Object>) response.getBody()).get("entry");
        String nodeId = (String) entry.get("id");

        if (request.getTags() != null && request.getTags().size() > 0)
            alfrescoTagService.createTag(nodeId, request.getTags());
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

    private String extractPropertieValue(String value) {
        int startIndex = value.indexOf('[');
        int endIndex = value.indexOf(']');

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex)
            return value.substring(startIndex + 1, endIndex);

        return value;
    }
}
