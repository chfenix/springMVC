package cn.solwind.admin.common;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
* @author Zln
* @version 2018-11-21 16:15
* 
* 常量类
*/

public class Constants extends Properties{
	
	private static final long serialVersionUID = 5131763841418318566L;

	protected static final Logger log = LogManager.getLogger();
	
	public static Constants P = null;
	
	/**
	 * 响应编号
	 */
	public static final String RESP_CODE="respcode";
	/**
	 * 响应信息
	 */
	public static final String RESP_MSG="respmsg";
	
	/**
	 * 响应编码(成功)
	 */
	public static final String SUCCESSCODE="0000";
	/**
	 * 响应信息(成功)
	 */
	public static final String SUCCESSMSG="成功";
	
	/*
	 * properties文件
	 */
	public static final String PFILE_CONFIG = "config.properties";
	/**
	 * 每页数据条数
	 */
	public static int pageSize = 0;
	/**
	 * MD5加密key
	 */
	public static final String MD5_KEY="md5_key";
	
	
	/**
	 * 通用状态
	 */
	public static final Integer COMMON_STATUS_VALID = 1;	// 有效
	public static final Integer COMMON_STATUS_INVALID = 0;	// 无效
	
	/*
	 * 功能类型
	 */
	public static final Integer FUNC_TYPE_MENU = 1;		// 菜单
	public static final Integer FUNC_TYPE_FUNC = 2;		// 功能
	
	/**
	 * 载入所有配置文件
	 */
	public static synchronized void initProperties() {
		try {
			if(P != null) {
				return;
			}
			
			log.info("loading properties..............");
			P = new Constants();
			String[] arrProFiles = {PFILE_CONFIG};
			
			for (String proFile : arrProFiles) {
				log.info("load properties file:" + proFile);
				P.load(Constants.class.getResourceAsStream("/" + proFile));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		String value = "";
		try {
			if (key == null || P == null) {
				return "";
			}
			if (P.containsKey(key))
				value = new String(P.getProperty(key).getBytes("UTF-8"),
						"UTF-8");
			else
				value = "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
}