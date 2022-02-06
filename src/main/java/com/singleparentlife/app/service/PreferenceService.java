package com.singleparentlife.app.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.PreferenceMapper;
import com.singleparentlife.app.model.Preference;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceMapper preferenceMapper;

    Logger logger = LoggerFactory.getLogger(PreferenceService.class);

    public JsonResponse createPreferenceFromList(List<String> preferences) {
        List<String> errors = new ArrayList<>();
        boolean haveError = false;
        for (String p : preferences) {
            Preference preference = preferenceMapper.findPreferenceByContent(p);
            if (preference == null) {
                try {
                    preferenceMapper.save(preference);
                    logger.info("Preference is saved: {}", p);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    haveError = true;
                    errors.add(p);
                    logger.error("ERROR with saving preference: {}", p);
                }
            }
        }
        // If we found errors, we need to tell controller.
        if (haveError) {
            return new JsonResponse(Status.PREFERENCES_PARTIALLY_CREATED, "Preferences with error", errors);
        }

        return new JsonResponse(Status.SUCCESS);
    }

    public JsonResponse getPreferenceByContent (String name) {
        try {
            Preference preference = preferenceMapper.findPreferenceByContent(name);
            return new JsonResponse(Status.SUCCESS, "preference", preference);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.FAIL);
        }
    }

    public JsonResponse updatePreferenceByContent (String content, String updatedContent) {
        try {
            Preference preference = preferenceMapper.findPreferenceByContent(content);
            if (preference == null) {
                logger.error("Preference not found: {}", content);
                return new JsonResponse(Status.PREFERENCE_NOT_FOUND);
            }
            else {
                preference.setContent(updatedContent);
                preferenceMapper.update(preference);
                logger.info("Preference is updated: {} -> {}", content, updatedContent );
                return new JsonResponse(Status.SUCCESS);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.FAIL);
        }
    }

    public JsonResponse deletePreferenceByName (String name) {
        try {
            preferenceMapper.deletePreferenceByContent(name);
            logger.info("Preference is deleted: {}", name);
            return new JsonResponse(Status.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.FAIL);
        }
    }
}
