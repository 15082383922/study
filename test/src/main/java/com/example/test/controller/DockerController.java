package com.example.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {
    @GetMapping(value = "hello")
    public Object sayHello(){
        return "hello! spring boot with docker.";
    }
}
