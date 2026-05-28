package com.gzlg.controller;

import com.gzlg.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        log.info("文件上传, 文件名: {}", file.getOriginalFilename());

        String originalFilename = file.getOriginalFilename();
        int lastDotIndex = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(lastDotIndex);
        String newFileName = UUID.randomUUID().toString() + extName;

        String imageDir = System.getProperty("user.dir") + File.separator + "image";
        File dir = new File(imageDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = imageDir + File.separator + newFileName;
        file.transferTo(new File(filePath));

        String url = "http://localhost:8080/image/" + newFileName;
        log.info("文件上传成功, 访问路径: {}", url);

        return Result.success(url);
    }
}
