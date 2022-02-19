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

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    public JsonResponse login (String token, String deviceToken) {
        FirebaseToken decodedToken = null;

        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            log.error("Error with validating Firebase token: {}", e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.STATUS_MESSAGE, "Invalid token");
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

                SanitizedUser sanitizedUser = sanitizeUser(user);
                log.info("User login: {} with device {}", fireId, existedDevice.getDeviceId());
                return new JsonResponse(Status.SUCCESS, DataType.USER, sanitizedUser);
            }
        } else {
            log.error("Invalid token");
            return new JsonResponse(Status.FAIL, DataType.STATUS_MESSAGE, "Invalid token");
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

        if (user == null) {
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, null);
        }
        return new JsonResponse(Status.SUCCESS, DataType.USER, user);
    }

    public JsonResponse deleteUser(Long userId) {
        //TODO
        return null;
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
        return sanitizedUser;
    }



     /*
     * Names of the methods to work on */
    public  void upgrade(User user){

    }
    public  void  downgrade(User user){

    }

    public  JsonResponse  delete(User user){
        if (user == null) {
            log.error("Null user");
            return new JsonResponse(Status.FAIL, DataType.INVALID_USER, user.getUserId());
        }

        else{
            try {

                //could be better to use getCurrent UserId from AuthUtil but willl do this for now
                userMapper.delete(user.getUserId());
               //might not show up these because user is already deleted
                log.info("User is deleted {}", user.getUserId());
                return new JsonResponse(Status.SUCCESS, DataType.USER, user.getUserId());
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }

    }}
    //to delete all voids with response, just keeping these for now to not get any error
    public  void updatePassword(User user, String password){

    }
    public  void  updateFirstName(User user, String firstName ){}
    public  void  updateLastName(User user, String lastName){}
    public  void  updateProfileImg(User user, Long profileImgID){}
    public  void  updateDescription(User user, String description){}
    public  void  updateLocation(User user, Location location){}

}
