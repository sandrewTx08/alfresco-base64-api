package com.sandrewtx08.alfresco.base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class AlfrescoConfig {
    @Value("${alfresco.api")
    private String alfrescoApiUrl;

    @Value("${alfresco.api.username}")
    private String username;

    @Value("${alfresco.api.password}")
    private String password;
}
