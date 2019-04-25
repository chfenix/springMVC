package cn.solwind.admin.service.role;
/** 
* @author wangxian 
* @version 创建时间：2019年1月14日 下午2:34:38 
* 
*/

import java.util.List;

import cn.solwind.admin.entity.SysFunction;
import cn.solwind.admin.entity.SysRole;
import cn.solwind.framework.common.PageBean;

public interface SysRoleService {
	
	//角色查询并分页
	public PageBean<SysRole> getSysRole(String name, String code,int pageNum, int pageSize);
	
	//获取所有有效功能，并封装成菜单样式
	List<SysFunction> queryAllValidFunction();
	
	//添加角色信息
	public boolean addRoleInfo(SysRole sysRole,String[] funcId);
	
	//添加角色功能
	public boolean addRoleFunc(String roleId,String[] funcId);
	
	//角色信息重复性校验
	public boolean duplicate(SysRole sysRole,String id);
	
	//查询角色信息
	public SysRole getRoleInfo(String roleId);
	
	//根据roleId查询角色功能关系数据,并处理成字符串进行返回
	public String getRoleFuncRelation(String roleId);
	
	//修改角色信息，同步修改角色功能关系表数据
	public boolean modifyRoleInfo(SysRole sysRole,String[] funcs);
	
	//删除用户信息，实质为修改用户状态为失效
	public boolean deleteRoleInfo(SysRole sysRole);
}

