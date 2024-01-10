package org.am.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Greeting {

    @GetMapping
    public String greeting() {
        return "Hello world!";
    }
}
