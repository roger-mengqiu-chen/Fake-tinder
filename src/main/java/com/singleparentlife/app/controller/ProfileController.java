package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.ProfileRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @PostMapping
    public ResponseEntity<JsonResponse> createProfile(@RequestBody ProfileRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<JsonResponse> getProfile(@PathVariable Long userId) {
        // TODO
        return null;
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateProfile(@RequestBody ProfileRequest request) {
        // TODO
        return null;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<JsonResponse> deleteProfile(@PathVariable Long userId) {
        // TODO
        return null;
    }

    @PostMapping("/react/{userId}")
    public ResponseEntity<JsonResponse> reactToProfile(@PathVariable Long userId) {
        //TODO
        return null;
    }
}
