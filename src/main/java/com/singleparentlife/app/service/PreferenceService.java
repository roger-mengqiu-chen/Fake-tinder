package com.singleparentlife.app.service;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.PreferenceMapper;
import com.singleparentlife.app.model.Preference;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PreferenceService {

    @Autowired
    private PreferenceMapper preferenceMapper;
    @Autowired
    private AuthUtil authUtil;

    public JsonResponse createPreference (String preferenceStr) {
        Preference preference = preferenceMapper.findByContent(preferenceStr);
        if (preference == null) {
            try {
                preference = new Preference(preferenceStr);
                preferenceMapper.save(preference);
                // we need the persisted preference with perferenceId
                preference = preferenceMapper.findByContent(preferenceStr);
                log.info("Preference is saved: {}", preferenceStr);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
        return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, preference);
    }

    /*
        Create preference for user
        If the preference doesn't exist, create it.
     */
    public JsonResponse createPreferenceOrTagForUser (String preferenceStr, DataType dataType) {

        long userId = authUtil.getCurrentUserId();

        JsonResponse response = createPreference(preferenceStr);
        if (response.getStatus().equals(Status.SUCCESS)) {
            Preference preference = (Preference)response.getData();
            if (dataType.equals(DataType.PREFERENCE)) {
                preferenceMapper.savePreferenceForUser(userId, preference.getPreferenceId());
            }
            else if (dataType.equals(DataType.TAG)) {
                preferenceMapper.saveTagForUser(userId, preference.getPreferenceId());
            }
            else {
                log.error("Invalid dataType, should be PREFERENCE or TAG");
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "Invalid dataType, should be PREFERENCE or TAG");
            }
            log.info("Preference is created: {}", preferenceStr);
            return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, preference);
        }
        else {
            return response;
        }
    }

    public JsonResponse getPreferenceByContent (String content) {
        Preference preference = preferenceMapper.findByContent(content);
        return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, preference);
    }

    public JsonResponse updatePreferenceByContent (String content, String updatedContent) {
        try {
            Preference preference = preferenceMapper.findByContent(content);
            Preference existedPreference = preferenceMapper.findByContent(updatedContent);
            if (preference == null) {
                log.error("Preference not found: {}", content);
                return new JsonResponse(Status.FAIL, DataType.PREFERENCE_NOT_FOUND, null);
            }
            else if (existedPreference != null) {
                log.error("Updated preference already existed: {}", updatedContent);
                return new JsonResponse(Status.FAIL, DataType.PREFERENCE_EXISTED, existedPreference);
            }
            else {
                preference.setContent(updatedContent);
                preferenceMapper.update(preference);
                log.info("Preference is updated: {} -> {}", content, updatedContent );
                return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, preference);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse deletePreferenceByContent (String content) {

        Preference preference = preferenceMapper.findByContent(content);
        if (preference == null) {
            return new JsonResponse(Status.FAIL, DataType.PREFERENCE_NOT_FOUND, null);
        }

        try {
            preferenceMapper.deletePreference(preference);
            log.info("Preference is deleted: {}", preference.getPreferenceId());
            return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, preference);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse deletePreferenceOrTagForUser(Long preferenceId, DataType dataType) {

        long userId = authUtil.getCurrentUserId();

        Preference preference = preferenceMapper.findById(preferenceId);
        if (preference == null) {
            log.error("Preference id not found: {}", preferenceId);
            return new JsonResponse(Status.FAIL, DataType.PREFERENCE_NOT_FOUND, null);
        }
        try {
            if (dataType.equals(DataType.PREFERENCE)) {
                preferenceMapper.deleteUserPreference(userId, preferenceId);
            }
            else if (dataType.equals(DataType.TAG)) {
                preferenceMapper.deleteUserTag(userId, preferenceId);
            }
            else {
                log.error("Invalid dataType, should be PREFERENCE or TAG");
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "Invalid dataType, should be PREFERENCE or TAG");
            }
            return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, preference);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

}
