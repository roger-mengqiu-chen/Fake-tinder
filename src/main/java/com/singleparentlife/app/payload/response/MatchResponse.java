package com.singleparentlife.app.payload.response;

import com.singleparentlife.app.model.Match;
import lombok.Data;

// This is not  a fully real response but a Pseudo response for better synchronous API calls, THis returns back match with a mutaul match boolean for front end to check whether both have accepted profile of each other at one API rewuest Only.
@Data
public class MatchResponse {
    private Match match;
    private Boolean mutualMatch;
    public MatchResponse(Match match,Boolean mutualMatch){
        this.match=match;
        this.mutualMatch=mutualMatch;
    }
}
