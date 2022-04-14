package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private AuthUtil authUtil;

    @GetMapping("/location")
    public ResponseEntity<JsonResponse> getRecommendationsBasedOnLocations() {

        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = recommendationService.getRecommendationsBasedOnLocation(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/preference")
    public ResponseEntity<JsonResponse> getRecommendationsBasedOnPreferences() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = recommendationService.getRecommendationBasedOnMatchedPreference(userId);
        return ResponseEntity.ok(response);
    }
}
