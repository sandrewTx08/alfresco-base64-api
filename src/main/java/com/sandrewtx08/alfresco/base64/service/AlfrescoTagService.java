package com.sandrewtx08.alfresco.base64.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sandrewtx08.alfresco.base64.dto.AlfrescoCreateTagRequest;

@Service
public class AlfrescoTagService {
    @Autowired
    private RestTemplate restTemplate;

    public void createTag(String nodeId, List<String> tags) {
        String tagsEndpoint = "/nodes/" + nodeId + "/tags";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<AlfrescoCreateTagRequest> tagBody = new ArrayList<>();

        // Create and add each tag
        for (String tag : tags) {
            tagBody.add(new AlfrescoCreateTagRequest(tag));

        }

        HttpEntity<Object> requestEntity = new HttpEntity<>(tagBody, headers);

        restTemplate.exchange(
                tagsEndpoint,
                HttpMethod.POST,
                requestEntity,
                Object.class);
    }
}
