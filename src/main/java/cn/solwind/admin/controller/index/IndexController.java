package cn.solwind.admin.controller.index;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cn.solwind.admin.common.Constants;
import cn.solwind.admin.common.LoginSummary;
import cn.solwind.admin.entity.SysUser;
import cn.solwind.admin.service.user.SysUserService;
import cn.solwind.framework.common.BusinessException;
import cn.solwind.framework.common.ErrorCode;
import cn.solwind.framework.utils.Md5Utils;

/**
 * @author XJ
 * @version 2018-11-27 首页 Controller
 */
@Controller
public class IndexController {
	Logger log = LogManager.getLogger();

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

	@Autowired
	SysUserService sysUserService;

	/**
	 * 进入首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/")
	public ModelAndView index() {
		log.info("IndexController enter index......");
		ModelAndView ret = new ModelAndView("/index/index");
		return ret;
	}


	/**
	 * 修改密码
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/updatePwd", method = {RequestMethod.POST},
			produces="text/html;charset=UTF-8;")
	@ResponseBody 
	public String updatePwd(String oldPassword,String newPassword,
			HttpServletRequest request) throws UnsupportedEncodingException {
		log.info("IndexController updatePwd...");	
		
		Subject subject = SecurityUtils.getSubject();
		LoginSummary summary = (LoginSummary) subject.getPrincipal();
		String username=summary.getUserName();
		String md5Key=Constants.get(Constants.MD5_KEY);
		String pwdStr=username.concat(oldPassword).concat(md5Key);
		//原密码加密
		String oldPwdMd5=Md5Utils.stringMD5(pwdStr);
		String userId=summary.getId().toString();
		//log.info(userId+"/"+username+"/"+oldPwdMd5);
		Map<String,String> resultMap=new HashMap<>();
		String jsonStr=null;
		//修改密码时查询输入原密码是否一致
		SysUser sysUser=sysUserService.querySysUserByParams(userId,username);
		if(sysUser!=null) {
			if(!sysUser.getPassword().toLowerCase().equals(oldPwdMd5)) {
				resultMap.put(Constants.RESP_CODE, ErrorCode.WRONG_PASSWORD);
				resultMap.put(Constants.RESP_MSG, new BusinessException(ErrorCode.WRONG_PASSWORD).getMessage());
				jsonStr=new Gson().toJson(resultMap);
				log.info(jsonStr);
				return jsonStr;
			}
			pwdStr=username.concat(newPassword).concat(md5Key);
			//新密码加密
			String newPwdMd5=Md5Utils.stringMD5(pwdStr);
			int updateStatus=sysUserService.updateSysUserPwd(userId,newPwdMd5,username,
					DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			
			//修改成功
			if(updateStatus>=1) {
				resultMap.put(Constants.RESP_CODE, Constants.SUCCESSCODE);
				resultMap.put(Constants.RESP_MSG, Constants.SUCCESSMSG);
			}else {
				resultMap.put(Constants.RESP_CODE, ErrorCode.UPDATE_PWD_FAIL);
				resultMap.put(Constants.RESP_MSG, new BusinessException(ErrorCode.UPDATE_PWD_FAIL).getMessage());
			}		
		}else {
			resultMap.put(Constants.RESP_CODE, ErrorCode.USER_IS_NOT_EXIST);
			resultMap.put(Constants.RESP_MSG, new BusinessException(ErrorCode.USER_IS_NOT_EXIST).getMessage());
		}
		
		jsonStr=new Gson().toJson(resultMap);
		log.info("jsonStr:"+jsonStr);
		return jsonStr;
	}

}
