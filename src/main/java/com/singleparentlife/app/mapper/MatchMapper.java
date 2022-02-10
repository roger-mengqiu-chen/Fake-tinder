package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Match;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchMapper {

    long save(Match match);
}
