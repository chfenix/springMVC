package cn.solwind.admin.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
* @author Zln
* @version 2018-11-23 15:38
* 
*/

public class ApplicationInitializer
		extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	Logger log = LogManager.getLogger();

	//配置Spring根上下文配置类
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class};
    }
  
  	//配置Spring Web上下文配置类
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfig.class};
    }
 
    //配置在什么路径下使用Spring的DispatcherServlet。等价于给DispatchServlet指定对应的url-mapping
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
  //配置与Spring相关的Filter。这里规定Spring MVC的编码为UTF-8。
    @Override
    protected Filter[] getServletFilters() {
    	log.info("register servletFilters..");
    	
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] { characterEncodingFilter };
    }
 
    /**
     * 注册 ShiroFilter，用来过滤所有URL请求，此处 addFilter 所指定的 Filter 的名字要与 ShiroConfig 中的 ShiroFilter 名字一致
     * @param servletContext
     */
    @Override
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        log.info("register shiroFilter...");

        FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("shiroFilter", new DelegatingFilterProxy());
        shiroFilter.setInitParameter("targetFilterLifecycle", "true");
        shiroFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");


        return shiroFilter;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	log.info("onStartup...");
        super.onStartup(servletContext);
        
        servletContext.addFilter("encodingFilter", new CharacterEncodingFilter("UTF-8", true))
        .addMappingForUrlPatterns(null, false, "/*");
    }
}
