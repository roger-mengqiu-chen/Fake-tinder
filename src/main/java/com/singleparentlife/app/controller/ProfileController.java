package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.ProfileRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @PostMapping
    public JsonResponse createProfile(@RequestBody ProfileRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/{userId}")
    public JsonResponse getProfile(@PathVariable Long userId) {
        // TODO
        return null;
    }

    @PutMapping()
    public JsonResponse updateProfile(@RequestBody ProfileRequest request) {
        // TODO
        return null;
    }

    @DeleteMapping("/{userId}")
    public JsonResponse deleteProfile(@PathVariable Long userId) {
        // TODO
        return null;
    }

    @PostMapping("/react/{userId}")
    public JsonResponse reactToProfile(@PathVariable Long userId) {
        //TODO
        return null;
    }
}
