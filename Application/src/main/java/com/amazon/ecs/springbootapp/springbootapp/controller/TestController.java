package com.amazon.ecs.springbootapp.springbootapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(path = "/test")
    public ResponseEntity<String> testMethod() {
        return ResponseEntity.ok("great, the spring boot app is deployed on ecs fargate!");
    }
}
