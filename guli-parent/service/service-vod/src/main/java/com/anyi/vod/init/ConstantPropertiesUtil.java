package com.anyi.vod.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 安逸i
 * @version 1.0
 * 初始化服务参数
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${aliyun.vod.file.keyid}")
    private String keyId;
    @Value("${aliyun.vod.file.keysecret}")

    private String keySecret;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET

            ;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
