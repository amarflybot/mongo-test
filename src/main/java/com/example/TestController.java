package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by amarendra on 26/1/17.
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello";
    }
}
