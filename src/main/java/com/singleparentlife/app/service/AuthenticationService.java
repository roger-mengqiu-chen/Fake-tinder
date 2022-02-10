package com.singleparentlife.app.service;

import com.singleparentlife.app.mapper.EventInvitationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private EventInvitationMapper eventInvitationMapper;


}
