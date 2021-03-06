package com.singleparentlife.app.service;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.ReportedUserMapper;
import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.ReportedUser;
import com.singleparentlife.app.model.User;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.payload.response.SanitizedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ReportUserService {

    @Autowired
    private ReportedUserMapper reportedUserMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthUtil authUtil;

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
        long reporterId = authUtil.getCurrentUserId();
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
            return  new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse deleteReportedUser (Long userId) {
        List<ReportedUser> reportedUsers = reportedUserMapper.findById(userId);
        if (reportedUsers.size() == 0) {
            log.error("User id not found");
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, "ReportedUser not found");
        }
        else {
            try {
                User user = userMapper.findById(userId);
                SanitizedUser sanitizedUser = new SanitizedUser();
                BeanUtils.copyProperties(user, sanitizedUser);
                reportedUserMapper.delete(userId);
                log.info("ReportedUser is deleted: {}", userId);
                return new JsonResponse(Status.SUCCESS, DataType.USER, sanitizedUser);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
    }

    public JsonResponse getReportedUserById(Long userId) {
        List<ReportedUser> reportedUsers = reportedUserMapper.findById(userId);
        if (reportedUsers.size() == 0) {
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, "ReportedUser id not found");
        }
        return new JsonResponse(Status.SUCCESS, DataType.REPORTED_USER, reportedUsers);
    }

}
