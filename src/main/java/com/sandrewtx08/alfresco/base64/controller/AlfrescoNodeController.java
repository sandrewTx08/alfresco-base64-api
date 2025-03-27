package com.sandrewtx08.alfresco.base64.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sandrewtx08.alfresco.base64.dto.AlfrescoCreateNodeChildBase64Request;
import com.sandrewtx08.alfresco.base64.service.AlfrescoNodeService;

@RestController
public class AlfrescoNodeController {
    @Autowired
    private AlfrescoNodeService alfrescoNodeService;

    @PostMapping
    public void createNodeChild(@Validated @RequestBody AlfrescoCreateNodeChildBase64Request request) {
        alfrescoNodeService.createNodeChild(request);
    }
}
