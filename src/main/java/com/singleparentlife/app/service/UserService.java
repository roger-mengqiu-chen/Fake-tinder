package com.singleparentlife.app.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.DeviceMapper;
import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.Device;
import com.singleparentlife.app.model.Location;
import com.singleparentlife.app.model.User;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.payload.response.SanitizedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * This method create user and attach device with this user
     * @param token
     * @param deviceToken
     * @return
     */
    public JsonResponse login (String token, String deviceToken) {
        FirebaseToken decodedToken = null;

        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            log.error("Error with validating Firebase token: {}", e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.INVALID_TOKEN, null);
        }
        if (decodedToken != null) {
            String fireId = decodedToken.getUid();
            User user = userMapper.findByFireId(fireId);
            Device device = new Device();
            device.setDeviceToken(deviceToken);
            device.setRegisterTime(LocalDateTime.now());

            if (user == null) {
                User newUser = new User();
                newUser.setFireId(fireId);
                newUser.setLoginTime(LocalDateTime.now());
                newUser.setStartDate(LocalDate.now());
                newUser.setRoleId(2);
                newUser.setActive(true);
                newUser.setSuspended(false);
                userMapper.save(newUser);

                device.setUserId(newUser.getUserId());
                deviceMapper.save(device);

                SanitizedUser sanitizedUser = sanitizeUser(newUser);
                log.info("New user registered: {}", fireId);
                log.info("New device registered with user {}: {}", newUser.getUserId(), device.getDeviceId());
                return new JsonResponse(Status.SUCCESS, DataType.USER, sanitizedUser);
            }
            else {
                user.setLoginTime(LocalDateTime.now());
                userMapper.update(user);
                device.setUserId(user.getUserId());

                Device existedDevice = deviceMapper.getDeviceByToken(deviceToken);
                if (existedDevice == null) {
                    deviceMapper.save(device);
                    log.info("New device registered with user {}: {}", user.getUserId(), device.getDeviceId());
                }
                else {
                    device.setDeviceId(existedDevice.getDeviceId());
                }

                SanitizedUser sanitizedUser = sanitizeUser(user);
                log.info("User login: {} with device {}", fireId, device.getDeviceId());
                return new JsonResponse(Status.SUCCESS, DataType.USER, sanitizedUser);
            }
        } else {
            log.error("Invalid token");
            return new JsonResponse(Status.FAIL, DataType.INVALID_TOKEN, null);
        }
    }

    public JsonResponse updateUser(User user) {
        try {
            userMapper.update(user);
            SanitizedUser sanitizedUser = sanitizeUser(user);
            log.info("User updated: {}", user.getUserId());
            return new JsonResponse(Status.SUCCESS, DataType.USER, sanitizedUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse getUserById(Long userId) {
        User user = userMapper.findById(userId);
        return new JsonResponse(Status.SUCCESS, DataType.USER, user);
    }

    public JsonResponse getAllUser() {
        List<User> users = userMapper.findAll();
        return new JsonResponse(Status.SUCCESS, DataType.USER, users);
    }

    public JsonResponse deleteUser(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, null);
        }
        try {
            userMapper.delete(userId);
            return new JsonResponse(Status.SUCCESS, DataType.USER, user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse upgradeOrDowngrade(User user, boolean isUpgrade){
        User existedUser = userMapper.findById(user.getUserId());
        if (existedUser == null) {
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, null);
        }

        if (isUpgrade) {
            user.setRoleId(3);
        }
        else {
            user.setRoleId(2);
        }

        try {
            userMapper.update(user);
            return new JsonResponse(Status.SUCCESS, DataType.USER, user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse logout (Long userId, String deviceToken) {
        User user = userMapper.findById(userId);
        if (user == null) {
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, null);
        }
        Device device = deviceMapper.getDeviceByToken(deviceToken);
        if (device == null) {
            return new JsonResponse(Status.FAIL, DataType.DEVICE_NOT_FOUND, null);
        }
        try {
            deviceMapper.deleteDevice(device);
            SanitizedUser sanitizedUser = sanitizeUser(user);
            log.info("User logged out: {}", user.getUserId());
            return new JsonResponse(Status.SUCCESS, DataType.USER, sanitizedUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    /*
       When we return user back to front end, we need hide some sensitive data.
    */
    private SanitizedUser sanitizeUser (User user) {
        SanitizedUser sanitizedUser = new SanitizedUser();
        sanitizedUser.setUserId(user.getUserId());
        sanitizedUser.setEmail(user.getEmail());
        sanitizedUser.setPhone(user.getPhone());
        sanitizedUser.setFireId(user.getFireId());
        switch (user.getRoleId()) {
            case 1: sanitizedUser.setRole("Administrator"); break;
            case 2: sanitizedUser.setRole("Free User"); break;
            case 3: sanitizedUser.setRole("Premium User"); break;
        }
        return sanitizedUser;
    }

}
