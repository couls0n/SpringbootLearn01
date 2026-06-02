package com.example.moslearn.controller;

import com.example.moslearn.dto.MessageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/index")
    public MessageResponse home() {
        return new MessageResponse("Spring Boot demo is running.");
    }

    @GetMapping("/hello")
    public MessageResponse hello(@RequestParam(defaultValue = "Spring Boot") String name) {
        return new MessageResponse("Hello, " + name + "!");
    }
}
