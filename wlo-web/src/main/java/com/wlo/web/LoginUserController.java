package com.wlo.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wlo.model.LoginUser;
import com.wlo.service.ILoginUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ck
 * @since 2018-01-06
 */
@Controller
@RequestMapping("/login")

public class LoginUserController {
    final static Logger logger = LoggerFactory.getLogger(LoginUserController.class);
    @Autowired
    ILoginUserService service;


    @GetMapping(value = "list")
    @ResponseBody
    public Map<String, Object> List() throws Exception {
        logger.info("查询开始");
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        try {
            List<LoginUser> list = service.getListBySQL();
            map.put("list", list);
            flag = true;
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }
        map.put("flag", flag);
        return map;
    }

    @GetMapping(value = "get")
    @ResponseBody
    public Map<String, Object> List2() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        try {
            List<LoginUser> list =
            service.getLoginUserListById(1);
            map.put("list", list);
            flag = true;
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }
        map.put("flag", flag);
        return map;
    }
    @GetMapping(value = "list2")
    @ResponseBody
    public Map<String, Object> List3() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        try {
            List<LoginUser> list= service.selectList(new EntityWrapper<LoginUser>());
            map.put("list", list);
            flag = true;
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }
        map.put("flag", flag);
        return map;
    }

}
