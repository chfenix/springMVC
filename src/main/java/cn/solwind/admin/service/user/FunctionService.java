package cn.solwind.admin.service.user;

import java.util.List;

import cn.solwind.admin.entity.SysFunction;

/**
* @author Zln
* @version 2019-01-04 16:35
* 
* 功能Service
*/

public interface FunctionService {
	
	/**
	 * 获取用户所有的权限功能
	 * @return
	 */
	public List<SysFunction> getUserFunction(Long userId);

	/**
	 * 载入所有功能至缓存
	 * @return
	 */
	public void loadAllFunc();
	
	/**
	 * 获取用户所有的菜单
	 * @return
	 */
	public List<SysFunction> getUserMenu(Long userId);
}
