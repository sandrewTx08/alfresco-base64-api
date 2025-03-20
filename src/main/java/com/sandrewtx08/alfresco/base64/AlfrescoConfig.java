package com.sandrewtx08.alfresco.base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AlfrescoConfig {
    @Value("${alfresco.api.url:http://localhost/alfresco/api/-default-/public/alfresco/versions/1}")
    private String alfrescoApiUrl;

    @Value("${alfresco.api.username:admin}")
    private String username;

    @Value("${alfresco.api.password:admin}")
    private String password;
}
