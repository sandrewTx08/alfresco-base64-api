package com.sandrewtx08.alfresco.base64.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AlfrescoCreateNodeChildBase64Request {
    @NotBlank
    private String filedata;

    @NotBlank
    private String name;

    @NotBlank
    private String nodeType;

    @NotBlank
    private String nodeId;
}
