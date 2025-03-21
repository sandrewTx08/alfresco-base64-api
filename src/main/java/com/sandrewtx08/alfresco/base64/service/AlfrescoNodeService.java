package com.sandrewtx08.alfresco.base64.service;

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

import com.sandrewtx08.alfresco.base64.AlfrescoConfig;
import com.sandrewtx08.alfresco.base64.dto.AlfrescoCreateNodeChildRequest;

@Service
public class AlfrescoNodeService {
    @Autowired
    private AlfrescoConfig alfrescoConfig;

    public void createNodeChild(AlfrescoCreateNodeChildRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        String url = alfrescoConfig.getAlfrescoApiUrl() + "/nodes/" + request.getNodeId() + "/children";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth(alfrescoConfig.getUsername(), alfrescoConfig.getPassword());

        // File part remains the same
        HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(
                new ByteArrayResource(request.getFiledata()) {
                    @Override
                    public String getFilename() {
                        return request.getName();
                    }
                },
                createFileHeaders(request.getName()));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Add standard fields
        body.add("name", request.getName());
        body.add("nodeType", request.getNodeType());
        body.add("filedata", filePart);
        body.add("overwrite", true);

        // Add properties as individual form fields
        request.getProperties().forEach((key, value) -> body.add(key, value.toString()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create node: " + response.getBody());
        }
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
}