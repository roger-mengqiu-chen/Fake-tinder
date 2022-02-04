package com.singleparentlife.app.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.singleparentlife.app.config.security.UserDetailsServiceImp;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.User;
import com.singleparentlife.app.payload.request.LoginRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/enter")
public class LoginController {

    @Autowired
    UserDetailsServiceImp userDetailsServiceImp;
    @Autowired
    UserService userService;
    @PostMapping
    public ResponseEntity<JsonResponse> login(@RequestBody LoginRequest request){
        String token = request.getToken();

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            if (decodedToken != null) {
                String uid = decodedToken.getUid();
                UserDetails user = userDetailsServiceImp.loadUserByUsername(token);

                if (user == null) {
                    User newUser = new User();
                    newUser.setFireId(uid);
                    newUser.setLoginTime(LocalDateTime.now());
                    newUser.setStartDate(LocalDate.now());
                    newUser.setRoleId(2);
                    newUser.setActive(true);
                    newUser.setSuspended(false);
                    userService.saveUser(newUser);
                    return ResponseEntity.ok(new JsonResponse(Status.SUCCESS, "User registered !"));
                }
                else {
                    return ResponseEntity.ok(new JsonResponse(Status.SUCCESS, "User login !"));
                }

            } else {
                return ResponseEntity.status(401).body(new JsonResponse(Status.FETCH_TOKEN_FAILED));
            }
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().body(new JsonResponse(Status.FAIL, "Invalid token !"));
        }
    }

}
