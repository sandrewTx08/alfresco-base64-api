package com.sandrewtx08.alfresco.base64.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("redirect:/swagger-ui/index.html");
    }
}
