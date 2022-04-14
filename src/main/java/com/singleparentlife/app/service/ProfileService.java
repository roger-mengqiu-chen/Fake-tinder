package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.AttachmentMapper;
import com.singleparentlife.app.mapper.MatchMapper;
import com.singleparentlife.app.mapper.ProfileMapper;
import com.singleparentlife.app.service.model.Profile;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;

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
            existedProfile = profileMapper.findByUserId(profile.getUserId());
            log.info ("Profile is updated: {}", profile.getUserId());
            return new JsonResponse(Status.SUCCESS, DataType.PROFILE, existedProfile);
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

    public JsonResponse setAvatarForProfile(Long userId, Long avatarId) {
        Profile profile = profileMapper.findByUserId(userId);
        if (profile == null) {
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, null);
        }
        // check if the image exists
        Long attachmentId = attachmentMapper.findIdOfAttachment(avatarId);
        if (attachmentId == null) {
            return new JsonResponse(Status.FAIL, DataType.ATTACHMENT_NOT_FOUND, null);
        }
        // check existence of profile
        Long profileId = attachmentMapper.getProfileIdOfAttachment(attachmentId);
        if (profileId == null) {
            return new JsonResponse(Status.FAIL, DataType.INVALID_IMAGE, "Not a profile image");
        }
        // check if the user has this image
        else if (profileId.longValue() != userId.longValue()) {
            return new JsonResponse(Status.FAIL, DataType.INVALID_IMAGE, "The image does not belong to this user");
        }

        profile.setAvatarId(avatarId);
        try {
            profileMapper.updateProfileAvatarId(profile);
            log.info("Profile avatar updated: {}", userId);
            return new JsonResponse(Status.SUCCESS, DataType.PROFILE, profile);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse getAllProfiles(Long userId) {
        List<Profile> profiles = profileMapper.findAllButUser(userId);
        return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_PROFILE, profiles);
    }
}
