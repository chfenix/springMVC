package cn.solwind.admin.service.role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageInfo;

import cn.solwind.admin.dao.SysRoleDao;
import cn.solwind.admin.entity.SysFunction;
import cn.solwind.admin.entity.SysRole;
import cn.solwind.framework.common.PageBean;
import cn.solwind.framework.utils.SqlHelper;

/** 
* @author wangxian 
* @version 创建时间：2019年1月14日 下午2:35:03 
* 
*/
@Service
public class SysRoleServiceImpl implements SysRoleService{
	
	@Autowired
	SysRoleDao sysRoleDao;
	
	//获取所有有效功能，并进行处理，把数据封装成菜单模式，返回菜单list
	@Override
	public List<SysFunction> queryAllValidFunction() {
	    
		//初始数据，为多条并列的从数据库取出来的数据行
	    List<SysFunction> rootMenu = sysRoleDao.queryAllValidFunction();
	    
	    //用于返回的list
	    List<SysFunction> menuList = new ArrayList<>();
	    
	    //首先是所有的一级菜单，通过parentId来判断，以及菜单没有parentId
	    for (int i = 0; i < rootMenu.size(); i++) {
	        // 一级菜单没有parentId
	        if (StringUtils.isEmpty(rootMenu.get(i).getParentId())) {
	            menuList.add(rootMenu.get(i));
	        }
	    }
	    
	    // 为一级菜单设置子菜单，getChild为递归方法，可以遍历无限层级
	    for (SysFunction sysFunction : menuList) {
	    	sysFunction.setChildList(getChild(sysFunction.getId(), rootMenu));
	    }
	    
	    return menuList;
	}
	
	//获取菜单子菜单，递归方法
	private List<SysFunction> getChild(long id,List<SysFunction> rootMenu){
		
		// 子菜单
	    List<SysFunction> childList = new ArrayList<>();
	    for (SysFunction sysFunction : rootMenu) {
	    	
	    	//非空判断，跳过parentId不存在的数据行
	        if (!StringUtils.isEmpty(sysFunction.getParentId())) {
	        	
	        	// 遍历所有节点，将父菜单id与传过来的id比较，数据parentId等于传过来id的，则为相应菜单子菜单
	            if (sysFunction.getParentId().equals(id)) {
	                childList.add(sysFunction);
	            }
	        }
	    }
	    
	    //只有子菜单存在也即是存在childlist的时候才继续递归
	    if (childList.size() != 0) {
		    //递归子菜单
		    for (SysFunction sysFunction : childList) {
		    	sysFunction.setChildList(getChild(sysFunction.getId(), rootMenu));
		    } 
	    }
	    return childList;
	}
	
	//角色信息查询并分页
	@Override
	public PageBean<SysRole> getSysRole(String name, String code,int pageNum, int pageSize) {
		//对字段进行处理如果值为""则赋值为null
		name=SqlHelper.getLikeParam(name);
		code=SqlHelper.getLikeParam(code);
		
		//取出信息存入返回
		List<SysRole> roleList = sysRoleDao.queryRoleInfo(name, code, pageNum, pageSize);
		PageInfo<SysRole> pageInfo = new PageInfo<>(roleList);
		PageBean<SysRole> pageBean = new PageBean<>(pageInfo);
		return pageBean;
	}
	
	//添加角色信息
	@Override
	public boolean addRoleInfo(SysRole sysRole,String[] funcId) {
		
		//插入数据到role表
		int result = sysRoleDao.addRoleInfo(sysRole);
		
		//返回生成的自增长id
		String roleId = sysRole.getId().toString();
		
		//添加数据进r_f关系表
		addRoleFunc(roleId,funcId);
		
		if(result>0) {
			//插入成功
			return true;
		}
		return false;
	}
	
	//添加角色功能
	@Override
	public boolean addRoleFunc(String roleId, String[] funcId) {
		
		//插入数据前进行关系删除操作
		sysRoleDao.deleteRFByRoleId(roleId);
		
		//如果关系数据存在，则进行添加操作
		if(funcId!=null) {
			int result = sysRoleDao.addRoleFunc(roleId, funcId);
			//返回值大于零说明插入成功
			if(result<0) {
				return false;
			}
		}
		//返回值
		return true;
	}
	
	//角色信息重复性校验
	@Override
	public boolean duplicate(SysRole sysRole,String id) {
		
		int result = sysRoleDao.roleDuplicate(sysRole.getName(), sysRole.getCode(), id);
		
		//大于0则说明有重复数据行
		if(result>0) {
			return false;
		}
		//反之不重复
		return true;
	}
	
	//根据roleId查询角色信息
	@Override
	public SysRole getRoleInfo(String roleId) {
		
		return sysRoleDao.getRoleInfoById(roleId);
	}
	
	//根据roleId查询角色功能关系数据,并处理成字符串进行返回
	@Override
	public String getRoleFuncRelation(String roleId) {
		
		//获取关系表数据
		List<String> rfInfoList = sysRoleDao.getRFInfoById(roleId);
		
		String rfInfo = "";
		
		//如果关系数据存在
		if(rfInfoList!=null) {
			//拼接字符串
			for(String str: rfInfoList) {
				rfInfo+=str+",";
			}
			//截取需要的字符串，去除句末","
			if(!rfInfo.isEmpty()) {
				rfInfo=rfInfo.substring(0, rfInfo.length()-1);
			}
		}
		return rfInfo;
	}
	
	//修改角色信息，同步修改角色功能关系数据
	@Override
	public boolean modifyRoleInfo(SysRole sysRole, String[] funcs) {
		
		//修改角色信息
		int result = sysRoleDao.updateRoleInfo(sysRole);
		//修改前先删除角色功能关系
		sysRoleDao.deleteRFByRoleId(sysRole.getId().toString());
		//如果状态没有改为0，则添加关系数据，add方法中先删除再添加
		if(sysRole.getStatus()!=0&&funcs!=null) {
			sysRoleDao.addRoleFunc(sysRole.getId().toString(), funcs);
		}
		
		//返回值大于零则修改成功
		if(result>0) {
			return true;
		}
		return false;
	}
	
	//删除用户信息，实质为修改用户状态为失效,同时，删除角色功能关系表相关数据
	@Override
	public boolean deleteRoleInfo(SysRole sysRole) {
		
		//删除角色功能关系数据
		sysRoleDao.deleteRFByRoleId(sysRole.getId().toString());
		//修改角色状态为失效
		int result = sysRoleDao.deleteRoleInfo(sysRole);
		//存在大于0 的返回值，说明修改成功
		if(result>0) {
			return true;
		}
		return false;
	}
}

