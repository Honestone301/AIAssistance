package com.aiassistance.Aspect;

import com.aiassistance.Annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
// 导入HttpServletRequest，在Spring3.x之后使用的是jakarta，而不是javax了
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志切面类
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 切点：使用@Log注解的方法
     */
    @Pointcut("@annotation(com.aiassistance.Annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 前置通知
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            log.info("URL: {}, HTTP方法: {}, IP: {}",
                    request.getRequestURL(),
                    request.getMethod(),
                    request.getRemoteAddr());
        }

        // 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);

        if (logAnnotation != null) {
            log.info("模块: {}, 操作: {}, 描述: {}",
                    logAnnotation.module(),
                    logAnnotation.type(),
                    logAnnotation.description());
        }

        log.info("类名: {}, 方法名: {}, 参数: {}",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 环绕通知
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.info("方法执行时间: {}ms", endTime - startTime);
        log.info("方法返回值: {}", result);

        return result;
    }

    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "throwable")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.error("方法执行异常: {}, 异常信息: {}",
                joinPoint.getSignature().getName(),
                throwable.getMessage());
    }
}
