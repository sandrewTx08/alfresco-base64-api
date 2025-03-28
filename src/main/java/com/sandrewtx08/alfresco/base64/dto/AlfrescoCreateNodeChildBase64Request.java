package com.sandrewtx08.alfresco.base64.dto;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class AlfrescoCreateNodeChildBase64Request {
    @NonNull
    private String filedata;

    @NotBlank
    private String name;

    private String nodeType;

    @NotBlank
    private String nodeId;

    private Map<String, Object> properties;

    private List<String> tags;

    public ByteArrayResource getFiledataByteArrayResource() {
        return new ByteArrayResource(Base64.getDecoder().decode(getFiledata())) {
            @Override
            public String getFilename() {
                return getName() != null && !getName().isBlank() ? getName() : this.getFilename();
            }
        };
    }
}
