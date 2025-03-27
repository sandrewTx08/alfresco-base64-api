package com.sandrewtx08.alfresco.base64.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class UtilsController {
    @GetMapping("randomUUID")
    public String getRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
