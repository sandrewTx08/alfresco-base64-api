package com.sandrewtx08.alfresco.base64.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AlfrescoConfig {
    @Value("${alfresco.api.url}")
    private String alfrescoApiUrl;

    @Value("${alfresco.api.username}")
    private String username;

    @Value("${alfresco.api.password}")
    private String password;
}
