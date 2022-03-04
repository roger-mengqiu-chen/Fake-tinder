package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/{deviceToken}")
    public JsonResponse logout (@PathVariable String deviceToken) {
        Long userId = authUtil.getCurrentUserId();
        return userService.logout(userId, deviceToken);
    }

}
