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
import java.util.stream.Collectors;

@Service
@Slf4j
public class PreferenceService {

    @Autowired
    private PreferenceMapper preferenceMapper;

    public JsonResponse createPreferenceFromList(List<String> preferences) {
        List<String> errors = new ArrayList<>();
        boolean haveError = false;
        for (String p : preferences) {
            Preference preference = preferenceMapper.findPreferenceByContent(p);
            if (preference == null) {
                try {
                    preference = new Preference();
                    preference.setContent(p);
                    preferenceMapper.save(preference);
                    log.info("Preference is saved: {}", p);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    haveError = true;
                    errors.add(p);
                    log.error("ERROR with saving preference: {}", p);
                }
            }
        }
        // If we found errors, we need to tell controller.
        if (haveError) {
            return new JsonResponse(Status.FAIL, DataType.PREFERENCES_WITH_ERROR, errors);
        }

        List<Preference> preferenceList = preferences.stream().map(
                e -> preferenceMapper.findPreferenceByContent(e)).collect(Collectors.toList());

        return new JsonResponse(Status.SUCCESS, DataType.PREFERENCES, preferenceList);
    }

    public JsonResponse getPreferenceByContent (String name) {
        try {
            Preference preference = preferenceMapper.findPreferenceByContent(name);
            return new JsonResponse(Status.SUCCESS, DataType.PREFERENCE, preference);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse updatePreferenceByContent (String content, String updatedContent) {
        try {
            Preference preference = preferenceMapper.findPreferenceByContent(content);
            if (preference == null) {
                log.error("Preference not found: {}", content);
                return new JsonResponse(Status.FAIL, DataType.PREFERENCE_NOT_FOUND, null);
            }
            else {
                preference.setContent(updatedContent);
                preferenceMapper.update(preference);
                log.info("Preference is updated: {} -> {}", content, updatedContent );
                return new JsonResponse(Status.SUCCESS, null, null);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR,null);
        }
    }

    public JsonResponse deletePreferenceByContent (String content) {

        Preference preference = preferenceMapper.findPreferenceByContent(content);
        if (preference == null) {
            return new JsonResponse(Status.FAIL, DataType.PREFERENCE_NOT_FOUND, null);
        }

        try {
            preferenceMapper.deletePreferenceByContent(preference);
            log.info("Preference is deleted: {}", preference.getPreferenceId());
            return new JsonResponse(Status.SUCCESS, null, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }
}
