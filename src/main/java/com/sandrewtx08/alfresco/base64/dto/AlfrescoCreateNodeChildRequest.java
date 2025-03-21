package com.sandrewtx08.alfresco.base64.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class AlfrescoCreateNodeChildRequest {
    @NonNull
    private byte[] filedata;

    @NotBlank
    private String name;

    @NotBlank
    private String nodeType;

    @NotBlank
    private String nodeId;

    private Map<String, Object> properties;
}
