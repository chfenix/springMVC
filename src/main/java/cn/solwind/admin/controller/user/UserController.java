package cn.solwind.admin.controller.user;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

import cn.solwind.admin.common.Constants;
import cn.solwind.admin.common.LoginSummary;
import cn.solwind.admin.entity.SysUser;
import cn.solwind.admin.service.user.SysUserService;
import cn.solwind.framework.common.BusinessException;
import cn.solwind.framework.common.ErrorCode;
import cn.solwind.framework.common.PageBean;
import cn.solwind.framework.utils.Md5Utils;

/**
* @author wangxian 
* @version 创建时间：2019年1月9日 上午10:05:19
* 
*/
@Controller
@RequestMapping(value="/manager/user")
public class UserController {
	
	Logger log = LogManager.getLogger();
	
	@Autowired
	SysUserService sysUserService;
	
	//根据名称查询用户信息
	@RequestMapping(value="/queryUserInfo")
	protected ModelAndView queryUserInfo(String name,String userName,Integer pageNum) {
		if (pageNum == null) {
			pageNum = 1;
		}
		
		log.info("用户姓名，用户登录名："+name+"+"+userName);
		
		ModelAndView modelAndView = new ModelAndView("user/userManager");
		
		//等待修改
		int pageSize = Constants.pageSize;
		PageBean<SysUser> pbUser = sysUserService.queryUserInfo(name, userName, pageNum, pageSize);
		
		modelAndView.addObject("pageBean",pbUser);
		modelAndView.addObject("name", name);
		modelAndView.addObject("userName", userName);
		
		return modelAndView;
	}
	
	//菜单栏点击跳转
	@RequestMapping(value="/toUserInfo")
	protected String toUserInfo() {
		return "user/userManager";
	}
	
	//返回按钮
	@RequestMapping(value="/goBackUser")
	protected ModelAndView goBack(String name,String userName,Integer pageNum) {
		ModelAndView modelAndView = new ModelAndView();
		if(pageNum==null) {
			//pageNum不存在的情况
			modelAndView.setViewName("user/userManager");
		}else {
			modelAndView = queryUserInfo(name, userName, pageNum);
		}
		return modelAndView;
	}
	
	//修改添加操作
	@RequestMapping(value="/operation")
	protected ModelAndView operation(String name,String userName,String operation,String id,String pageNum) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//查询输入保持
		modelAndView.addObject("name_search", name);
		modelAndView.addObject("username_search", userName);
		modelAndView.addObject("pageNum", pageNum);
		
		//获取所有角色，用于为用户添加角色
		modelAndView.addObject("srList", sysUserService.getAllRoles());
		
		//跳到添加页面
		if(operation.equalsIgnoreCase("add")) {
			modelAndView.setViewName("user/userAdd");
		}
		
		//跳到修改页面
		if(operation.equalsIgnoreCase("modify")) {
			//获取用户信息
			modelAndView.addObject("ui", sysUserService.queryUserInfoById(id));
			//获取用户角色关系信息
			modelAndView.addObject("roleIds", sysUserService.getURInfoById(id));
			//返回路径
			modelAndView.setViewName("user/userModify");
		}
		
		return modelAndView;
		
	}
	
	//添加用户信息
	@RequestMapping(value="/addUserInfo")
	protected ModelAndView addUserInfo(SysUser user,String name_search,String username_search,
			Integer pageNum,String id,String roleIds) {
		
		log.info("用户姓名，用户登录名："+user.getName()+user.getUserName());
		
		ModelAndView modelAndView = new ModelAndView();
		
		//密码加密处理
		user.setPassword(Md5Utils
				.stringMD5(user.getUserName() + user.getPassword() + Constants.get(Constants.MD5_KEY)));
		
		//去除空格
		user.setName(user.getName().trim());
		user.setUserName(user.getUserName().trim());
		user.setEmail(user.getEmail().trim());
		//重复性验证
		boolean duplicate = sysUserService.duplicate(user.getUserName(),id);
		
		if(!duplicate) {
			//不重复
			//获取创建人和创建时间
			Subject subject = SecurityUtils.getSubject();
			LoginSummary loginSummary = (LoginSummary)subject.getPrincipal();
			user.setCreateUser(loginSummary.getUserName());
			user.setCreateTime(new Date());
			
			String[] str = null;
			//roleIds拆分成字符串数组,用于插入一对多关系表
			if(!StringUtils.isEmpty(roleIds)) {
				str = roleIds.split(",");
			}else {
				str = null;
			}
			
			//添加用户信息
			boolean bn = sysUserService.addUserInfo(user,str);
			
			//添加转发页面路径，为管理页并保持查询页面状态
			modelAndView=queryUserInfo(name_search, username_search, pageNum);
			
			if(bn) {
				//添加成功
				modelAndView.addObject(Constants.RESP_CODE, Constants.SUCCESSCODE);
				 modelAndView.addObject(Constants.RESP_MSG, Constants.SUCCESSMSG);
			}else {
				//添加失败，系统异常
				modelAndView.addObject(Constants.RESP_CODE, ErrorCode.USER_ADD_FAILED);
				modelAndView.addObject(Constants.RESP_MSG, new BusinessException(ErrorCode.USER_ADD_FAILED).getMessage());
			}
			
		}else {
			//重复
			modelAndView.addObject(Constants.RESP_CODE, ErrorCode.USER_USERNAME_DUPLICATE);
			modelAndView.addObject(Constants.RESP_MSG, new BusinessException(ErrorCode.USER_USERNAME_DUPLICATE).getMessage());
			
			//获取所有角色，用于为用户添加角色
			modelAndView.addObject("srList", sysUserService.getAllRoles());
			
			//查询框内容传递
			modelAndView.addObject("username_search",username_search);
			modelAndView.addObject("name_search",name_search);
			modelAndView.addObject("pageNum",pageNum);
			//勾选框按钮状态保持
			modelAndView.addObject("roleIds", roleIds);
			
			//输入栏信息保持
			modelAndView.addObject("ui", user);
			//返回路径
			modelAndView.setViewName("user/userAdd");
		}
		return modelAndView;
	}
	
	//通过id修改用户信息
	@RequestMapping(value="/modifyUserInfo")
	protected ModelAndView modifyUserInfo(SysUser user,String name_search,String username_search,
			Integer pageNum,String id,String roleIds) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//密码加密处理,如果没有输入新密码则不修改密码
		if(!user.getPassword().trim().equals("")) {
			user.setPassword(Md5Utils
					.stringMD5(user.getUserName() + user.getPassword() + Constants.get(Constants.MD5_KEY)));
		}else {
			user.setPassword(null);
		}
		
		//去除空格
		user.setName(user.getName().trim());
		user.setUserName(user.getUserName().trim());
		user.setEmail(user.getEmail().trim());
		//重复性验证
		boolean duplicate = sysUserService.duplicate(user.getUserName(),id);
		
		if(!duplicate) {
			//不重复
			//获取修改人和修改时间
			Subject subject = SecurityUtils.getSubject();
			LoginSummary loginSummary = (LoginSummary)subject.getPrincipal();
			user.setCreateUser(loginSummary.getUserName());
			user.setCreateTime(new Date());
			
			String[] str = null;
			//roleIds拆分成字符串数组,用于插入一对多关系表
			if(!StringUtils.isEmpty(roleIds)) {
				str = roleIds.split(",");
			}else {
				str = null;
			}
			
			//进行信息修改
			boolean bn = sysUserService.updateUserInfoById(user,str);
			//添加转发页面路径，为管理页并保持查询页面状态
			modelAndView=goBack(name_search, username_search, pageNum);
			
			if(bn) {
				//修改成功
				modelAndView.addObject(Constants.RESP_CODE, Constants.SUCCESSCODE);
				modelAndView.addObject(Constants.RESP_MSG, Constants.SUCCESSMSG);
			}else {
				//修改失败，系统异常
				modelAndView.addObject(Constants.RESP_CODE, ErrorCode.USER_MODIFY_FAILED);
				modelAndView.addObject(Constants.RESP_MSG, new BusinessException(ErrorCode.USER_MODIFY_FAILED).getMessage());
			}
			
		}else {
			//重复
			modelAndView.addObject(Constants.RESP_CODE, ErrorCode.USER_USERNAME_DUPLICATE);
			modelAndView.addObject(Constants.RESP_MSG, new BusinessException(ErrorCode.USER_USERNAME_DUPLICATE).getMessage());
			
			//查询框内容传递
			modelAndView.addObject("username_search",username_search);
			modelAndView.addObject("name_search",name_search);
			modelAndView.addObject("pageNum",pageNum);
			
			//勾选框保持
			modelAndView.addObject("roleIds", roleIds);
			
			//输入框信息保持
			modelAndView.addObject("ui", user);
			//返回路径
			modelAndView.setViewName("user/userAdd");
		}
		return modelAndView;
	}
	
	//通过id删除用户信息，实则是修改用户状态为无效
	@RequestMapping(value="/deleteUserInfo")
	protected ModelAndView deleteUserInfo(String id,String name,String userName,Integer pageNum) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		Subject subject = SecurityUtils.getSubject();
		LoginSummary loginSummary = (LoginSummary)subject.getPrincipal();
		String modifyUser = loginSummary.getUserName();
		//任务修改时间
		Date modifyTime = new Date();
		
		boolean bn = sysUserService.deleteUserInfoBy(id,modifyUser,modifyTime);
		
		//返回当前页并重新加载
		modelAndView=queryUserInfo(name, userName, pageNum);
		
		if(bn) {
			//成功
			modelAndView.addObject(Constants.RESP_CODE, Constants.SUCCESSCODE);
			modelAndView.addObject(Constants.RESP_MSG, Constants.SUCCESSMSG);
		}else {
			//失败
			modelAndView.addObject(Constants.RESP_CODE, ErrorCode.USER_DELETE_FAILED);
			modelAndView.addObject(Constants.RESP_MSG, new BusinessException(ErrorCode.USER_DELETE_FAILED).getMessage());
		}
		return modelAndView;
	}
	
	//通过登录名查询用户信息，用于用户登录名重复性验证
	@RequestMapping(value="/userNameDuplicate",produces = "application/json; charset=utf-8")
	@ResponseBody
	protected String queryUserNameDuplicate(String userName,String id) {
		
		JSONObject json = new JSONObject();
		if(sysUserService.duplicate(userName,id)) {
			json.put("valid", false);
		}else {
			json.put("valid", true);
		}
		return json.toJSONString();
	}
	//底部
}

