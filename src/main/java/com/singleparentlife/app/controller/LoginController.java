package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.payload.request.LoginRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enter")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<JsonResponse> login(@RequestBody LoginRequest request){
        String token = request.getToken();

        JsonResponse responseOfLogin = userService.login(token);
        if (responseOfLogin.getStatus().equals(Status.SUCCESS)) {
            return ResponseEntity.ok().body(responseOfLogin);
        }
        else {
            return ResponseEntity.status(401).body(responseOfLogin);
        }
    }

}
