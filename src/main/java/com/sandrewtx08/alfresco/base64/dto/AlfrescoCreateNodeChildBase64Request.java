package com.sandrewtx08.alfresco.base64.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
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
