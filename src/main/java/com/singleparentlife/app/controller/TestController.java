package com.singleparentlife.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
    //TODO to be removed after test
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping()
    public ResponseEntity<String> testForAuth() {
        return ResponseEntity.ok("Success for auth");
    }

}
