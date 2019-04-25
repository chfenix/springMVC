package cn.solwind.admin.service.user;

import java.util.Date;
import java.util.List;

import cn.solwind.admin.entity.SysRole;
import cn.solwind.admin.entity.SysUser;
import cn.solwind.framework.common.PageBean;

/**
* @author wangxian
* @version 创建时间：2019年1月7日
*
*/
public interface SysUserService {
	public SysUser querySysUserByParams(String userId, String username);

	public int updateSysUserPwd(String userId, String newPwdMd5,String username,String time);
	
	//通过用户名查出密码做对比
	SysUser getUserByUserName(String username);
	
	public void updateLoginDate(long id);
	
	//通过用户登录名和姓名查询用户信息
	PageBean<SysUser> queryUserInfo(String name,String userName,int pageNum, int pageSize);
	
	//通过id查询用户信息
	SysUser queryUserInfoById(String id);
	
	//通过id删除用户，实际是设置状态为失效
	boolean deleteUserInfoBy(String id,String modifyUser,Date modifyTime);
	
	//通过id修改用户信息
	boolean updateUserInfoById(SysUser user,String[] str);
	
	//添加用户信息
	boolean addUserInfo(SysUser user,String[] str);
	
	//用户登录名重复性验证
	public boolean duplicate(String username,String id);
	
	//查询所有角色
	public List<SysRole> getAllRoles();
	
	//添加U_R表信息
	public boolean addURRelation(String userId,String[] roleId);
	
	//根据id获取U_R关系,并拼接成字符串
	public String getURInfoById(String userId);
}

