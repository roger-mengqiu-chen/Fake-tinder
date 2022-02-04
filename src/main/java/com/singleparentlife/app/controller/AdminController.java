package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.AppTermRequest;
import com.singleparentlife.app.payload.request.EventRequest;
import com.singleparentlife.app.payload.request.MessageRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @DeleteMapping("/user/{userId}")
    public JsonResponse deleteUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @PutMapping("/user/suspend/{userId}")
    public JsonResponse suspendUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @GetMapping("/reportedUsers")
    public JsonResponse getReportedUser(){
        //TODO
        return null;
    }

    @DeleteMapping("/reportedUsers/{userId}")
    public JsonResponse deleteReportedUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @PostMapping("/appTerm/")
    public JsonResponse createAppTerms(@RequestBody AppTermRequest request) {
        //TODO
        return null;
    }

    @PutMapping("/appTerm/")
    public JsonResponse updateAppTerms(@RequestBody AppTermRequest request){
        //TODO
        return null;
    }

    @DeleteMapping("/appTerm/{versionId}")
    public JsonResponse deleteAppTerms(@PathVariable Integer versionId) {
        //TODO
        return null;
    }

    public JsonResponse backupDatabase() {
        //TODO
        return null;
    }

    public JsonResponse restoreDatabase() {
        //TODO
        return null;
    }


}
