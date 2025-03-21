package com.sandrewtx08.alfresco.base64.dto;

import java.util.Map;

import org.springframework.core.io.ByteArrayResource;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class AlfrescoCreateNodeChildRequest {
    @NonNull
    private ByteArrayResource filedata;

    @NotBlank
    private String name;

    private String nodeType;

    @NotBlank
    private String nodeId;

    private Map<String, Object> properties;
}
