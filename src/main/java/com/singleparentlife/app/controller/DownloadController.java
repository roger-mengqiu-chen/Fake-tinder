package com.singleparentlife.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @GetMapping("/message/{messageId}")
    public byte[] getAttachmentByMessageId(){
        //TODO
        return null;
    }

    @GetMapping("/profile/{profileId}")
    public byte[] getAttachmentByProfileId(){
        //TODO
        return null;
    }
}
