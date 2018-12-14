package com.easybug.cache;

import com.easybug.common.SysException;
import com.easybug.model.TokenBlackList;
import jdk.nashorn.internal.parser.Token;
import org.apache.shiro.cache.Cache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.Date;

@Aspect
@PropertySource("classpath:cache.properties")
public class BlackListAop {
    @Autowired
    private BlackListCacheManager cacheManager;
    @Value("${blackListCache.cacheName}")
    private String cacheName;
    @Pointcut("execution(* com.easybug.service.token.TokenServiceImpl.insertTokenList(..))")
    public void putPointCut(){}
    @Around("putPointCut()")
    public Integer putRedisCache(ProceedingJoinPoint jp){
        Object id = null;
        try {
            id = jp.proceed();
            Object[] args = jp.getArgs();
            for (Object o:args){
                if(o instanceof  TokenBlackList) {
                    TokenBlackList t = (TokenBlackList) o;
                    Cache cache = cacheManager.getCache(cacheName);
                    //获取过期剩余秒数
                    Long ex = (t.getExpiredTime().getTime() - System.currentTimeMillis()) / 1000;
                    cacheManager.setExpire(ex.intValue());
                    cache.put(t.getId().toString(), t.getToken());
                }
            }
        } catch (Throwable throwable) {
            throw new SysException("黑名单切面出现问题！",500);
        }
        return (Integer)id;
    }
}
