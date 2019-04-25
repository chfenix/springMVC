package cn.solwind.admin.service.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.solwind.admin.dao.SysFunctionDao;
import cn.solwind.admin.entity.SysFunction;
import cn.solwind.framework.common.CacheKey;
import cn.solwind.framework.redis.Redis;

/**
* @author Zln
* @version 2019-01-04 16:35
* 
* 功能Service
*/

@Service
public class FunctionServiceImpl implements FunctionService {
	
	private static Logger log = LogManager.getLogger();
	
	@Autowired
	SysFunctionDao funcDao;
	
	/**
	 * 获取用户所有的权限功能
	 * @return
	 */
	public List<SysFunction> getUserFunction(Long userId) {
		return funcDao.getUserFunc(userId);
	}
	
	/**
	 * 载入所有功能至缓存
	 * @return
	 */
	public void loadAllFunc() {
		log.info("FunctionService.loadAllFunc invoke!");
		List<SysFunction> listFunc = funcDao.getAllValidFunc();
		
		Redis redis = Redis.getInstance();
		
		// 将function按照key为url进行保存，用于权限判断时获取SysFunction对象
		if(listFunc != null && listFunc.size() > 0) {
			for (SysFunction objFunc : listFunc) {
				redis.hSet(CacheKey.SYS_FUNCTION, objFunc.getAction(), objFunc);
			}
		}
		
		log.info("FunctionService.loadAllFunc(" + listFunc.size() + ") invoke end!");
	}
	
	/**
	 * 获取用户所有的菜单
	 * @return
	 */
	public List<SysFunction> getUserMenu(Long userId) {
		return funcDao.getUserMenu(userId);
	}

}
