package com.xmind.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmind.entity.LogEntity;
import com.xmind.security.service.LogoutService;
import com.xmind.services.log.LogService;
import com.xmind.utils.AuthUtils;
import com.xmind.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final LogService logService;
    private final ObjectMapper objectMapper;

    @Around("@within(com.xmind.annonation.Logging) || @annotation(com.xmind.annonation.Logging)")
    public Object auditMethod(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        String methodName = pjp.getSignature().toShortString();
        Object[] methodArgs = pjp.getArgs();
        String currentUsername = AuthUtils.getCurrentUser() != null ? AuthUtils.getCurrentUser().getUsername() : "Not found user";

        LogEntity logEntity = new LogEntity();
        logEntity.setUsername(currentUsername);
        logEntity.setAction(methodName);
        logEntity.setRequestParams(convertToJson(methodArgs));

        Object result = null;
        LogEntity.LogLevel logLevel = null;
        try {
            result = pjp.proceed();
            logLevel = LogEntity.LogLevel.SUCCESS;
            logEntity.setResponseBody(result != null ? convertToJson(result) : "");
        } catch (Exception ex) {
            logEntity.setResponseBody("Exception: " + ex.getClass().getName() + " - " + ex.getMessage());
            logLevel = LogEntity.LogLevel.ERROR;
            throw ex;
        } finally {
            logEntity.setCreatedDate(DateUtil.now());
            logEntity.setLogLevel(logLevel);
            logService.save(logEntity);
        }

        return result;
    }

    private String convertToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return String.valueOf(obj);
        }
    }
}
