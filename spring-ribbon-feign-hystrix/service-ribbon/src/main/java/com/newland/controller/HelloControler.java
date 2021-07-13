package com.newland.controller;

import com.newland.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloControler {
    @Autowired
    HelloService helloService;

    @GetMapping(value = "/hi")
    @ResponseBody
    public String hi(@RequestParam String name) {
        return helloService.hiService(name);
    }
}
