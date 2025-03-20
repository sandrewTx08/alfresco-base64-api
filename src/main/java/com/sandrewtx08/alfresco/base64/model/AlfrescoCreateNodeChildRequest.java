package com.sandrewtx08.alfresco.base64.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
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
}
