package com.yangnan.selfhelpordingsystem.controller;

import com.yangnan.selfhelpordingsystem.common.CommonResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class TestController {
    @GetMapping("/test")
    public CommonResult test() {
        return CommonResult.success();
    }

//    @Value("${prop.upload-folder}")
    @Value("D:\\")
    private String UPLOAD_FOLDER;

    @PostMapping("/singlefile")
    public Object singleFileUpload(MultipartFile file,String name) {
        if(!Strings.isEmpty(name)){
            System.out.println(name);
        }
        if (Objects.isNull(file) || file.isEmpty()) {
            return "文件为空，请重新上传";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "后端异常...";
        }
    }

}
