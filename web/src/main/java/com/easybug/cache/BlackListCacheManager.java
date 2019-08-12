package com.easybug.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@PropertySource("classpath:cache.properties")
public class BlackListCacheManager implements CacheManager {
    private ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>();
    @Autowired
    private RedisManager redisManager;
    @Value("${blackListCache.redis.prefix}")
    private String prefix;
    @Override
    public <K, V> Cache<K, V> getCache(String var1)  {
        Cache cache =  this.caches.get(var1);
        redisManager.init();
        if(cache==null){
            cache = new RedisCache(redisManager,prefix);
            this.caches.put(var1,cache);
        }
        return cache;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix(){return this.prefix;};
    public void setExpire(Integer ex){
        this.redisManager.setExpire(ex);
    }
}
