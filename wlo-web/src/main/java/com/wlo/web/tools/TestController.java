package com.wlo.web.tools;

import com.wlo.commons.swagger.Result;
import com.wlo.model.LoginUser;
import com.wlo.service.ILoginUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.HashMap;
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
@RequestMapping("/test")
@Api(value = "测试api", description = "用户测试")
public class TestController {
	final static Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	ILoginUserService service;

	@ApiOperation(value = "获取个人信息")
	@ApiResponse(code = 200, message = "success", response = Result.class)
	@GetMapping(value = "/get/{id}")
	@ResponseBody
	public Map<String, Object> get(@ApiParam(required = true, value = "唯一标识") @PathVariable Integer id)
			throws Exception {
		TestRedisFlag();
		logger.info("查询开始");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		try {
			LoginUser user = service.getLoginUserById(id);
			logger.info("name=" + user.getName());
			flag = true;
			map.put("name", user.getName());
		} catch (Exception e) {
			map.put("flag", flag);
			map.put("msg", e.getMessage());
		}
		logger.info("查询结束 状态：" + flag + "");
		return map;
	}
	@GetMapping(value = "/get2/{id}")
	@ResponseBody
	public Map<String, Object> get2(@ApiParam(required = true, value = "唯一标识") @PathVariable Integer id)
			throws Exception {
		TestRedisFlag();
		logger.info("查询开始");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		try {
			LoginUser user = service.getLoginUserById2(id);
			logger.info("name=" + user.getName());
			flag = true;
			map.put("name", user.getName());
		} catch (Exception e) {
			map.put("flag", flag);
			map.put("msg", e.getMessage());
		}
		logger.info("查询结束 状态：" + flag + "");
		return map;
	}

	public void TestRedisFlag() {
		// 连接本地的 Redis 服务
		Jedis jedis = new Jedis("localhost");
		logger.info("连接成功");
		// 查看服务是否运行
		logger.info("服务正在运行: " + jedis.ping() + "");
	}

	@ApiOperation(value = "以id删除个人信息")
	@ApiResponse(code = 200, message = "success", response = Result.class)
	@GetMapping(value = "/de/{id}")
	@ResponseBody
	public Map<String, Object> deleteById(@ApiParam(required = true, value = "唯一标识") @PathVariable Integer id)
			throws Exception {
		TestRedisFlag();
		logger.info("查询开始");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		try {
			flag = service.delete(id);
		} catch (Exception e) {
			map.put("flag", flag);
			map.put("msg", e.getMessage());
		}
		logger.info("查询结束 状态：" + flag + "");
		return map;
	}

	@ApiOperation(value = "创建个人信息")
	@ApiResponse(code = 200, message = "success", response = Result.class)
	@GetMapping(value = "/in")
	@ResponseBody
	public Map<String, Object> setLoginUser(@ApiParam(required = true, value = "姓名") String name,
			@ApiParam(required = true, value = "密码") String password,
			@ApiParam(required = true, value = "手机") String phone) throws Exception {
		TestRedisFlag();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		/*
		 * id = request.getParameter("id"); name = request.getParameter("name");
		 * password = request.getParameter("password");
		 */
		try {
			LoginUser user = new LoginUser();
			user.setName(name);
			user.setPassword(password);
			user.setUpdatedate(new Date());
			user.setPhone(phone);
			flag = service.insert(user);
		} catch (Exception e) {
			map.put("msg", e.getMessage());
		}
		map.put("flag", flag);
		return map;
	}

	@ApiOperation(value = "修改个人信息")
	@ApiResponse(code = 200, message = "success", response = Result.class)
	@GetMapping(value = "/up")
	@ResponseBody
	public Map<String, Object> updateLoginUser(@ApiParam(required = true, value = "唯一标识") String id,
			@ApiParam(required = true, value = "姓名") String name,
			@ApiParam(required = true, value = "密码") String password,
			@ApiParam(required = true, value = "手机") String phone) throws Exception {
		TestRedisFlag();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		logger.info("修改开始。。。");
		try {
			logger.info("修改中。。。。");
			service.update(Integer.valueOf(id), name, password, phone);
			flag=true;
		} catch (Exception e) {
			map.put("msg", e.getMessage());
		}
		logger.info("修改结束。。。");
		map.put("flag", flag);
		return map;
	}

	/**
	 * 测试hello
	 * @return
	 */
	@ApiOperation(value = "测试hello")
	@ApiResponse(code = 200, message = "success", response = Result.class)
	@GetMapping(value = "/hello")
	public ModelAndView hello(Model model) {
		ModelAndView view =new ModelAndView();
		view.setViewName("hello");
		view.addObject("name","ck");
		return view;
	}

	/**
	 * 测试hello
	 * @return
	 */
	@ApiOperation(value = "测试hello")
	@ApiResponse(code = 200, message = "success", response = Result.class)
	@GetMapping(value = "/hello2")
	public String hello2(Model model) {
		model.addAttribute("name", "Dear");
		return "hello";
	}


}
