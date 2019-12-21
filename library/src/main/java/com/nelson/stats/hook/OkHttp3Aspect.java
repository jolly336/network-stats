package com.nelson.stats.hook;

import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * AOP OkHttp {@link OkHttpClient#eventListenerFactory()}
 *
 * Created by Nelson on 2019-11-25.
 */
@Aspect
public class OkHttp3Aspect {

    /**
     * Field -> get  FieldSignature @注解 访问权限 变量类型 类型.成员变量名
     *
     * <a href="https://www.jianshu.com/p/f90e04bcb326">AOP 之 AspectJ 全面剖析 in Android</a>
     */
    @Around("get(* okhttp3.OkHttpClient.eventListenerFactory)")
    public Object aroundEventFactoryGet(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.proceed();
        if (target instanceof EventListener.Factory) {
            EventListener.Factory factory = (EventListener.Factory) target;
            return OkHttpEventListenerFactoryWrapper.wrap(factory);
        }
        return target;
    }
}
