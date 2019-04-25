package cn.solwind.admin.controller.role;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.druid.util.StringUtils;

import cn.solwind.admin.common.Constants;
import cn.solwind.admin.common.LoginSummary;
import cn.solwind.admin.entity.SysRole;
import cn.solwind.admin.service.role.SysRoleService;
import cn.solwind.framework.common.BusinessException;
import cn.solwind.framework.common.ErrorCode;
import cn.solwind.framework.common.PageBean;

/** 
* @author wangxian 
* @version 创建时间：2019年1月14日 上午10:31:52 
* 
*/
@Controller
@RequestMapping(value="/manager/role")
public class RoleController {
	
	Logger log = LogManager.getLogger();
	
	@Autowired
	SysRoleService sysRoleService;
	
	//菜单栏点击跳转
	@RequestMapping(value="/toRole")
	protected String toUserInfo() {
		return "role/roleManager";
	}
	
	//返回按钮
	@RequestMapping(value="/goBackRole")
	protected ModelAndView goBack(String name,String code,Integer pageNum) {
		ModelAndView modelAndView = new ModelAndView();
		if(pageNum==null) {
			//pageNum不存在的情况
			modelAndView.setViewName("role/roleManager");
		}else {
			modelAndView = queryRoleInfo(name, code, pageNum);
		}
		return modelAndView;
	}
	
	
	//根据名称查询用户信息
	@RequestMapping(value="/queryRoleInfo")
	protected ModelAndView queryRoleInfo(String name,String code,Integer pageNum) {
		if (pageNum == null) {
			pageNum = 1;
		}
		
		log.info("用户姓名，用户登录名："+name+"+"+code+"pageNum:"+pageNum);
		
		ModelAndView modelAndView = new ModelAndView("role/roleManager");
		modelAndView.addObject("name", name);
		modelAndView.addObject("code", code);
		
		//获取每页条数
		int pageSize = Constants.pageSize;
		PageBean<SysRole> pbRole =sysRoleService.getSysRole(name, code, pageNum, pageSize) ;
		
		modelAndView.addObject("pageBean",pbRole);
		
		return modelAndView;
	}
	
	//修改添加操作
	@RequestMapping(value="/roleOperation")
	protected ModelAndView operation(String name,String code,String operation,String id,String pageNum) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//查询输入保持
		modelAndView.addObject("name_search", name);
		modelAndView.addObject("code_search", code);
		modelAndView.addObject("pageNum", pageNum);
		
		//用于生成功能树，包含了所有的有效功能
		modelAndView.addObject("roleList",sysRoleService.queryAllValidFunction());
		
		//跳到添加页面
		if(operation.equalsIgnoreCase("add")) {
			modelAndView.setViewName("role/roleAdd");
		}
		
		//跳到修改页面
		if(operation.equalsIgnoreCase("modify")) {
			//获取角色信息
			modelAndView.addObject("sr", sysRoleService.getRoleInfo(id));
			//获取角色功能关联关系信息
			modelAndView.addObject("funcs",sysRoleService.getRoleFuncRelation(id));
			//页面路径
			modelAndView.setViewName("role/roleModify");
		}
		
		return modelAndView;
		
	}
	
	//添加角色
	@RequestMapping(value="/addRoleInfo")
	protected ModelAndView addRole(SysRole sysRole,String funcs,String id,
			String name_search,String code_search,Integer pageNum) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String[] str = null;
		
		//funcs拆分成字符串数组,用于插入一对多关系表
		if(!StringUtils.isEmpty(funcs)) {
			str = funcs.split(",");
		}else {
			str = null;
		}
 		
		//查询页面信息保持
		modelAndView.addObject("name_search", name_search);
		modelAndView.addObject("code_search", code_search);
		modelAndView.addObject("pageNum", pageNum);
		//对应功能表
		modelAndView.addObject("funcs", funcs);
		
		//去空格处理
		sysRole.setCode(sysRole.getCode().trim());
		sysRole.setName(sysRole.getName().trim());
		
		log.info("功能字符串funcs："+funcs);
		
		if(sysRoleService.duplicate(sysRole,id)) {
			
			log.info("不重复");
			//获取创建人和创建时间
			Subject subject = SecurityUtils.getSubject();
			LoginSummary loginSummary = (LoginSummary)subject.getPrincipal();
			sysRole.setCreateUser(loginSummary.getUserName());
			sysRole.setCreateTime(new Date());
			
			boolean bn = sysRoleService.addRoleInfo(sysRole, str);
			//返回查询页面
			modelAndView = queryRoleInfo(name_search, code_search, pageNum);
			
			if(bn) {
				//插入成功
				modelAndView.addObject(Constants.RESP_CODE,Constants.SUCCESSCODE);
				modelAndView.addObject(Constants.RESP_MSG,Constants.SUCCESSMSG);
			}else {
				//返回插入失败信息
				modelAndView.addObject(Constants.RESP_CODE,ErrorCode.ROLE_ADD_FAILED);
				modelAndView.addObject(Constants.RESP_MSG,new BusinessException(ErrorCode.ROLE_ADD_FAILED).getMessage());
			}
			//返回查询页面
			
		}else {
			//重复情况
			modelAndView.addObject(Constants.RESP_CODE,ErrorCode.ROLE_DUPLICATE);
			modelAndView.addObject(Constants.RESP_MSG,new BusinessException(ErrorCode.ROLE_DUPLICATE).getMessage());
			modelAndView.addObject("roleList",sysRoleService.queryAllValidFunction());
			modelAndView.addObject("sr",sysRole);
			modelAndView.setViewName("role/roleAdd");
		}
		return modelAndView;
	}
	
	//修改角色
	@RequestMapping(value="/modifyRole")
	protected ModelAndView modifyRole(SysRole sysRole,String funcs,String id,
			String name_search,String code_search,Integer pageNum) {
		ModelAndView modelAndView = new ModelAndView();
		
		String[] str = null;
		
		//funcs拆分成字符串数组,用于插入一对多关系表
		if(!StringUtils.isEmpty(funcs)) {
			str = funcs.split(",");
		}else {
			str = null;
		}
 		
		//查询页面信息保持
		modelAndView.addObject("name_search", name_search);
		modelAndView.addObject("code_search", code_search);
		modelAndView.addObject("pageNum", pageNum);
		//对应功能表
		modelAndView.addObject("funcs", funcs);
		
		//去空格处理
		sysRole.setCode(sysRole.getCode().trim());
		sysRole.setName(sysRole.getName().trim());
		
		log.info("功能字符串funcs："+funcs);
		
		if(sysRoleService.duplicate(sysRole,id)) {
			
			log.info("不重复");
			//获取修改人和修改时间
			Subject subject = SecurityUtils.getSubject();
			LoginSummary loginSummary = (LoginSummary)subject.getPrincipal();
			sysRole.setModifyUser(loginSummary.getUserName());
			sysRole.setModifyTime(new Date());
			
			//修改数据
			boolean bn = sysRoleService.modifyRoleInfo(sysRole, str);
			//返回查询页面
			modelAndView = queryRoleInfo(name_search, code_search, pageNum);
			
			if(bn) {
				//修改成功
				modelAndView.addObject(Constants.RESP_CODE,Constants.SUCCESSCODE);
				modelAndView.addObject(Constants.RESP_MSG,Constants.SUCCESSMSG);
			}else {
				//返回修改失败信息
				modelAndView.addObject(Constants.RESP_CODE,ErrorCode.ROLE_MODIFY_FAILED);
				modelAndView.addObject(Constants.RESP_MSG,new BusinessException(ErrorCode.ROLE_MODIFY_FAILED).getMessage());
			}
			//返回查询页面
			
		}else {
			//重复情况
			modelAndView.addObject(Constants.RESP_CODE,ErrorCode.ROLE_DUPLICATE);
			modelAndView.addObject(Constants.RESP_MSG,new BusinessException(ErrorCode.ROLE_DUPLICATE).getMessage());
			modelAndView.addObject("roleList",sysRoleService.queryAllValidFunction());
			modelAndView.addObject("sr",sysRole);
			modelAndView.setViewName("role/roleModify");
		}
		return modelAndView;
	}
	
	//删除角色
	@RequestMapping(value="/deleteRoleInfo")
	protected ModelAndView deleteRole(SysRole sysRole,String name,String code,Integer pageNum) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//获取操作人和操作时间
		Subject subject = SecurityUtils.getSubject();
		LoginSummary loginSummary = (LoginSummary)subject.getPrincipal();
		sysRole.setModifyUser(loginSummary.getUserName());
		sysRole.setModifyTime(new Date());
		
		//删除
		boolean bn = sysRoleService.deleteRoleInfo(sysRole);
		
		//返回当前页并重新加载
		modelAndView = queryRoleInfo(name, code, pageNum);
		
		if(bn) {
			//返回操作成功信息
			modelAndView.addObject(Constants.RESP_CODE,Constants.SUCCESSCODE);
			modelAndView.addObject(Constants.RESP_MSG,Constants.SUCCESSMSG);
		}else {
			modelAndView.addObject(Constants.RESP_CODE,ErrorCode.ROLE_DELETE_FAILED);
			modelAndView.addObject(Constants.RESP_MSG,new BusinessException(ErrorCode.ROLE_DELETE_FAILED).getMessage());
		}
		return modelAndView;
	}
		
}

