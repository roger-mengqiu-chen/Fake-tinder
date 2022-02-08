package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/message")
    public ResponseEntity<JsonResponse> uploadWithMessage(@RequestBody MultipartFile file) {
        //TODO
        return null;
    }

    @PostMapping("/profile")
    public ResponseEntity<JsonResponse> uploadWithProfile(@RequestBody MultipartFile file) {
        //TODO
        return null;
    }
}
