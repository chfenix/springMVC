package cn.solwind.admin.filter;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import cn.solwind.admin.common.Constants;
import cn.solwind.admin.common.LoginSummary;
import cn.solwind.admin.entity.SysFunction;
import cn.solwind.framework.common.CacheKey;
import cn.solwind.framework.redis.Redis;

/**
* @author Zln
* @version 2019-01-04 11:35
* 
* 资源权限检查过滤器
*/

public class ResourceCheckFilter extends AccessControlFilter {
	
	Logger log = LogManager.getLogger();
	
	Redis redis = Redis.getInstance();

	@SuppressWarnings("unchecked")
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response,
			Object mappedValue) throws Exception {
		log.debug("onPreHandle...");
		// 获取用户菜单信息
		Subject subject = SecurityUtils.getSubject();
		LoginSummary summary = (LoginSummary)subject.getPrincipal();
		List<SysFunction> listMenu = (List<SysFunction>)redis.get(CacheKey.USER_MENU_PRFIX + summary.getId());
		request.setAttribute("menu", listMenu);
		
		return super.onPreHandle(request, response, mappedValue);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) throws Exception {
		log.debug("isAccessAllowed...");
        String url = getPathWithinApplication(request);
        log.debug("当前用户正在访问的 url => " + url);
        
        // 获取当前url对应的function
		SysFunction objFunction = (SysFunction)redis.hGet(CacheKey.SYS_FUNCTION, url);
		
		String funcCode = "unknowFunc";
		if(objFunction != null) {
			if(objFunction.getType().equals(Constants.FUNC_TYPE_FUNC)) {
				// 如当前操作的为功能，则菜单为parentId
				request.setAttribute("currMenu", objFunction.getParentId());
			}
			else {
				request.setAttribute("currMenu", objFunction.getId());
			}
			funcCode = objFunction.getCode();
		}
		
		if("/".equals(url)) {
			// 如果是首页，不做权限检查
			return true;
		}
		else {
			Subject subject = getSubject(request, response);
//			return subject.isPermitted(funcCode);
			// FIXME 调试阶段暂不做权限控制，发布之前需要开放
	        return true;
		}
        
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		log.debug("onAccessDenied...");
		
		// 无权限直接跳转至401
		WebUtils.issueRedirect(request, response, "/unauthorized.do");
		
		return false;
	}

}
