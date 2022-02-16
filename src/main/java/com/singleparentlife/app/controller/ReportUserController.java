package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.payload.request.ReportUserRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.ReportUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reportUser")
public class ReportUserController {

    @Autowired
    private ReportUserService reportUserService;

    @PostMapping()
    public ResponseEntity<JsonResponse> reportUser(@RequestBody ReportUserRequest request) {
        Long userId = request.getUserId();

        if (userId == null) {
            return ResponseEntity.badRequest().header("Access-Control-Allow-Origin", "*").body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "UserId can't be null"));
        }
        String reason = request.getReason();

        JsonResponse response = reportUserService.reportUser(userId, reason);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reportedUserId}")
    public ResponseEntity<JsonResponse> getReportedUser(@PathVariable Long reportedUserId) {
        if (reportedUserId == null) {
            return ResponseEntity.badRequest().header("Access-Control-Allow-Origin", "*").body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "ReportedUserId can't be null"));
        }
        JsonResponse response = reportUserService.getReportedUserById(reportedUserId);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{reportedUserId}")
    public ResponseEntity<JsonResponse> deleteReportedUser(@PathVariable Long reportedUserId) {
        if (reportedUserId == null) {
            return ResponseEntity.badRequest().header("Access-Control-Allow-Origin", "*").body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "ReportedUserId can't be null"));
        }
        JsonResponse response = reportUserService.deleteReportedUser(reportedUserId);

        return ResponseEntity.ok(response);
    }

}
