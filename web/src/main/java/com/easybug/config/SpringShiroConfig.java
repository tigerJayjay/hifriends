package com.easybug.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.easybug.realm.AuthRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SpringShiroConfig {
	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private Integer redisPort;
	@Value("${spring.redis.timeout}")
	private Integer timeOut;
	@Value("${shiro.algorithmName}")
	private String algorithmName;
	@Value("${shiro.hashIterations}")
	private Integer iterator;
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);
		// 配置登录的url和登录成功的url
		bean.setLoginUrl("/main");
		bean.setSuccessUrl("/Home");
		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		//配置不会被拦截的链接
		filterChainDefinitionMap.put("/**", "anon");
		/*filterChainDefinitionMap.put("/js", "anon");//放开静态资源
		filterChainDefinitionMap.put("/css", "anon");//放开静态资源
		filterChainDefinitionMap.put("/swagger*","anon");//放开swagger
		filterChainDefinitionMap.put("/test/**","anon");//放开websocket
		filterChainDefinitionMap.put("/main*", "anon"); // 表示可以匿名访问
		filterChainDefinitionMap.put("/user/dologin", "anon");//执行登录操作，RequestMapping中对应的路径
		filterChainDefinitionMap.put("/resources/**", "anon");//放开静态资源
		filterChainDefinitionMap.put("/logout*", "anon");//退出登录操作
		//剩余请求需要身份验证登录
		filterChainDefinitionMap.put("/*", "authc");// 表示需要认证才可以访问
		filterChainDefinitionMap.put("/**", "authc");// 表示需要认证才可以访问
		filterChainDefinitionMap.put("/*.*", "authc");*/
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}

	/**
	 *配置核心安全事务管理器
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		System.err.println("--------------shiro已经加载----------------");
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm());
		manager.setCacheManager(redisCacheManager());
		manager.setSessionManager(sessionManager());
		return manager;
	}

	/**
	 *配置自定义的权限登录器
 	 */
	@Bean(name = "authRealm")
	public AuthRealm authRealm() {
		AuthRealm authRealm = new AuthRealm();
		authRealm.setCredentialsMatcher(getMatcher());
		authRealm.setCacheManager(redisCacheManager());
		return authRealm;
	}
     @Bean
     public RedisCacheManager  redisCacheManager() {
             RedisCacheManager cacheManager = new RedisCacheManager();
             cacheManager.setRedisManager(new RedisManager());
             return cacheManager;
     }
     @Bean
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(redisHost);
		redisManager.setPort(redisPort);
		redisManager.setTimeout(timeOut);
		return redisManager;
	}

	/**
	 * Session Manager
	 * 使用的是shiro-redis开源插件
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO());
		return sessionManager;
	}

	/**
	 * RedisSessionDAO shiro sessionDao层的实现 通过redis
	 * 使用的是shiro-redis开源插件
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}
	/**
	 * 加static 否则properties为null
	 * @return
	 */
	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
		@Qualifier("securityManager") SecurityManager manager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}

	/**
	 * 配置加密
	 */

	public HashedCredentialsMatcher getMatcher(){
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName(algorithmName);
		matcher.setHashIterations(iterator);
		return matcher;
	}
}
