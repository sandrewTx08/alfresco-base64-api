package com.sandrewtx08.alfresco.base64.model;

import lombok.Data;

@Data
public class AlfrescoCreateNodeChildBase64Request {
    private String filedata;
    private String name;
    private String nodeType;
    private String nodeId;
}
