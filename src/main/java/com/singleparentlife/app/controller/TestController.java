package com.singleparentlife.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
    //TODO to be removed after test
 */
@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping()
    public ResponseEntity<String> testForAuth() {
        return ResponseEntity.ok("Success for auth");
    }

}
