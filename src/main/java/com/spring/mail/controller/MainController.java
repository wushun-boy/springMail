package com.spring.mail.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    @GetMapping("/hello")
    public String hello(){
        System.out.println(1/0);
        return "test";
    }
}
