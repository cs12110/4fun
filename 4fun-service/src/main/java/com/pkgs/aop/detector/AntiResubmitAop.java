package com.pkgs.aop.detector;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 防止重复提交切面
 * <p/>
 *
 * @author cs12110 created at: 2019/1/7 8:34
 * <p>
 * since: 1.0.0
 */
@Aspect
@Component
public class AntiResubmitAop {

    private static Logger logger = LoggerFactory.getLogger(AntiResubmitAop.class);

    /**
     * 防止重复提交提示map
     */
    private static Supplier<Map<String, Object>> resubmitMapErrSupplier = () -> {
        Map<String, Object> errMap = new HashMap<>(2);
        errMap.put("status", "fail");
        errMap.put("msg", "anti resubmit");
        return errMap;
    };

    /**
     * 拦截所有带有{@link com.pkgs.aop.anno.AntiResubmitAnno }注解的方法
     */
    @Pointcut("@annotation(com.pkgs.aop.anno.AntiResubmitAnno)")
    public void aop() {

    }

    /**
     * 处理
     *
     * @param joinPoint join point
     */
    @Around("aop()")
    public Object handle(ProceedingJoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                HttpSession session = request.getSession(true);

                String sessionId = session.getId();
                String reqUrl = request.getRequestURI();
                String key = sessionId + "#" + reqUrl;
                Object value = session.getAttribute(key);


                if (value == null) {
                    session.setAttribute(key, System.currentTimeMillis());
                    Object result = joinPoint.proceed();
                    session.removeAttribute(key);
                    return result;
                } else {
                    logger.error("anti: {} resubmit", reqUrl);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(resubmitMapErrSupplier.get());
    }
}
