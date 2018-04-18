package com.wlo.web.tools;


import com.wlo.commons.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/caches")
public class RedisCacheController {

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/test")
    public String test() {
        redisUtils.set("123", "hello world");
        System.out.println("进入了方法");
        String string = redisUtils.get("123").toString();
        return string;
    }

}