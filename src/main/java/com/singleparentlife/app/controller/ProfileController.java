package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.Util.LocationUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.*;
import com.singleparentlife.app.payload.request.*;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ReactionService reactionService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private LocationUtil locationUtil;

    @PostMapping()
    public ResponseEntity<JsonResponse> createProfile(@RequestBody ProfileRequest request) {
        Long userId = authUtil.getCurrentUserId();
        User user = (User)userService.getUserById(userId).getData();
        String email = request.getEmail();
        if (email != null && !validEmail(email)) {
            return ResponseEntity.ok(new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Invalid email format"));
        }

        user.setEmail(email);
        Profile profile = new Profile();
        BeanUtils.copyProperties(request, profile);

        profile.setUserId(userId);
        profile.setProfileImgAmt((short)0);
        LocationRequest locationRequest = request.getLocation();

        Double lat = locationRequest.getLat();
        Double lon = locationRequest.getLon();

        List<String> preferences = request.getPreferences().getTagNames();
        JsonResponse preferenceResponse = preferenceService.createPreferenceOrTagForUser(userId, preferences, DataType.PREFERENCE);

        if (preferenceResponse.getStatus().equals(Status.FAIL)) {
            return ResponseEntity.ok(preferenceResponse);
        }

        Location location = locationUtil.GPSToLocation(lat, lon);

        // we need the id of location, check it with location service.
        JsonResponse locationResponse = locationService.createLocation(location);
        if (locationResponse.getStatus().equals(Status.SUCCESS)) {
            Location returnedLocation = (Location) locationResponse.getData();
            if (returnedLocation != null) {
                profile.setLocationId(returnedLocation.getLocationId());
            }
        }

        userService.updateUser(user);
        JsonResponse response = profileService.createProfile(profile);

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<JsonResponse> getCurrentUserProfile() {
        try {
            long userId = authUtil.getCurrentUserId();
            JsonResponse response = profileService.getProfileOfUser(userId);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {

            return ResponseEntity.ok(new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, null));
        }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<JsonResponse> getProfile(@PathVariable Long userId) {
        JsonResponse response = profileService.getProfileOfUser(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<JsonResponse> updateProfile(@RequestBody ProfileRequest request) {
        Long userId = authUtil.getCurrentUserId();

        // as email is not part of profile, it should be updated with userService
        String email = request.getEmail();
        User user = (User) userService.getUserById(userId).getData();
        if (user == null) {
            return ResponseEntity.badRequest().body(new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, null));
        }
        user.setEmail(email);
        JsonResponse userUpdateResponse = userService.updateUser(user);
        if (userUpdateResponse.getStatus().equals(Status.FAIL)) {
            return ResponseEntity.ok(userUpdateResponse);
        }

        Profile profile = new Profile();
        BeanUtils.copyProperties(request, profile);
        profile.setUserId(userId);
        LocationRequest locationRequest = request.getLocation();
        Double lat = null;
        Double lon = null;
        if (locationRequest != null) {
            lat = locationRequest.getLat();
            lon = locationRequest.getLon();
        }

        Location location = locationUtil.GPSToLocation(lat, lon);

        // we need the id of location, check it with location service.
        JsonResponse locationResponse = locationService.createLocation(location);
        Location returnedLocation = null;
        if (locationResponse.getData() != null) {
            returnedLocation = (Location) locationResponse.getData();
        }
        if (returnedLocation != null) {
            profile.setLocationId(returnedLocation.getLocationId());
        }

        JsonResponse response = profileService.updateProfile(profile);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/avatar/{avatarId}")
    public ResponseEntity<JsonResponse> updateAvatar(@PathVariable Long avatarId) {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = profileService.setAvatarForProfile(userId, avatarId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("")
    public ResponseEntity<JsonResponse> deleteProfile() {

        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = profileService.deleteProfileOfUser(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/react")
    public ResponseEntity<JsonResponse> reactToProfile(@RequestBody ReactToProfile request) {
        Long userId = authUtil.getCurrentUserId();
        Long targetId = request.getTargetId();
        JsonResponse reactionResponse = reactionService.getReactionByName(request.getReaction());
        if (reactionResponse.getStatus().equals(Status.FAIL)) {
            return ResponseEntity.ok(reactionResponse);
        }
        Reaction reaction = (Reaction) reactionResponse.getData();
        if(matchService.alreadyReacted(userId,targetId)==false){

            JsonResponse response = matchService.reactToProfile(userId, targetId, reaction);
        if (matchService.isMatched(userId, targetId)) {
            Profile targetProfile =(Profile)profileService.getProfileOfUser(targetId).getData();
            String targetName = targetProfile.getFirstname();
            NotificationRequest notification = new NotificationRequest();
            notification.setTopic("Match");
            notification.setTitle("You have a matched profile");
            notification.setBody("You have matched with " + targetName);
            JsonResponse notificationResponse = notificationService.sendNotification(userId, notification);
            if (notificationResponse.getStatus().equals(Status.FAIL)) {
                return ResponseEntity.ok(notificationResponse);
            }
        }
        return ResponseEntity.ok(response);}
        JsonResponse response=matchService.updateMatch(userId, targetId, request.getReaction());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/reacted")
    public ResponseEntity<JsonResponse> isMatch(@RequestBody MatchRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Long targetId = request.getTargetId();
        JsonResponse matchResponse = matchService.isMatchedJson(userId, targetId);
        return ResponseEntity.ok(matchResponse);
    }
    private boolean validEmail(String email) {
        return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }
}
