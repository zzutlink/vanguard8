package com.vanguard8.interceptor;

import com.vanguard8.common.SessionName;
import com.vanguard8.common.SessionUser;
import com.vanguard8.framework.service.DeptstaService;
import com.vanguard8.framework.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private LogService logService;

    @Autowired
    private DeptstaService deptstaService;

    private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    //在控制器执行前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle)
            throws Exception {
        logger.debug("执行preHandle方法-->01");
        String queryString = request.getQueryString();
        String servletPath = request.getServletPath();
        if (queryString != null && !queryString.equals("")) {
            servletPath = servletPath.concat("?").concat(queryString);
        }
        logger.debug(servletPath);

        //重新登录路径
        String redirectUrl = request.getContextPath().concat("/login/index");
        String rlimitUrl = request.getContextPath().concat("/login/rlimit");

        //如果session存在，表示已登录，那么检查是否具有相应模块权限,更严谨的应把每个模块的Action放到表中进行验证
        //若不存在，则没有登录，直接让重新登录

        HttpSession session = request.getSession();
        boolean sessionFlag = false;
        boolean rightFlag = false;
        if (session != null && session.getAttribute(SessionName.USER) != null) {
            sessionFlag = true;
            SessionUser sessionUser = (SessionUser) session.getAttribute(SessionName.USER);
            rightFlag = deptstaService.checkActionRight(sessionUser.getDsId(), servletPath);
            //越权访问，记录到数据库
            if(!rightFlag){
                logService.saveLog(sessionUser.getUserId(),sessionUser.getUserName(),"NoRight",servletPath);
            }
        }

        //如果session不存在则退出进行登录
        //如果session存在，但权限受限，则只处理当前页面进行提示
        if (sessionFlag) {
            if(!rightFlag) {
                logger.debug("无权限页面跳转……");
                rlimitUrl = rlimitUrl.concat("?url=").concat(servletPath);
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    //告诉ajax我是重定向
                    logger.debug("无权限ajax重定向写入……");
                    response.setHeader("REDIRECT", "REDIRECT2");
                    //告诉ajax我重定向的路径
                    response.setHeader("CONTEXTPATH", rlimitUrl);
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.sendRedirect(rlimitUrl);
                }
            }
        }
        else {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                //告诉ajax我是重定向
                logger.debug("ajax重定向写入……");
                response.setHeader("REDIRECT", "REDIRECT");
                //告诉ajax我重定向的路径
                response.setHeader("CONTEXTPATH", redirectUrl);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                logger.debug("普通登录页面跳转……");
                response.sendRedirect(redirectUrl);
            }
        }
        return sessionFlag&&rightFlag;
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
