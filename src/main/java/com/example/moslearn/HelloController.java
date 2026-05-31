package com.example.moslearn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/index")
    public String home() {
        return "Spring Boot demo is running.";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "我是许嵩嵩") String name) {
        return "Hello, " + name + "!";
    }
}
