package com.sandrewtx08.alfresco.base64.service;

import java.util.HashMap;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandrewtx08.alfresco.base64.AlfrescoConfig;
import com.sandrewtx08.alfresco.base64.dto.AlfrescoCreateNodeChildRequest;

@Service
public class AlfrescoNodeService {
    @Autowired
    private AlfrescoConfig alfrescoConfig;

    public void createNodeChild(AlfrescoCreateNodeChildRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper objectMapper = new ObjectMapper();

        // Create API endpoint URL
        String url = alfrescoConfig.getAlfrescoApiUrl() + "/nodes/" + request.getNodeId() + "/children";

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth(alfrescoConfig.getUsername(), alfrescoConfig.getPassword());

        // Create properties JSON part
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", request.getName());
        if (request.getNodeType() != null) {
            properties.put("nodeType", request.getNodeType());
        }

        HttpEntity<String> propertiesPart = null;
        try {
            propertiesPart = new HttpEntity<>(
                    objectMapper.writeValueAsString(properties),
                    createJsonHeaders());
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Create file part
        HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(
                new ByteArrayResource(request.getFiledata()) {
                    @Override
                    public String getFilename() {
                        return request.getName();
                    }
                },
                createFileHeaders(
                        request.getName()));

        // Build multipart request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("properties", propertiesPart);
        body.add("filedata", filePart);
        body.add("overwrite", true);
        body.add("majorVersion", true);
        body.add("versioningEnabled", true);

        // Create and send request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create node: " + response.getBody());
        }
    }

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
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