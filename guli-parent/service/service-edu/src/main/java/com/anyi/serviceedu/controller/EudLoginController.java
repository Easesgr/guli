package com.anyi.serviceedu.controller;

import com.anyi.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author 安逸i
 * @version 1.0
 */
@Api(tags = "后台登录")
@RestController
@CrossOrigin
@RequestMapping("/edu/user")
public class EudLoginController {
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","安逸i").data("avatar","https://img2.baidu.com/it/u=1902606201,2793070708&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800");
    }
}
