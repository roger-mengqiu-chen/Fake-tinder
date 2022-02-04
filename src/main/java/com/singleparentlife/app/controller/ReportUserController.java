package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.ReportUserRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportUser")
public class ReportUserController {

    @PostMapping()
    public JsonResponse reportUser(@RequestBody ReportUserRequest request) {
        //TODO
        return null;
    }


}
