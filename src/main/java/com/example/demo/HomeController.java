package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = {"http://localhost:8081","http://localhost:8080"})
@Controller
@RequestMapping("/rest/home")
public class HomeController {
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String hello(){
        return "hello world";
    }

}