package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.payload.request.PreferenceRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.PreferenceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private AuthUtil authUtil;

    @ApiOperation(value = "Create tags from a list")
    @PostMapping("/myTags")
    public ResponseEntity<JsonResponse> addTags(@RequestBody PreferenceRequest request) {
        //TODO
        return null;
    }
    @GetMapping("/myTags")
    public ResponseEntity<JsonResponse> getTags() {
        //TODO
        return null;
    }

    @DeleteMapping("/myTags")
    public ResponseEntity<JsonResponse> deleteTag(@RequestBody PreferenceRequest request) {
        //TODO
        return null;
    }

    @PostMapping("/myPreferences")
    public ResponseEntity<JsonResponse> addPreference(@RequestBody PreferenceRequest request) {
        Long userId = authUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        List<String> preferences = request.getTagNames();
        JsonResponse response = preferenceService.createPreferenceOrTagForUser(userId, preferences, DataType.PREFERENCE);
        return response.toResponseEntity();
    }

    @GetMapping("/myPreferences")
    public ResponseEntity<JsonResponse> getPreferences() {
        //TODO
        return null;
    }

    @DeleteMapping("/myPreferences")
    public ResponseEntity<JsonResponse> deletePreference(@RequestBody PreferenceRequest request) {
        //TODO
        return null;
    }
}
