package cn.solwind.framework.common;

/**
* @author Zln
* @version 2018-12-03 13:32
* 
* 缓存密钥
*/

public class CacheKey {

	
	/*
	 * 系统配置表 保存结构为MAP，对应配置表中数据
	 */
	public static final String SYS_CONFIG_KEY = "SYS_CONFIG_KEY";

	/*
	 * 系统常量表 保存结构为MAP，每个KEY对应的VALUE为LIST，LIST中为Sysbook对象
	 */
	public static final String SYS_BOOK_KEY = "SYS_BOOK_KEY";

	/*
	 * 系统常量表平铺数据 保存结构为MAP,KEY为"TYPE_CODE"+"|"+"LIST_CODE",VALUE为LIST_NAME
	 */
	public static final String SYS_BOOK_LIST_VALUE = "SYS_BOOK_LIST_VALUE";
	
	/*
	 * 用户权限保存key List<SysFunction>
	 */
	public static final String USER_PERMISSION_PRFIX = "USER_PERMISSION_";
	
	/*
	 * 用户菜单保存key List<SysFunction>
	 */
	public static final String USER_MENU_PRFIX = "USER_MENU_";
	
	/*
	 * 系统功能，保存结构为MAP，KEY为URL，VALUE为SysFunction对象
	 */
	public static final String SYS_FUNCTION = "SYS_FUNCTION";
	
}
