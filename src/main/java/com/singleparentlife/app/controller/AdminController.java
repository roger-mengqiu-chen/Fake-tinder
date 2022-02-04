package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.AppTermRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<JsonResponse> deleteUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @PutMapping("/user/suspend/{userId}")
    public ResponseEntity<JsonResponse> suspendUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @GetMapping("/reportedUsers")
    public ResponseEntity<JsonResponse> getReportedUser(){
        //TODO
        return null;
    }

    @DeleteMapping("/reportedUsers/{userId}")
    public ResponseEntity<JsonResponse> deleteReportedUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @PostMapping("/appTerm/")
    public ResponseEntity<JsonResponse> createAppTerms(@RequestBody AppTermRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/appTerm/{versionId}")
    public ResponseEntity<JsonResponse> getAppTermsByVersionId(@PathVariable Long versionId) {
        //TODO
        return null;
    }

    @PutMapping("/appTerm/")
    public ResponseEntity<JsonResponse> updateAppTerms(@RequestBody AppTermRequest request){
        //TODO
        return null;
    }

    @DeleteMapping("/appTerm/{versionId}")
    public ResponseEntity<JsonResponse> deleteAppTerms(@PathVariable Integer versionId) {
        //TODO
        return null;
    }

    public ResponseEntity<JsonResponse> backupDatabase() {
        //TODO
        return null;
    }

    public ResponseEntity<JsonResponse> restoreDatabase() {
        //TODO
        return null;
    }


}
