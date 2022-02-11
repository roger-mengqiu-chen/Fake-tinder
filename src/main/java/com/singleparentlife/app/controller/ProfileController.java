package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.Location;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.model.Reaction;
import com.singleparentlife.app.model.User;
import com.singleparentlife.app.payload.request.LocationRequest;
import com.singleparentlife.app.payload.request.ProfileRequest;
import com.singleparentlife.app.payload.request.ReactToProfile;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping()
    public ResponseEntity<JsonResponse> createProfile(@RequestBody ProfileRequest request) {
        Long userId = authUtil.getCurrentUserId();
        User user = (User)userService.getUserById(userId).getData();
        user.setEmail(request.getEmail());
        Profile profile = new Profile();
        BeanUtils.copyProperties(request, profile);

        profile.setUserId(userId);
        profile.setProfileImgAmt((short)0);
        LocationRequest locationRequest = request.getLocation();
        locationRequest.formatted();

        Location location = new Location();
        BeanUtils.copyProperties(locationRequest, location);

        // we need the id of location, check it with location service.
        JsonResponse locationResponse = locationService.createLocation(location);
        if (locationResponse.getStatus().equals(Status.SUCCESS)) {
            Location returnedLocation = (Location) locationResponse.getData();
            profile.setLocationId(returnedLocation.getLocationId());
        }
        // if failed, returned the response directly
        else {
            return locationResponse.toResponseEntity();
        }
        userService.updateUser(user);
        JsonResponse response = profileService.createProfile(profile);

        return response.toResponseEntity();
    }

    @GetMapping()
    public ResponseEntity<JsonResponse> getCurrentUserProfile() {
        long userId = authUtil.getCurrentUserId();
        JsonResponse response = profileService.getProfileOfUser(userId);
        return response.toResponseEntity();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<JsonResponse> getProfile(@PathVariable Long userId) {
        JsonResponse response = profileService.getProfileOfUser(userId);
        return response.toResponseEntity();
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateProfile(@RequestBody ProfileRequest request) {
        Profile profile = new Profile();
        BeanUtils.copyProperties(request, profile);
        LocationRequest locationRequest = request.getLocation();
        locationRequest.formatted();

        Location location = new Location();
        BeanUtils.copyProperties(locationRequest, location);

        // we need the id of location, check it with location service.
        JsonResponse locationResponse = locationService.createLocation(location);
        if (locationResponse.getStatus().equals(Status.SUCCESS)) {
            Location returnedLocation = (Location) locationResponse.getData();
            profile.setLocationId(returnedLocation.getLocationId());
        }
        // if failed, returned the response directly
        else {
            return locationResponse.toResponseEntity();
        }
        JsonResponse response = profileService.updateProfile(profile);

        return response.toResponseEntity();
    }

    @DeleteMapping("")
    public ResponseEntity<JsonResponse> deleteProfile() {

        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = profileService.deleteProfileOfUser(userId);
        return response.toResponseEntity();
    }

    @PostMapping("/react")
    public ResponseEntity<JsonResponse> reactToProfile(@RequestBody ReactToProfile request) {
        Long userId = authUtil.getCurrentUserId();
        Long targetId = request.getTargetId();
        JsonResponse reactionResponse = reactionService.getReactionByName(request.getReaction());
        if (reactionResponse.getStatus().equals(Status.FAIL)) {
            return reactionResponse.toResponseEntity();
        }
        Reaction reaction = (Reaction) reactionResponse.getData();
        JsonResponse response = profileService.reactToProfile(userId, targetId, reaction);
        return null;
    }
}
