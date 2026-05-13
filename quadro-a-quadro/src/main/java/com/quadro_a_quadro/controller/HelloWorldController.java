package com.quadro_a_quadro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {
    //GET /hello-world
    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }
}
