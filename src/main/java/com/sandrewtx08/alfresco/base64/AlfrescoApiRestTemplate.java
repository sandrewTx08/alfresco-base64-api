package com.sandrewtx08.alfresco.base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class AlfrescoApiRestTemplate {
    @Autowired
    private AlfrescoConfig alfrescoConfig;

    private RestTemplate restTemplate = new RestTemplate();

    @Bean
    public RestTemplate restTemplate() {
        restTemplate.getInterceptors()
                .add(new BasicAuthenticationInterceptor(alfrescoConfig.getUsername(), alfrescoConfig.getPassword()));
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(alfrescoConfig.getAlfrescoApiUrl()));
        return restTemplate;
    }
}
