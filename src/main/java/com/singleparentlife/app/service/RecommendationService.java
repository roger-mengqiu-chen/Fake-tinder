package com.singleparentlife.app.service;

import com.singleparentlife.app.Util.LocationUtil;
import com.singleparentlife.app.Util.comparator.ProfileDistanceComparator;
import com.singleparentlife.app.Util.comparator.ProfilePreferenceComparator;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.LocationMapper;
import com.singleparentlife.app.mapper.MatchMapper;
import com.singleparentlife.app.mapper.PreferenceMapper;
import com.singleparentlife.app.mapper.ProfileMapper;
import com.singleparentlife.app.model.Location;
import com.singleparentlife.app.model.Match;
import com.singleparentlife.app.model.Preference;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RecommendationService needs more advanced algorithms. Currently, we are assuming that there are not many profiles in
 * database.
 */

@Service
@Slf4j
public class RecommendationService {

    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private PreferenceMapper preferenceMapper;
    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private LocationUtil locationUtil;

    public JsonResponse getRecommendationsBasedOnLocation(Long userId) {
        Profile profile = profileMapper.findByUserId(userId);
        if (profile == null) {
            log.error("Profile not found: {}", userId);
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, null);
        }
        Location userLocation = locationMapper.findById(profile.getLocationId());
        if (userLocation == null) {
            log.error("User does not have location: {}", userId);
            return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, "User does not have location");
        }

        List<Profile> otherProfiles = profileMapper.findAllButUser(userId);
        List<Profile> sortedProfiles = new ArrayList<>();

        for (Profile p : otherProfiles) {
            Long locationId = p.getLocationId();
            double distance = Double.MAX_VALUE;
            if (locationId != null) {
                Location location = locationMapper.findById(p.getLocationId());
                distance = locationUtil.distanceBetweenLocations(userLocation, location);
            }
            p.setDistanceToMe(distance);

            if (shouldGetRecommended(profile, p)) {
                sortedProfiles.add(p);
            }
        }
        sortedProfiles.sort(new ProfileDistanceComparator());
        return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_PROFILE, sortedProfiles);
    }

    public JsonResponse getRecommendationBasedOnMatchedPreference(Long userId) {
        Profile currentUserProfile = profileMapper.findByUserId(userId);
        List<Preference> userPreferences = preferenceMapper.getPreferencesOfUser(userId);
        List<Profile> otherProfiles = profileMapper.findAllButUser(userId);
        List<Profile> sortedProfiles = new ArrayList<>();
        List<Integer> numbersOfMatchedPreferences = new ArrayList<>();
        for (Profile p : otherProfiles) {
            List<Preference> preferences = preferenceMapper.getPreferencesOfUser(p.getUserId());
            int numberOfMatched = calculateNumberOfMatchedPreference(userPreferences, preferences);
            p.setNumberOfMatchedPreferencesWithMe(numberOfMatched);
            if (shouldGetRecommended(currentUserProfile, p)) {
                sortedProfiles.add(p);
            }
        }
        sortedProfiles.sort(new ProfilePreferenceComparator());
        return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_PROFILE, sortedProfiles);
    }

    private int calculateNumberOfMatchedPreference(List<Preference> list1, List<Preference> list2) {
        int matched = 0;
        for (Preference p1 : list1) {
            for (Preference p2 : list2) {
                if (p1.equalsTo(p2)) {
                    matched ++;
                }
            }
        }
        return matched;
    }

    private boolean shouldGetRecommended(Profile user, Profile profile) {
        String showme = user.getShowme();
        String gender = profile.getGender();
        Long userId = profile.getUserId();
        Long targetId = profile.getUserId();

        return !showme.equals(gender) && matchMapper.findMatchBetweenUsers(userId, targetId) == null;
    }
}
