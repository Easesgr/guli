package com.anyi.serviceoss.controller;

import com.anyi.commonutils.R;
import com.anyi.serviceoss.serivcie.OssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author 安逸i
 * @version 1.0
 */
@RestController
@RequestMapping("/oss-service")
@CrossOrigin
public class OssController {
    @Resource
    private OssService ossService;

    @PostMapping("/uploadFile")
    public R uploadFile(MultipartFile file){
        String url = ossService.uploadFile(file);
        return R.ok().data("url",url);
    }
}
