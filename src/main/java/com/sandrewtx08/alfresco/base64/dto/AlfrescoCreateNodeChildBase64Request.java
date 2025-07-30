package com.sandrewtx08.alfresco.base64.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ByteArrayResource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@Schema(description = "Dados para criação de um nó no Alfresco com conteúdo de arquivo codificado em Base64")
public class AlfrescoCreateNodeChildBase64Request {

    @NotBlank
    @Schema(description = "Conteúdo do arquivo em Base64", example = "U29tZSBCYXNlNjQgY29udGVudA==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filedata;

    @NotBlank
    @Schema(description = "Nome do arquivo", example = "documento.txt", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Tipo do nó a ser criado (opcional)", example = "cm:content")
    private String nodeType;

    @NotBlank
    @Schema(description = "ID do nó pai no Alfresco", example = "-root-", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nodeId;

    @Schema(description = "Propriedades adicionais do nó (opcional)")
    private Map<String, Object> properties;

    @Schema(description = "Lista de tags para adicionar ao nó (opcional)")
    private List<String> tags;

    @Schema(description = "Tags separadas por ponto e vírgula", example = "contrato;2025;sigiloso")
    private String tagsString;

    @Schema(description = "Caminho relativo dentro do nó pai onde o arquivo será salvo", example = "documentos/contratos")
    private String relativePath;

    @Hidden
    public List<String> getTags() {
        if (tags != null) {
            return tags;
        }

        String tagStr = getTagsString();
        if (tagStr == null || tagStr.isBlank()) {
            return Collections.emptyList();
        }

        return Stream.of(tagStr.split(";"))
                .map(tag -> tag.trim().toLowerCase())
                .filter(tag -> !tag.isEmpty())
                .collect(Collectors.toList());
    }

    @Hidden
    public ByteArrayResource getFiledataByteArrayResource() {
        return new ByteArrayResource(Base64.decodeBase64(getFiledata())) {
            @Override
            public String getFilename() {
                return getName() != null && !getName().isBlank()
                        ? getName()
                        : super.getFilename();
            }
        };
    }
}
