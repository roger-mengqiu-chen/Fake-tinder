package com.singleparentlife.app.service;

import com.singleparentlife.app.mapper.AttachmentMapper;
import com.singleparentlife.app.model.Message;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    public JsonResponse uploadWithMessage(Message message, MultipartFile file) {
        //TODO
        return null;
    }

    public JsonResponse uploadWithProfile(Profile profile, MultipartFile file){
        //TODO
        return null;
    }
}
