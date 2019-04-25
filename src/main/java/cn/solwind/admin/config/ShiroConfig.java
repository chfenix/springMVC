package cn.solwind.admin.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import cn.solwind.admin.controller.login.ShiroRealm;
import cn.solwind.admin.filter.ResourceCheckFilter;
import cn.solwind.shiro.RedisCacheManager;
import cn.solwind.shiro.RedisSessionDao;
import cn.solwind.shiro.ShiroSessionManager;

/**
 * @author Zln
 * @version 2018-12-20 11:08
 * 
 * Shiro配置
 */
@Configuration
@Import(RootConfig.class)
public class ShiroConfig {
	 
	@Bean(name = "ShiroRealm")
	public ShiroRealm getShiroRealm() {
		ShiroRealm authRealm = new ShiroRealm();
		authRealm.setCacheManager(redisCacheManager());
		authRealm.setCachingEnabled(true);
		authRealm.setAuthenticationCachingEnabled(false);
		authRealm.setAuthorizationCachingEnabled(true);
		return authRealm;
	}
 
	
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
    	RedisCacheManager shiroRedisCacheManager = new RedisCacheManager();
        return shiroRedisCacheManager;
    }

	
	@Bean
    public RedisSessionDao sessionDao() {
        RedisSessionDao sessionDao = new RedisSessionDao();
        sessionDao.setCacheManager(redisCacheManager());
        return sessionDao;
    }

	
	@Bean
	public DefaultWebSessionManager sessionManager() {
		ShiroSessionManager sessionManager = new ShiroSessionManager();
		sessionManager.setSessionDAO(sessionDao());
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		// 设置session过期时间为1小时(单位：毫秒)，默认为30分钟
		sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setCacheManager(redisCacheManager());
		sessionManager.setSessionValidationSchedulerEnabled(false);
		Cookie sessionIdCookie = sessionManager.getSessionIdCookie();
		sessionIdCookie.setPath("/hyms");
		sessionIdCookie.setName("csid");
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		return sessionManager;
	}
	
	@Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 暂时不需使用redis管理session 
//        securityManager.setCacheManager(redisCacheManager());
//        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(getShiroRealm());
        return securityManager;
    }
	 
 
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		// 安全管理器
		shiroFilter.setSecurityManager(securityManager);
		// 默认的登陆访问url
		shiroFilter.setLoginUrl("/toLogin.do");
		// 登陆成功后跳转的url
//		shiroFilter.setSuccessUrl("/index.do");
		// 没有权限跳转的url
//		shiroFilter.setUnauthorizedUrl("/unauthorized.do");
		
		// 资源权限过滤器
        Map<String, Filter> filterMap = shiroFilter.getFilters();
        ResourceCheckFilter resourceCheckFilter = new ResourceCheckFilter();
        filterMap.put("resc", resourceCheckFilter);
        shiroFilter.setFilters(filterMap);

		/**
		 * 配置shiro过滤器链，从前往后验证 1、anon 不需要认证 2、authc 需要认证 3、user
		 * 验证通过或RememberMe登录的都可以 当应用开启了rememberMe时,用户下次访问时可以是一个user,
		 * 但不会是authc,因为authc是需要重新认证的 顺序从上到下,优先级依次降低
		 */

		// 要用LinkedHashMap，因为filter必须是有序的
		Map<String, String> filterChainMap = new LinkedHashMap<>();
		filterChainMap.put("/toLogin.do", "anon");		// 显示登录页面不做拦截
		filterChainMap.put("/login.do", "anon");		// 登录本身不做拦截
		filterChainMap.put("/captcha.do", "anon");		//图片验证码(kaptcha框架)
		filterChainMap.put("/callback/**", "anon");		// 回调不做拦截
		filterChainMap.put("/manual/**", "anon");		// 手工触发url不做拦截
		filterChainMap.put("/api/**", "anon");			// api不做拦截
		filterChainMap.put("/resources/**", "anon");	// 静态资源不做拦截
		filterChainMap.put("/unauthorized.do", "authc");	// 无权限页面，只做登录拦截
		filterChainMap.put("/logout.do", "authc");		// 登出只做登录拦截
		filterChainMap.put("/updatePwd.do", "authc");		// 修改密码只做登录拦截
		filterChainMap.put("/**", "authc,resc");		// 全都做权限拦截
		shiroFilter.setFilterChainDefinitionMap(filterChainMap);
		return shiroFilter;
	}
	
	@Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
