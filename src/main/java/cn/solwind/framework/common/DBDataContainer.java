package cn.solwind.framework.common;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.solwind.admin.entity.Sysbook;
import cn.solwind.framework.redis.Redis;

/**
* @author Zln
* @version 2018-12-04 13:23
* 
* 载入数据库数据
*/

public class DBDataContainer{
	
	private static Logger log = LogManager.getLogger();
	
	/**
	 * 用于获取Sysbook中TypeCode及ListCode对应的值
	 * 
	 * @param typeCode
	 * @param listCode
	 * @return
	 */

	public static String getListName(String typeCode,String listCode) {
		Redis redis = Redis.getInstance();
		String listName = (String)redis.hGet(CacheKey.SYS_BOOK_LIST_VALUE, typeCode+ "|" +listCode);
		return listName;
	}
	
	/**
	 * 用于获取Sysbook中TypeCode对应所有的Sysbook对象
	 * @param typeCode
	 * @return
	 */
	public static List<Sysbook> getSysbook(String typeCode) {
		Redis redis = Redis.getInstance();
		List listSysbook = (List)redis.hGet(CacheKey.SYS_BOOK_KEY, typeCode);
		if(listSysbook != null && listSysbook.size() > 0) {
			return (List<Sysbook>)listSysbook;
		}
		else {
			return null;
		}
	}
}
