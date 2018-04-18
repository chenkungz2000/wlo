package com.wlo.commons.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUtils {

	/**
	 * 获取Token
	 * 
	 * @param request
	 * @param response
	 * @param key
	 * @return
	 */
	public Map<String, Object> getToken(HttpServletRequest request, HttpServletResponse response, String key) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String keyR = "gzgigl";
		try {
			if (!key.equals(keyR)) {
				throw new Exception("key error");
			}

			String token = getTokenToCache(key);
			map.put("token", token);
		} catch (Exception ex) {
			map.put("message", ex.getMessage());
		}
		return map;

	}

	public Map<String, Object> getTokenToCache(HttpServletRequest request, String openidToken, HttpServletResponse response, String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		String keyR = "gzgigl";
		try {
			// token校验
			String sessionResearchToken = (String) request.getSession(true).getAttribute("openidToken");
			request.getSession(true).removeAttribute("openidToken");
			if (StringUtils.isBlank(sessionResearchToken) || !sessionResearchToken.equals(openidToken)) {
			}
			String tokenCache = getTokenToCache(keyR);
			if (!token.equals(tokenCache)) {
				throw new Exception("token error");
			}
			map.put("token", token);
		} catch (Exception ex) {
			map.put("message", ex.getMessage());
		}
		return map;

	}

	public String getCookie(HttpServletRequest request) throws Exception {
		String value = "";
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies == null) {
			} else {
				for (int i = 0; i < cookies.length; i++) {
					// 获得具体的Cookie
					Cookie cookie = cookies[i];
					// 获得Cookie的名称
					if (cookie.getName().equals("randomstring")) {
						value = cookie.getValue();
					} else {
					}
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return value;
	}

	private Map<String, Object> setCookie(HttpServletRequest request, HttpServletResponse response) throws SocketException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		try {
			if (getCookie(request).length() == 0) {
				String randomstring = getRandomString();
				Cookie cookie = new Cookie("randomstring", randomstring);
				// cookie.setMaxAge(3600);
				// 设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
				cookie.setPath("/");
				response.addCookie(cookie);
				map.put("cookie", randomstring);
			} else {
				map.put("cookie", getCookie(request));

			}
			flag = true;
			map.put("flag", flag);
		} catch (Exception e) {
			e.getMessage();
			map.put("flag", flag);
		}
		return map;
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 存到cookie
	 * 
	 * @param request
	 * @param response
	 * @param key
	 * @return String
	 */
	public String getTokenToCookie(HttpServletRequest request, HttpServletResponse response, String key) {
		String keyR = "gzgigl";
		String cookietoken = null;
		if (key.equals(keyR)) {
			cookietoken = getRandomString();
			Cookie cookie = new Cookie("cookietoken", cookietoken);
			cookie.setPath("/");
			// 失效时间为两小时
			cookie.setMaxAge(7200);
			response.addCookie(cookie);
		}
		return cookietoken;
	}

	/**
	 * 保存Cookie
	 * 
	 * @param response
	 * @param key
	 * @param val
	 */
	public static void saveCookie(HttpServletResponse response, String key, String val) {
		Cookie cookie = new Cookie(key, val);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}

	/**
	 * 删除指定cookie
	 * 
	 * @param response
	 * @param cookie
	 */
	public void removeCookie(HttpServletResponse response, Cookie cookie) {
		cookie.setPath("/");
		cookie.setMaxAge(0);
		cookie.setValue("");
		response.addCookie(cookie);
	}

	/**
	 * 删除指定的cookie
	 * 
	 * @param response
	 * @param cookies
	 */
	public void removeCookie(HttpServletResponse response, List<Cookie> cookies) {
		for (Cookie cookie : cookies)
			removeCookie(response, cookie);
	}
    public static String getCookie(HttpServletRequest request,String name) {
        String value = "";
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                value = null;
            } else {
                for (int i = 0; i < cookies.length; i++) {
                    // 获得具体的Cookie
                    Cookie cookie = cookies[i];
                    // 获得Cookie的名称
                    if (cookie.getName().equals(name)) {
                        value = cookie.getValue();
                    } else {
                        value = null;
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return value;
    }

    public static void setCookie(HttpServletResponse response, String val,String name) {
        Cookie cookie = new Cookie(name, val);
        cookie.setPath("/");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
    }

	public int getRandom(int i) {
		return (int) Math.round(Math.random() * (i));
	}

	/**
	 * 获得随机数
	 * 
	 * @return String
	 */
	public String getRandomString() {
		String randomStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int length = 50;
		StringBuffer sb = new StringBuffer();
		int len = randomStr.length();
		for (int i = 0; i < length; i++) {
			sb.append(randomStr.charAt(getRandom(len - 1)));
		}
		return sb.toString();
	}

	/**
	 * 第三方访问验证接口服务
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @return boolean
	 */
	public boolean validate(HttpServletRequest request, String token) {
		token=request.getParameter("token");
		String cookietoken = null;
		Cookie[] cookie = request.getCookies();
		if (token == null && cookie.length == 0) {
			System.out.println("token不可为空或cookie无数据");
		}
		for (int i = 0; i < cookie.length; i++) {
			Cookie cook = cookie[i];
			if (cook.getName().equalsIgnoreCase("cookietoken")) {
				cookietoken = cook.getValue().toString();
			}
		}
		if (cookietoken != null && cookietoken.equals(token)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 存到session
	 * 
	 * @param request
	 * @param response
	 * @param key
	 * @return String
	 */
	public String getTokenToSession(HttpServletRequest request, HttpServletResponse response, String key) {
		key=request.getParameter("key");
		String keyR = "gzgiwl";
		String sessiontoken = null;
		if (key.equals(keyR)) {
			sessiontoken = getRandomString();
			HttpSession session = request.getSession();
			session.setAttribute("sessiontoken", sessiontoken);
			// session失效时间为40分钟
			session.setMaxInactiveInterval(40 * 60);
		}
		return sessiontoken;
	}

	// TODO 使用缓存

	public String getTokenToCache(String key) throws IOException {
		Map<String, Object[]> tokenCache = new HashMap<String, Object[]>();
		key = key.hashCode() + "_";
		String token = "";
		Object[] obj = tokenCache.get(key);
		if (obj != null) {
			Date date = (Date) obj[0];
			if (date != null && System.currentTimeMillis() - date.getTime() >= 30 * 60 * 1000)
				obj = null;
		}

		if (obj == null) {
			token = getRandomString();
			obj = new Object[] { new Date(), token };
			tokenCache.put(key, obj);
		} else {
			token = (String) obj[1];
		}
		return token;
	}
}
