package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.payload.request.PreferenceRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.PreferenceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private AuthUtil authUtil;

    @ApiOperation(value = "Create tags from a list")
    @PostMapping("")
    public ResponseEntity<JsonResponse> addTags(@RequestBody PreferenceRequest request) {
        Long userId = authUtil.getCurrentUserId();
        if (userId == null){
            return ResponseEntity.status(401).build();
        }
        else{
            List<String> tags = request.getTagNames();
            JsonResponse response = preferenceService.createPreferenceOrTagForUser(userId, tags, DataType.TAG);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<JsonResponse> getTags(@PathVariable Long userId) {
        if (userId == -1) {
            userId = authUtil.getCurrentUserId();
        }
        try{
            JsonResponse response = preferenceService.getTagsOfUser(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.ok(new JsonResponse(Status.FAIL, DataType.PREFERENCE_NOT_FOUND, null));
        }
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<JsonResponse> deleteTagById(@PathVariable Long tagId) {
        long userId = authUtil.getCurrentUserId();
        long preferenceId = tagId;
        JsonResponse response = preferenceService.deletePreferenceOrTagForUser(userId, preferenceId, DataType.TAG);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public ResponseEntity<JsonResponse> deleteAllTagsByUserId(){
        long userId =  authUtil.getCurrentUserId();
        JsonResponse response = preferenceService.deleteAllPreferenceOrTagForUser(userId, DataType.TAG);
        return ResponseEntity.ok(response);
    }
}
