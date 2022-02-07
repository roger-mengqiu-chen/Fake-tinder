package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Preference;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PreferenceMapper {


    Preference findPreferenceByContent(String content);

    int save(Preference preference);

    int deletePreferenceByContent(Preference preference);

    int update(Preference preference);
}
