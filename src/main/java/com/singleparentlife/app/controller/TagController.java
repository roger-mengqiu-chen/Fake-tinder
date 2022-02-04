package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.TagRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @PostMapping("/myTags")
    public JsonResponse addTags(@RequestBody TagRequest request) {
        //TODO
        return null;
    }
    @GetMapping("/myTags")
    public JsonResponse getTags() {
        //TODO
        return null;
    }

    @DeleteMapping("/myTags")
    public JsonResponse deleteTag(@RequestBody TagRequest request) {
        //TODO
        return null;
    }

    @PostMapping("/myPreferences")
    public JsonResponse addPreference(@RequestBody TagRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/myPreferences")
    public JsonResponse getPreferences() {
        //TODO
        return null;
    }

    @DeleteMapping("/myPreferences")
    public JsonResponse deletePreference(@RequestBody TagRequest request) {
        //TODO
        return null;
    }
}
