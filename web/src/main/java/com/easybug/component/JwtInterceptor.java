package com.easybug.component;

import com.easybug.annocation.Login;
import com.easybug.cache.BlackListCacheManager;
import com.easybug.common.SysException;
import com.easybug.config.JWT;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    @Autowired
    private JWT jwt;
    @Autowired
    private BlackListCacheManager cacheManager;
    /**
     * 无需拦截的页面路径
     */
    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("拦截器拦截...");
        Login annocation;
        //去除前缀的key
        String rk;
        //存放带有前缀的key的集合
        Set<byte[]> keys;
        //存放value的集合
        Set<String> values = new HashSet<String>();
        logger.info(handler.toString());
        if(handler instanceof HandlerMethod){
            annocation = ((HandlerMethod)handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }
        if(annocation==null){
            return true;
        }
        String token = getToken(request);
        if(StringUtils.isBlank(token)){
            throw new SysException(jwt.getHeader()+"失效,请重新登陆",401);
        }
        //判断token是否在黑名单
        Cache cache = cacheManager.getCache("blackList");
        keys = cache.keys();
        for(byte[] k : keys){
            String tk = new String(k);
            rk = tk.substring(cacheManager.getPrefix().length(),tk.length());
            Object o = cache.get(rk);
           values.add((String)o);
        }
        for(String t:values){
            if(token.equals(t)){
                throw new SysException(jwt.getHeader()+"失效,请重新登陆",401);
            }
        }
        //获取签名信息
        Claims claims = jwt.getClaimByToken(token);
        //判断签名是否存在过期
        boolean b = claims==null || claims.isEmpty() || jwt.isTokenExpired(claims.getExpiration());
        if(b){
            throw new SysException(jwt.getHeader()+"失效,请重新登陆",401);
        }
        //将签名中获取的用户信息放入request中
        request.setAttribute(USER_KEY,claims.getSubject());
        return true;
    }
    private String getToken(HttpServletRequest request){
        String token = request.getHeader(jwt.getHeader());
        if(StringUtils.isBlank(token)){
            token = request.getParameter(jwt.getHeader());
        }
        return token;
    }

}
