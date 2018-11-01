package com.vanguard8.framework.interceptor;

import com.vanguard8.common.SessionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

            private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

            //在控制器执行前调用
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle)
            throws Exception{
                logger.debug("执行preHandle方法-->01");
                logger.debug(request.getServletPath());
                HttpSession session = request.getSession();
                if(session==null||session.getAttribute(SessionName.USER)==null) {
                    //没有通过拦截器，返回登录页面
                    logger.debug("未找到对应的session值");
                    response.sendRedirect(request.getContextPath() + "/login/index");
                    return false;
        }
        return true;
    }

    //在后端控制器执行后调用
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        logger.info("执行postHandle方法-->02");
    }
    //整个请求执行完成后调用
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        logger.info("执行afterCompletion方法-->03");
    }
}
