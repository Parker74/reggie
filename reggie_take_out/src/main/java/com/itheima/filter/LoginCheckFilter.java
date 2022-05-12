package com.itheima.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.commen.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/12 10:57
 */
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        log.info("拦截到请求:{}",uri);
        //可以供用户随意查看的一些静态资源
        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        boolean check = check(urls, uri);

        if (check) {
            log.info("本次请求{}不需要处理",uri);
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Object employee = session.getAttribute("employee");

        if (employee != null) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString("NOTLOGIN"));
    }

    /**
     * 路径匹配 检查本次请求是否需要放行
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
