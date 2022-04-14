package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.ReactionMapper;
import com.singleparentlife.app.service.model.Reaction;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {
    @Autowired
    private ReactionMapper reactionMapper;

    public JsonResponse getReactionByName(String reactionName) {
        Reaction reaction = reactionMapper.findByName(reactionName);

        if (reaction == null) {
            return new JsonResponse(Status.FAIL, DataType.REACTION_NOT_FOUND, reactionName);
        }
        return new JsonResponse(Status.SUCCESS, DataType.REACTION, reaction);
    }
}
