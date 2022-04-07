package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.MatchMapper;
import com.singleparentlife.app.mapper.ProfileMapper;
import com.singleparentlife.app.mapper.ReactionMapper;
import com.singleparentlife.app.model.Match;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.model.Reaction;
import com.singleparentlife.app.payload.request.MatchRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.payload.response.MatchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MatchService {
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private ReactionMapper reactionMapper;

    /**
     * Set reaction to a profile
     * @param userId userId
     * @param targetUserId targetUserId
     * @param reaction Reaction
     * @return JsonResponse of Match
     */
    public JsonResponse reactToProfile(Long userId, Long targetUserId, Reaction reaction) {
        Profile userProfile = profileMapper.findByUserId(userId);
        Profile targetProfile = profileMapper.findByUserId(targetUserId);

        if (userProfile == null || targetProfile == null) {
            log.error("Profile not found for {} or {} or both", userId, targetUserId);
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, null);
        }

        Match match = new Match();
        match.setUserId(userId);
        match.setTargetId(targetUserId);
        match.setReactionId(reaction.getReactionId());


        try {
            matchMapper.save(match);
            if (isMatched(userId,targetUserId)==true) {

                JsonResponse response=new JsonResponse(Status.SUCCESS, DataType.MATCH, (new MatchResponse(match,true)));
                return response;
            }
            log.info("Created a match: {} -> {}", userId, targetUserId);
            return new JsonResponse(Status.SUCCESS, DataType.MATCH, (new MatchResponse(match,false)));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    /**
     * Update a match
     * It can be used with rewind functionality.
     * @param userId
     * @param targetUserId
     * @param reactionStr
     * @return JsonResponse of updating
     */
    public JsonResponse updateMatch(Long userId, Long targetUserId, String reactionStr) {
        Match match = matchMapper.findMatchBetweenUsers(userId, targetUserId);
        if (match == null) {
            return new JsonResponse(Status.FAIL, DataType.MATCH_NOT_FOUND, null);
        }

        Reaction reaction = reactionMapper.findByName(reactionStr);
        if (reaction == null) {
            log.error("Reaction name not found: {}", reactionStr);
            return new JsonResponse(Status.FAIL, DataType.REACTION_NOT_FOUND, null);
        }

        try {
            match.setReactionId(reaction.getReactionId());
            matchMapper.update(match);
            log.info("Match is updated: {} <-> {}", match.getUserId(), match.getTargetId());
            return new JsonResponse(Status.SUCCESS, DataType.MATCH, new MatchResponse(match,isMatched(userId,targetUserId)));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse getAllMatchedUsers(Long userId) {
        try {
            List<Match> matches = matchMapper.findOutMatchOfUser(userId);
            return new JsonResponse(Status.SUCCESS, DataType.MATCH, matches);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse getAllMatchesCreatedByUser(Long userId) {
        try {
            List<Match> matches = matchMapper.findMatchOfUser(userId);
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_MATCH, matches);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse getFailedMatchOfUser(Long userId) {
        try {
            List<Match> matches = matchMapper.findFailedMatchOfUser(userId);
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_MATCH, matches);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse rewindMatch(Long userId, Long targetId) {
        Match match = matchMapper.findMatchBetweenUsers(userId, targetId);
        if (match == null) {
            log.error("Match between user {} and {} not found", userId, targetId);
            return new JsonResponse(Status.FAIL, DataType.MATCH_NOT_FOUND, null);
        }

        try {
            matchMapper.delete(match);
            log.info("Match between {} and {} is dropped", match.getUserId(), match.getTargetId());
            return new JsonResponse(Status.SUCCESS, DataType.MATCH, match);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    /**
     * Check match between user and target
     * We should get 2 matches as matching is bilateral
     * If 2 matches both have non-reject reactions, we can say two users are matched
     * @param userId
     * @param targetUserId
     * @return boolean true or false, true if match is mutual
     */
    public boolean isMatched(Long userId, Long targetUserId) {
        Match match1 = matchMapper.findMatchBetweenUsers(userId, targetUserId);
        Match match2 = matchMapper.findMatchBetweenUsers(targetUserId, userId);
        return match1 != null && match2 != null && match1.getReactionId() > 1 && match2.getReactionId()> 1;
    }
    /**
     * Check if user has reacted to same profile before
     * if true, update the ,match with new reaction, otherwise add it.
     * @param userId
     * @param targetUserId
     * @return
     */
    public boolean alreadyReacted(Long userId,Long targetUserId){
        Match match = matchMapper.findMatchBetweenUsers(userId, targetUserId);
        if(match==null){
            return false;
        }
        return true;
    }


    public JsonResponse isMatchedJson(Long userId, Long targetUserId) {
        Match match1 = matchMapper.findMatchBetweenUsers(userId, targetUserId);
        Match match2 = matchMapper.findMatchBetweenUsers(targetUserId, userId);
        if (match1 != null && match2 != null && match1.getReactionId() > 1 && match2.getReactionId() > 1) {
            return new JsonResponse(Status.SUCCESS, DataType.IS_MATCH, true);
        }
        return new JsonResponse(Status.SUCCESS, DataType.IS_MATCH, false);
    }
    public JsonResponse sendMatched(Long userId, MatchRequest mutualMatch){

        return null;
    }
}
