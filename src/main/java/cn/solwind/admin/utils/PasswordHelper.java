package cn.solwind.admin.utils;

import cn.solwind.admin.common.Constants;
import cn.solwind.framework.utils.Md5Utils;

/**
* @author Zln
* @version 2019-01-04 17:09
* 
* 密码工具类
*/

public class PasswordHelper {

	/**
	 * 生成用户密码
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String generateUserPwd(String userName,String password) {
		String md5Key = Constants.get(Constants.MD5_KEY);
		
		return Md5Utils.stringMD5(userName + password + md5Key).toUpperCase();
	}
}
