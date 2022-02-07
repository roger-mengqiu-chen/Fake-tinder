package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.ReportedUserMapper;
import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.ReportedUser;
import com.singleparentlife.app.model.User;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ReportUserService {

    @Autowired
    private ReportedUserMapper reportedUserMapper;
    @Autowired
    private UserMapper userMapper;

    public JsonResponse reportUser(Long userId, String reason) {

        User user = userMapper.findById(userId);
        if (user == null) {
            log.error("User not found: {}", userId);
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, "User not found");
        }

        if (reason == null || reason.isEmpty()) {
            log.error("Reason can't be empty");
            return new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Reason can't be empty");
        }

        // get current user's userId as reporterId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fireId = authentication.getName();
        long reporterId = userMapper.getUserIdByFireId(fireId);
        LocalDateTime reportTime = LocalDateTime.now();

        ReportedUser reportedUser = new ReportedUser();
        reportedUser.setUserId(userId);
        reportedUser.setReporterId(reporterId);
        reportedUser.setReason(reason);
        reportedUser.setReportTime(reportTime);

        try {
            reportedUserMapper.save(reportedUser);
            log.info("User {} is reported by {}", userId, reporterId);
            return new JsonResponse(Status.SUCCESS, DataType.REPORTED_USER, reportedUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            return  new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, e.getMessage());
        }
    }

    public JsonResponse deleteReportedUser (Long userId) {
        ReportedUser reportedUser = reportedUserMapper.findById(userId);
        if (reportedUser == null) {
            log.error("User id not found");
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, "ReportedUser id not found");
        }
        else {
            try {
                reportedUserMapper.delete(reportedUser);
                log.info("ReportedUser is deleted: {}", userId);
                return new JsonResponse(Status.SUCCESS, DataType.STATUS_MESSAGE, "ReportedUser is deleted");
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, e.getMessage());
            }
        }
    }

    public JsonResponse getReportedUserById(Long userId) {
        ReportedUser reportedUser = reportedUserMapper.findById(userId);
        if (reportedUser == null) {
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, "ReportedUser id not found");
        }
        return new JsonResponse(Status.SUCCESS, DataType.REPORTED_USER, reportedUser);
    }

}
