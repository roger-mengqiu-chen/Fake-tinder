package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.PreferenceMapper;
import com.singleparentlife.app.model.Preference;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PreferenceService {

    @Autowired
    private PreferenceMapper preferenceMapper;

    public JsonResponse createPreference (String preferenceStr) {
        Preference p = createPreferenceHelper(preferenceStr);
        if (p != null) {
            return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, p);
        }
        else {
            return new JsonResponse(Status.FAIL, DataType.PREFERENCE_WITH_ERROR, preferenceStr);
        }
    }

    /*
        Create preference for user
        If the preference doesn't exist, create it.
     */
    public JsonResponse createPreferenceOrTagForUser (long userId, List<String> preferenceStr, DataType dataType) {

        if(preferenceStr.size() == 0) {
            return new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Preference or tag list is empty");
        }

        List<Preference> successList = new ArrayList<>();
        List<String> failedList = new ArrayList<>();

        for (String p : preferenceStr) {
            Preference createdPreference = createPreferenceHelper(p);
            if (createdPreference != null) {
                successList.add(createdPreference);
            }
            else {
                failedList.add(p);
            }
        }

        if (dataType.equals(DataType.PREFERENCE)) {
            successList.forEach(pref
                    -> preferenceMapper.savePreferenceForUser(userId, pref.getPreferenceId()));
            if (failedList.size() == 0) {
                return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, successList);
            }
            else {
                return new JsonResponse(Status.FAIL, DataType.PREFERENCE_WITH_ERROR, failedList);
            }
        }
        else if (dataType.equals(DataType.TAG)) {
            successList.forEach((pref
                    -> preferenceMapper.saveTagForUser(userId, pref.getPreferenceId())));
            if (failedList.size() == 0) {
                return new JsonResponse(Status.SUCCESS, DataType.TAG, successList);
            }
            else {
                return new JsonResponse(Status.FAIL, DataType.TAG_WITH_ERROR, failedList);
            }
        }
        else {
            log.error("Invalid dataType, should be PREFERENCE or TAG");
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "Invalid dataType, should be PREFERENCE or TAG");
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

    public JsonResponse deletePreferenceOrTagForUser(long userId, long preferenceId, DataType dataType) {

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

    private Preference createPreferenceHelper (String preference) {
        preference = preference.trim().toLowerCase();
        Preference p = preferenceMapper.findByContent(preference);
        if (p == null) {
            try {
                Long id = preferenceMapper.save(new Preference(preference));
                log.info("Preference is saved: {}", preference);
                return preferenceMapper.findById(id);
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }
        return p;
    }
}
