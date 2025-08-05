package com.sandrewtx08.alfresco.base64.controller;

import com.sandrewtx08.alfresco.base64.dto.AlfrescoCreateNodeChildBase64Request;
import com.sandrewtx08.alfresco.base64.service.AlfrescoNodeChildService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@RestController
public class AlfrescoNodeChildController {
    @Autowired
    private AlfrescoNodeChildService alfrescoNodeChildService;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("redirect:/swagger-ui/index.html");
    }

    @Operation(summary = "Codifica um arquivo para Base64", description = "Recebe um arquivo no formato multipart/form-data e retorna seu conteúdo codificado em Base64.", responses = {@ApiResponse(responseCode = "200", description = "Retorno com a string Base64", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)), @ApiResponse(responseCode = "400", description = "Arquivo inválido ou ausente")})
    @PostMapping(value = "encode", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String encodeBase64String(@Parameter(description = "Arquivo a ser codificado", required = true, content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(type = "string", format = "binary"))) @RequestParam("file") MultipartFile file) throws IOException {
        return Base64.encodeBase64String(file.getBytes());
    }

    @Operation(summary = "Cria um nó filho no Alfresco com conteúdo em Base64", description = "Cria um nó no Alfresco sob o ID de nó pai especificado, utilizando um arquivo codificado em Base64.", requestBody = @RequestBody(required = true, description = "Dados necessários para criação do nó no Alfresco", content = @Content(schema = @Schema(implementation = AlfrescoCreateNodeChildBase64Request.class))), responses = {@ApiResponse(responseCode = "200", description = "Nó criado com sucesso"), @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"), @ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    @PostMapping
    public Map<String, Object> createNodeChild(@Validated @org.springframework.web.bind.annotation.RequestBody AlfrescoCreateNodeChildBase64Request request) {
        return alfrescoNodeChildService.createNodeChild(request);
    }
}
