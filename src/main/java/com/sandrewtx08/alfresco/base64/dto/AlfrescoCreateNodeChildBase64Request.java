package com.sandrewtx08.alfresco.base64.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AlfrescoCreateNodeChildBase64Request {
    @NotBlank
    private String filedata;

    @NotBlank
    private String name;

    private String nodeType;

    @NotBlank
    private String nodeId;

    private Map<String, Object> properties;
}
