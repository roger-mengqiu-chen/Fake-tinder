package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.UserRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping()
    public JsonResponse createUser(@RequestBody UserRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/{userId}")
    public JsonResponse getUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @PutMapping()
    public JsonResponse updateUser(@RequestBody UserRequest request) {
        //TODO
        return null;
    }

    @PostMapping("/upgrade")
    public JsonResponse upgradeUser(){
        //TODO
        return null;
    }

    @DeleteMapping()
    public JsonResponse DeleteUser() {
        //TODO
        return null;
    }



}
