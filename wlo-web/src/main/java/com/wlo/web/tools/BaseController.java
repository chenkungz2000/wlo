package com.wlo.web.tools;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    // 无效
    // ServletRequestAttributes attr = (ServletRequestAttributes)
    // RequestContextHolder.currentRequestAttributes();
    // HttpServletRequest request=attr.getRequest();
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

}
