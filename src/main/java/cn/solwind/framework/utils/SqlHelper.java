package cn.solwind.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
* @author Zln
* @version 2018-12-11 13:56
* 
* SQL处理辅助类
*/

public class SqlHelper {

	/**
	 * 获取全模糊查询条件
	 * %param%
	 * 
	 * @param param
	 * @return
	 */
	public static String getLikeParam(String param) {
		
		if(StringUtils.isNotBlank(param)) {
			return "%" + param + "%";
		}
		else {
			return null;
		}
	}
	
	/**
	 * 获取左模糊查询条件
	 * %param
	 * 
	 * @param param
	 * @return
	 */
	public static String getLLikeParam(String param) {
		if(StringUtils.isNotBlank(param)) {
			return "%" + param;
		}
		else {
			return null;
		}
	}
	
	/**
	 * 获取右模糊查询条件
	 * param%
	 * 
	 * @param param
	 * @return
	 */
	public static String getRLikeParam(String param) {
		if(StringUtils.isNotBlank(param)) {
			return param + "%";
		}
		else {
			return null;
		}
	}
	
}
