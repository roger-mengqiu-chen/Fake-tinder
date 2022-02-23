package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.UserRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{userId}")
    public ResponseEntity<JsonResponse> getUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateUser(@RequestBody UserRequest request) {
        //TODO
        return null;
    }

    @PostMapping("/upgrade")
    public ResponseEntity<JsonResponse> upgradeUser(){
        //TODO
        return null;
    }

    @DeleteMapping()
    public ResponseEntity<JsonResponse> DeleteUser() {
        //TODO
        return null;
    }



}
