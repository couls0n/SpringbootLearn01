package com.example.moslearn.controller;

import com.example.moslearn.service.RedisDemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
//@RequestMapping("/api/redis")
public class RedisDemoController {
    public final RedisDemoService redisDemoService;
    public RedisDemoController(RedisDemoService redisDemoService) {
        this.redisDemoService = redisDemoService;
    }


    @GetMapping("/api/redis/demo")
    public void hello() {
        redisDemoService.demo();
    }

    @GetMapping("/api/redis/demo2")
    public String demo2() {
        return redisDemoService.demo2();
    }

}
