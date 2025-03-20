package com.sandrewtx08.alfresco.base64.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlfrescoCreateNodeChildRequest {
    private byte[] filedata;
    private String name;
    private String nodeType;
    private String nodeId;
}
