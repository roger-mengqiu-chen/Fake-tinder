package com.singleparentlife.app.controller;

import com.google.api.client.json.Json;
import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.payload.request.PreferenceRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.PreferenceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.JdbcRowSet;
import java.util.List;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("")
    public ResponseEntity<JsonResponse> addPreference(@RequestBody PreferenceRequest request) {
        Long userId = authUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        List<String> preferences = request.getTagNames();
        JsonResponse response = preferenceService.createPreferenceOrTagForUser(userId, preferences, DataType.PREFERENCE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<JsonResponse> getPreferences(@PathVariable Long userId) {
        if (userId == -1) {
            userId = authUtil.getCurrentUserId();
        }
        try{
            JsonResponse response = preferenceService.getPreferenceByUserId(userId);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e){
            return ResponseEntity.ok(new JsonResponse(Status.FAIL, DataType.PREFERENCE_NOT_FOUND, null));
        }
    }

    @DeleteMapping("/{preferenceId}")
    public ResponseEntity<JsonResponse> deletePreference(@PathVariable Long preferenceId) {
        long userId = authUtil.getCurrentUserId();
        long tagId = preferenceId;
        JsonResponse response = preferenceService.deletePreferenceOrTagForUser(userId, tagId, DataType.PREFERENCE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public ResponseEntity<JsonResponse> deleteAllPreferenceByUserId(){
        long userId = authUtil.getCurrentUserId();
        JsonResponse response = preferenceService.deleteAllPreferenceOrTagForUser(userId, DataType.PREFERENCE);
        return ResponseEntity.ok(response);
    }


}
