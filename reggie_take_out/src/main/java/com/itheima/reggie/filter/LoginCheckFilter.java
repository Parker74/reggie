package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.commen.BaseContext;
import com.itheima.reggie.commen.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/12 10:57
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        log.info("拦截到请求:{}", uri);

        //可以供用户随意查看的一些静态资源
        String[] urls = {"/employee/login", "/employee/logout", "/backend/**", "/front/**", "/user/sendMsg", "/user/login"};

        boolean check = check(urls, uri);

        if (check) {
            log.info("本次请求{}不需要处理", uri);
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Object employee = session.getAttribute("employee");

        //4-2、判断登录状态，如果已登录，则直接放行
        if (employee != null) {
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("employee"));
            Long employee1 = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employee1);
            filterChain.doFilter(request, response);
            return;
        }
        log.info("用户未登录");

        //4-2、判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }


    /**
     * 路径匹配 检查本次请求是否需要放行
     *
     * @param urls
     * @param uri
     * @return
     */
    public boolean check(String[] urls, String uri) {
        for (String url : urls) {
            boolean flag = PATH_MATCHER.match(url, uri);
            if (flag) {
                return true;
            }
        }
        return false;
    }
}
