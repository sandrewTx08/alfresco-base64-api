package com.sandrewtx08.alfresco.base64.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Autowired
    private AlfrescoConfig alfrescoConfig;

    @Value("${swagger.server.url}")
    private String swaggerServerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        var openApi = new OpenAPI()
                .info(new Info()
                        .title("API de Integração com Alfresco via Base64")
                        .version("1.0")
                        .description("Esta API permite o envio de arquivos codificados em Base64 para o repositório Alfresco.\nUsuário configurado: "
                                + alfrescoConfig.getUsername())
                );

        if (swaggerServerUrl != null && !swaggerServerUrl.isBlank()) {
            openApi.servers(List.of(
                    new Server().url(swaggerServerUrl).description("Servidor da API")
            ));
        }

        return openApi;
    }
}
