package com.singleparentlife.app.Util;

import com.singleparentlife.app.model.Attachment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUtil {

    public Attachment fileToAttachment(MultipartFile file) throws IOException {
        try {
            InputStream is = file.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                baos.write(data, 0, nRead);
            }
            baos.flush();
            byte[] attachmentContent = baos.toByteArray();
            baos.close();
            is.close();
            String attachmentType = file.getContentType();
            Attachment attachment = new Attachment();
            attachment.setAttachmentType(attachmentType);
            attachment.setAttachmentContent(attachmentContent);
            return attachment;
        } catch (IOException e) {
            throw e;
        }
    }
}
