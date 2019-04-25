package cn.solwind.admin.service.user;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import cn.solwind.admin.dao.SysUserDao;
import cn.solwind.admin.entity.SysRole;
import cn.solwind.admin.entity.SysUser;
import cn.solwind.framework.common.PageBean;
import cn.solwind.framework.utils.SqlHelper;

/**
* @author wangxian
* @version 创建时间：2019年1月7日
*
*/
@Service
public class SysUserServiceImpl implements SysUserService{
	Logger log = LogManager.getLogger();
	
	@Autowired
	SysUserDao sysUserDao;
	
	@Override
	public SysUser querySysUserByParams(String userId, String username) {
		SysUser sysUser =sysUserDao.selectByParams(Long.parseLong(userId),username);
		return sysUser;
	}

	@Override
	public int updateSysUserPwd(String userId, String newPwdMd5,String username,String time) {
		int index=sysUserDao.updatePwd(Long.parseLong(userId), newPwdMd5, username, time);
		return index;
	}
	
	
	@Override
	public SysUser getUserByUserName(String username) {
		return sysUserDao.getUserByUserName(username);
	}

	@Override
	public void updateLoginDate(long id) {
		sysUserDao.updateLoginDate(id);
	}
	
	//通过用户登录名和用户姓名查询用户信息
	@Override
	public PageBean<SysUser> queryUserInfo(String name, String userName,int pageNum, int pageSize) {
		
		//对字段进行处理如果值为""则赋值为null
		name=SqlHelper.getLikeParam(name);
		userName=SqlHelper.getLikeParam(userName);
		
		//取出信息存入返回
		List<SysUser> userList = sysUserDao.queryUserInfo(name, userName,pageNum,pageSize);
		PageInfo<SysUser> pageInfo = new PageInfo<>(userList);
		PageBean<SysUser> pageBean = new PageBean<>(pageInfo);
		return pageBean;
	}

	//通过id查询用户信息
	@Override
	public SysUser queryUserInfoById(String id) {
		
		return sysUserDao.queryUserInfoById(id);
		
	}

	//通过id删除用户，实际是设置状态为失效
	@Override
	public boolean deleteUserInfoBy(String id,String modifyUser,Date modifyTime) {
		
		int result = sysUserDao.delestUserInfoById(id,modifyUser,modifyTime);
		
		//删除用户信息的同时，删除对应的用户角色关系数据
		sysUserDao.deleteURByUserId(id);
		
		if(result>0) {
			return true;
		}
		return false;
	}

	//通过id修改用户信息
	@Override
	public boolean updateUserInfoById(SysUser user,String[] str) {
		
		int result = sysUserDao.updateUserInfoById(user);
		
		//如果添加了角色信息且状态没有修改为失效也就是0，则进行用户角色信息的添加操作
		if(user.getStatus()!=0) {
			addURRelation(user.getId().toString(), str);
		}else {
			//否则删除用户角色关系
			sysUserDao.deleteURByUserId(user.getId().toString());
		}
		
		if(result>0) {
			//修改成功
			return true;
		}
		//修改失败
		return false;
	}
	
	//添加用户信息
	@Override
	public boolean addUserInfo(SysUser user,String[] str) {
		
		//判断是否插入成功
		int result = sysUserDao.addUserInfo(user);
		
		//如果角色信息存在，则进行用户角色信息的添加操作
		if(str!=null) {
			addURRelation(user.getId().toString(), str);
		}
		
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}
	
	//用户名称重复性判断，根据dao返回值判断
	@Override
	public boolean duplicate(String username,String id) {
		
		int result = sysUserDao.userNameDuplicate(username,id);
		
		log.info("result:"+result);
		
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}
	
	//获取所有角色，用于为用户添加角色
	@Override
	public List<SysRole> getAllRoles() {
		
		return sysUserDao.getAllRole();
		
	}
	
	//添加信息进入用户角色关系表
	@Override
	public boolean addURRelation(String userId, String[] roleId) {
		
		//插入关系前先进行删除操作,以防存在相应关系造成冲突
		sysUserDao.deleteURByUserId(userId);
		
		//然后进行关系的插入
		if(roleId!=null) {
			int result = sysUserDao.addUserRoleInfo(userId, roleId);
			//返回值大于零说明操作成功
			if(result<=0) {
				return false;
			}
		}
		//返回值
		return true;
	}
	
	//根据id获取用户角色关系信息
	@Override
	public String getURInfoById(String userId) {
		
		List<String> urInfoList = sysUserDao.getURInfoById(userId);
		
		String roleIds = "";
		
		//如果urInfoList不为空
		if(urInfoList!=null) {
			//forEach拼接字符串
			for(String str: urInfoList) {
				roleIds+=str+",";
			}
			//去除字符串最后的空格
			if(!roleIds.isEmpty()) {
				roleIds=roleIds.substring(0, roleIds.length()-1);
			}
		}
		log.info("roleId的值："+roleIds);
		return roleIds;
	}

}

