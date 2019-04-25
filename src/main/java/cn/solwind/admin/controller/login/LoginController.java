package cn.solwind.admin.controller.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.solwind.admin.service.user.SysUserService;
import cn.solwind.shiro.CaptchaUsernamePasswordToken;
import cn.solwind.shiro.IncorrectCaptchaException;

/**
 * @author wx
 * @date 2018年11月27日 上午10:33:02
 *
 */
@Controller
public class LoginController {

	Logger log = LogManager.getLogger();

	@Autowired
	SysUserService sysUserService;
	
	/**
	 * 登录验证
	 * 
	 * @throws ServletException
	 */

	@RequestMapping(value = "/login")
	public ModelAndView login(String username, String password,
			String captcha, HttpSession session,HttpServletRequest request)
			throws IOException, ServletException {
		log.info("user:" + username + " captcha:" + captcha + " login..");
		
		ModelAndView model = new ModelAndView();
		model.addObject("username", username);
		model.addObject("password", password);

		Map<String, String> mapRet = new HashMap<>();
		
		if(StringUtils.isBlank(username)
				|| StringUtils.isBlank(captcha)
				|| StringUtils.isBlank(captcha)) {
			// 参数为空，返回登录页面
			model.addObject("result", mapRet);
			model.setViewName("login");
			return model;
		}
		
		CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(username,password.toCharArray(),false,captcha);
		
		Subject subject = SecurityUtils.getSubject();
		
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				log.info("login success! redirect to index");
				
				return new ModelAndView("redirect:/");
				
				// 由于登录后跳转登录前url会导致一些登录前是提交的操作出现问题，所以屏蔽
				/*if(WebUtils.getSavedRequest(request)== null || StringUtils.isBlank(WebUtils.getSavedRequest(request).getRequestUrl())) {
					return new ModelAndView("redirect:/");
				}
				else {
					// 跳转登录前url
					String redirectUrl = WebUtils.getSavedRequest(request).getRequestUrl();
					log.info("=====url before login:" + redirectUrl);
					if(redirectUrl.lastIndexOf("/") == 0) {
						return new ModelAndView("redirect:/");
					}
					else {
						redirectUrl = redirectUrl.substring(redirectUrl.indexOf("/", 1));
						return new ModelAndView("redirect:" + redirectUrl);
					}
				}*/
			} else {
				model.setViewName("login");
				return model;
			}
		} catch (UnknownAccountException e) {
			// 用户名错误
			mapRet.put("code", "1");
			mapRet.put("message", "用户名错误");
		} catch (IncorrectCredentialsException e) {
			// 密码错误
			mapRet.put("code", "2");
			mapRet.put("message", "密码错误");
		} catch (IncorrectCaptchaException e) {
			// 验证码错误
			mapRet.put("code", "4");
			mapRet.put("message", "验证码错误!");
		}
		model.addObject("result", mapRet);
		model.setViewName("login");
		return model;
	}

	/**
	 * 进入登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toLogin")
	public ModelAndView index() {
		log.info("toLogin");
		ModelAndView ret = new ModelAndView("login");
		return ret;
	}
	
	/**
	 * 无权限页面
	 * @return
	 */
	@RequestMapping(value = "/unauthorized")
	public ModelAndView unauthorized() {
		log.info("unauthorized");
		ModelAndView ret = new ModelAndView("common/401");
		return ret;
	}
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout() {
		log.info("logout");
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		ModelAndView ret = new ModelAndView("login");
		return ret;
	}
}
