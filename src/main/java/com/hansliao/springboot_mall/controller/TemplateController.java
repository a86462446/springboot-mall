package com.hansliao.springboot_mall.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

@RestController
public class TemplateController {
    
    // 主頁面
    @GetMapping("/index")
    public String showIndex() {
        PebbleEngine engine= new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate= engine.getTemplate("templates/index.html");
        Writer writer = new StringWriter();

        Map<String, Object> context = new HashMap<>();

        try {
            compiledTemplate.evaluate(writer, context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String output = writer.toString();
        return output;
    }

    // 登入頁面
    @GetMapping("/users/login")
    public String showLoginForm(){
        PebbleEngine engine= new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate= engine.getTemplate("templates/login.html");
        Writer writer = new StringWriter();

        Map<String, Object> context = new HashMap<>();

        try {
            compiledTemplate.evaluate(writer, context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String output = writer.toString();
        return output;
    }
}
