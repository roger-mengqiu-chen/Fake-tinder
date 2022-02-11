package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.MatchMapper;
import com.singleparentlife.app.mapper.ProfileMapper;
import com.singleparentlife.app.model.Match;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.model.Reaction;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private MatchMapper matchMapper;

    /**
     * Create a new profile
     * @param profile profile to be created
     * @return JsonResponse of created profile
     */
    public JsonResponse createProfile(Profile profile) {
        try {
            profileMapper.save(profile);
            log.info("Profile is saved");
            return new JsonResponse(Status.SUCCESS, DataType.PROFILE, profile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
    }

    /**
     * Get profile of user according to userId
     * @param userId userId
     * @return JsonResponse about profile of this user
     */
    public JsonResponse getProfileOfUser(Long userId) {
        Profile profile = profileMapper.findByUserId(userId);
        if (profile == null) {
            log.error("Profile not found: {}", userId);
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, userId);
        }
        return new JsonResponse(Status.SUCCESS, DataType.PROFILE, profile);
    }

    /**
     * Update a profile and return the updated profile
     * @param profile profile
     * @return JsonResponse of updated profile
     */
    public JsonResponse updateProfile(Profile profile) {
        Profile existedProfile = profileMapper.findByUserId(profile.getUserId());
        if (existedProfile == null) {
            log.error("Profile not found");
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, profile);
        }
        try {
            profileMapper.update(profile);
            log.info ("Profile is updated: {}", profile.getUserId());
            return new JsonResponse(Status.SUCCESS, DataType.PROFILE, profile);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }

    }

    /**
     * Delete profile by userId
     * @param userId userId is profileId
     * @return JsonResponse of deleted profile
     */
    public JsonResponse deleteProfileOfUser(Long userId) {
        Profile profile = profileMapper.findByUserId(userId);
        if (profile == null) {
            log.error ("Profile not found: {}", userId);
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, userId);
        }
        try {
            profileMapper.delete(profile);
            log.info("Profile is deleted: {}", profile.getUserId());
            return new JsonResponse(Status.SUCCESS, DataType.PROFILE, profile);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    /**
     * Set reaction to a profile
     * @param userId userId
     * @param targetUserId targetUserId
     * @param reaction Reaction
     * @return JsonResponse of Match
     */
    public JsonResponse reactToProfile(Long userId, Long targetUserId, Reaction reaction) {
        Profile userProfile = profileMapper.findByUserId(userId);
        Profile targetProfile = profileMapper.findByUserId(targetUserId);

        if (userProfile == null || targetProfile == null) {
            log.error("Profile not found for {} or {} or both", userId, targetUserId);
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, null);
        }

        Match match = new Match();
        match.setUserId(userId);
        match.setTargetId(targetUserId);
        match.setReactionId(reaction.getReactionId());
        try {
            matchMapper.save(match);
            log.info("Created a match: {} -> {}", userId, targetUserId);
            return new JsonResponse(Status.SUCCESS, DataType.MATCH, match);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse setAvatarForProfile() {
        //TODO
        return null;
    }
}
