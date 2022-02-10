package com.singleparentlife.app.mapper;

import com.singleparentlife.app.model.Reaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionMapper {
    /* Only has findByName */

    Reaction findByName(String reactionName);
}
