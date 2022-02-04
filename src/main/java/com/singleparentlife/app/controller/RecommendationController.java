package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @GetMapping
    public ResponseEntity<JsonResponse> getRecommendations() {
        //TODO
        return null;
    }
}