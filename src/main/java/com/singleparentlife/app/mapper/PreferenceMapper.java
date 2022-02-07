package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Preference;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PreferenceMapper {


    Preference findByContent(String content);

    int save(Preference preference);

    int update(Preference preference);

    int savePreferenceForUser(long userId, long preferenceId);

    int saveTagForUser(long userId, long preferenceId);

    int deletePreference(Preference preference);

    int deleteUserPreference(long userId, long preferenceId);

    Preference findById(long preferenceId);

    int deleteUserTag(long userId, long preferenceId);
}
