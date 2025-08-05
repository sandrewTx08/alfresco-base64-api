package com.sandrewtx08.alfresco.base64.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public Object createTag(String nodeId, List<String> tags) {
        if (nodeId == null || nodeId.isBlank()) {
            throw new IllegalArgumentException("Node ID must not be null or blank");
        }

        if (tags == null || tags.isEmpty()) {
            return null;
        }

        List<AlfrescoCreateTagRequest> tagBody = tags.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(tag -> !tag.isEmpty())
                .map(AlfrescoCreateTagRequest::new)
                .collect(Collectors.toList());

        if (tagBody.isEmpty()) {
            return null;
        }

        String tagsEndpoint = "/nodes/" + nodeId + "/tags";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(tagBody, headers);

        return restTemplate.exchange(
                tagsEndpoint,
                HttpMethod.POST,
                requestEntity,
                Object.class);
    }
}
