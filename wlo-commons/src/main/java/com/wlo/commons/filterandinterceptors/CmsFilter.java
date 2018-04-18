package com.wlo.commons.filterandinterceptors;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CmsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    //登陆拦截
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        System.out.print("自定义过滤器->doFilter");
		session.setMaxInactiveInterval(30 * 60);// 设置单位为秒，设置为-1永不过期
		String sessionname = session.getAttribute("name") == null ? "" : (String) session.getAttribute("name");
		if (StringUtils.isBlank(sessionname)) {
            response.sendRedirect("/login.html");
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }
}
