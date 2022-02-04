package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.TagRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {
    @ApiOperation(value = "Create tags from a list")
    @PostMapping("/myTags")
    public ResponseEntity<JsonResponse> addTags(@RequestBody TagRequest request) {
        //TODO
        return null;
    }
    @GetMapping("/myTags")
    public ResponseEntity<JsonResponse> getTags() {
        //TODO
        return null;
    }

    @DeleteMapping("/myTags")
    public ResponseEntity<JsonResponse> deleteTag(@RequestBody TagRequest request) {
        //TODO
        return null;
    }

    @PostMapping("/myPreferences")
    public ResponseEntity<JsonResponse> addPreference(@RequestBody TagRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/myPreferences")
    public ResponseEntity<JsonResponse> getPreferences() {
        //TODO
        return null;
    }

    @DeleteMapping("/myPreferences")
    public ResponseEntity<JsonResponse> deletePreference(@RequestBody TagRequest request) {
        //TODO
        return null;
    }
}
