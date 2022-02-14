package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.payload.request.ReportUserRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.ReportUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/reportUser")
public class ReportUserController {

    @Autowired
    private ReportUserService reportUserService;

    @PostMapping()
    public ResponseEntity<JsonResponse> reportUser(@RequestBody ReportUserRequest request) {
        Long userId = request.getUserId();

        if (userId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "UserId can't be null"));
        }
        String reason = request.getReason();

        JsonResponse response = reportUserService.reportUser(userId, reason);
        return response.toResponseEntity();
    }

    @GetMapping("/{reportedUserId}")
    public ResponseEntity<JsonResponse> getReportedUser(@PathVariable Long reportedUserId) {
        if (reportedUserId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "ReportedUserId can't be null"));
        }
        JsonResponse response = reportUserService.getReportedUserById(reportedUserId);

        return response.toResponseEntity();

    }

    @DeleteMapping("/{reportedUserId}")
    public ResponseEntity<JsonResponse> deleteReportedUser(@PathVariable Long reportedUserId) {
        if (reportedUserId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "ReportedUserId can't be null"));
        }
        JsonResponse response = reportUserService.deleteReportedUser(reportedUserId);

        return response.toResponseEntity();
    }

}
