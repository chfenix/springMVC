package cn.solwind.admin.controller.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import cn.solwind.admin.common.LoginSummary;
import cn.solwind.admin.dto.MenuDTO;
import cn.solwind.admin.entity.SysFunction;
import cn.solwind.admin.entity.SysUser;
import cn.solwind.admin.service.user.FunctionService;
import cn.solwind.admin.service.user.SysUserService;
import cn.solwind.admin.utils.PasswordHelper;
import cn.solwind.framework.common.CacheKey;
import cn.solwind.framework.redis.Redis;
import cn.solwind.shiro.CaptchaUsernamePasswordToken;
import cn.solwind.shiro.IncorrectCaptchaException;

/**
* @author Zln
* @version 2018-12-20 11:17
* 
* Shiro Realm实现
*/

public class ShiroRealm extends AuthorizingRealm {

	Logger log = LogManager.getLogger();
	
	Redis redis = Redis.getInstance();
	
	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	FunctionService functionService;
	
	/**
     * 首先执行这个登录验证
     *
     * @param token 登录用户
     * @return
     * @throws AuthenticationException
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		log.info("ShiroRealm doGetAuthenticationInfo...");
		
		CaptchaUsernamePasswordToken authToken = (CaptchaUsernamePasswordToken) token;
		String userName = authToken.getUsername();
		String password = String.valueOf(authToken.getPassword());
		String captcha = authToken.getCaptcha();
		
		// 检查验证码是否正确
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		
		String sessionCaptcha = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (!captcha.equalsIgnoreCase(sessionCaptcha)) {
			// 验证码不正确
			throw new IncorrectCaptchaException("Wrong captcha!");
		}
		
		String md5Password = PasswordHelper.generateUserPwd(userName, password);
		SysUser user = sysUserService.getUserByUserName(userName);
		
		if (user == null) {
			// 用户名错误
			throw new UnknownAccountException(String.format("Account [%s] not exists!", userName));
		}

		if (user.getPassword() != null
				&& !md5Password.equalsIgnoreCase(user.getPassword())) {
			 throw new IncorrectCredentialsException ("Wrong password!");
		}
		
		// 设置登录时间
		sysUserService.updateLoginDate(user.getId());
		
		// 登录信息
		LoginSummary summary = new LoginSummary();
		summary.setId(user.getId());
		summary.setName(user.getName());
		summary.setUserName(user.getUserName());
		summary.setLoginTime(new Date());
		
		// 保存LoginSummary至Principals
		SimpleAuthenticationInfo simpleAuthInfo = new SimpleAuthenticationInfo(summary,password,getName());
		
		// 读取用户权限保存至Redis
		List<SysFunction> listFunc = functionService.getUserFunction(summary.getId());
		if(listFunc != null) {
			Map<String, String> mapFunc = new HashMap<>();
			for (SysFunction objFunc : listFunc) {
				if(StringUtils.isNotBlank(objFunc.getAction())) {
					mapFunc.put(objFunc.getAction(), objFunc.getCode());
				}
			}
			redis.set(CacheKey.USER_PERMISSION_PRFIX + user.getId(), mapFunc, 60*60*48);
		}
		
		// 读取用户菜单，并封装成树形结构保存至Redis
		List<SysFunction> listMenu = functionService.getUserMenu(summary.getId());
		if(listMenu != null && listMenu.size() > 0) {
			List<MenuDTO> listMenuDTO = new ArrayList<>();
			for (SysFunction objFunc : listMenu) {
				// 获取一级菜单，保存至List
				if(objFunc.getParentId() == null) {
					MenuDTO objMenu = new MenuDTO();
					objMenu.setFunc(objFunc);
					List<SysFunction> listChildMenu = new ArrayList<>();	// 二级菜单
					for (SysFunction objChildMenu : listMenu) {
						// 获取二级菜单
						if(objFunc.getId().equals(objChildMenu.getParentId())) {
							listChildMenu.add(objChildMenu);
						}
					}
					if(listChildMenu.size() > 0) {
						objMenu.setChildren(listChildMenu);
					}
					listMenuDTO.add(objMenu);
				}
			}
			redis.set(CacheKey.USER_MENU_PRFIX + user.getId(), listMenuDTO, 60*60*48);
		}
		
		return simpleAuthInfo;
	}

	/**
     * 用于的权限的认证
     *
     * @param principals
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		log.info("ShiroRealm doGetAuthorizationInfo...");
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 读取用户权限
		LoginSummary summary = (LoginSummary)super.getAvailablePrincipal(principals);
		Map<String,String> mapFunc = (Map<String,String>)redis.get(CacheKey.USER_PERMISSION_PRFIX + summary.getId());
		if(mapFunc != null) {
			for (String strFuncCode : mapFunc.values()) {
				if(StringUtils.isNotBlank(strFuncCode)) {
					authorizationInfo.addStringPermission(strFuncCode);
				}
			}
		}
        return authorizationInfo;
	}

}
