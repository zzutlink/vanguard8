package com.vanguard8.interceptor;

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
                    //获取当前请求的路径
                    //String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
                    String redirectUrl = request.getContextPath().concat("/login/index");
                    //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理 否则直接重定向就可以了
                    if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                        //告诉ajax我是重定向
                        logger.debug("ajax重定向写入……");
                        response.setHeader("REDIRECT", "REDIRECT");
                        //告诉ajax我重定向的路径
                        response.setHeader("CONTEXTPATH", redirectUrl);
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    }else{
                        logger.debug("普通登录页面跳转……");
                        response.sendRedirect(redirectUrl);
                    }
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
