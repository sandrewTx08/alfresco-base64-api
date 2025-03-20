package com.sandrewtx08.alfresco.base64.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sandrewtx08.alfresco.base64.model.AlfrescoCreateNodeChildBase64Request;
import com.sandrewtx08.alfresco.base64.model.AlfrescoCreateNodeChildRequest;
import com.sandrewtx08.alfresco.base64.service.AlfrescoNodeService;

@RestController
public class AlfrescoNodeController {
    @Autowired
    private AlfrescoNodeService alfrescoNodeService;

    @PostMapping
    public void createNodeChild(@Validated @RequestBody AlfrescoCreateNodeChildBase64Request request) {
        alfrescoNodeService.createNodeChild(new AlfrescoCreateNodeChildRequest(
                Base64.getDecoder().decode(request.getFiledata()),
                request.getName(),
                request.getNodeType(),
                request.getNodeId()));
    }
}
