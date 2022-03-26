package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.payload.request.MatchRequest;
import com.singleparentlife.app.payload.request.MatchedRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private AuthUtil authUtil;

    @GetMapping("/success")
    public ResponseEntity<JsonResponse> getAllMatchesOfUser() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = matchService.getAllMatchedUsers(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fail")
    public ResponseEntity<JsonResponse> getFailedMatchMadeByUser() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = matchService.getFailedMatchOfUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/createdMatches")
    public ResponseEntity<JsonResponse> getMatchCreatedByUser() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = matchService.getAllMatchesCreatedByUser(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<JsonResponse> rewindMatch(@PathVariable Long targetId) {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = matchService.rewindMatch(userId, targetId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResponse> updateMatch(@RequestBody MatchRequest request) {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = matchService.updateMatch(userId, request.getTargetId(), request.getReaction());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/matched")
    public ResponseEntity<JsonResponse> isMatch(@RequestBody MatchedRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Long targetId = request.getTargetId();
        JsonResponse matchResponse = matchService.isMatchedJson(userId, targetId);
        return ResponseEntity.ok(matchResponse);
    }
}
