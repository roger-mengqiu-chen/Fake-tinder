package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private FileService fileService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/profile")
    public ResponseEntity<JsonResponse> uploadWithProfile(@RequestPart("file") MultipartFile file) {

        Long userId = authUtil.getCurrentUserId();
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_IMAGE, "Image can't be empty")
            );
        }

        JsonResponse response = fileService.uploadWithProfile(userId, file);

        return response.toResponseEntity();
    }


}
