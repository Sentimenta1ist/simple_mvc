package com.app.RestAppTest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @Controller + @ResponseBody
@RequestMapping("/api")
public class FirstRestController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello world";
    }


}
