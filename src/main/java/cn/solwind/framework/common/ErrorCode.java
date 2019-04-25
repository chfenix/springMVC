package cn.solwind.framework.common;

/**
 * 错误编码
 * 
 * @author zln
 *
 */
public class ErrorCode {
	
	/*
	 * 通用系统类
	 */
	public static final String COMMON_SUCCESS = "0000";		// 成功
	public static final String COMMON_PARAM_ERROR = "9997";	// 参数错误
	public static final String COMMON_SYSTEM_ERROR = "9999";	// 系统错误
	public static final String COMMON_FIAL = "9990";		// 失败
	
	/*
	 * 通用
	 */
	public static final String DELETE_FAIL="0001";//删除失败
	public static final String CREATE_FAIL="0002";//新增失败
	public static final String MODIFY_FAIL="0003";//修改失败
	
	/**
	 * 响应编号码(修改密码失败)
	 */
	public static final String UPDATE_PWD_FAIL="9901";
	/**
	 * 响应编号码(密码错误)
	 */
	public static final String WRONG_PASSWORD="9902";
	/**
	 * 响应编号码(用户不存在)
	 */
	public static final String USER_IS_NOT_EXIST="9903";
	
	/*
	 * 用户管理
	 */
	public static final String USER_ADD_FAILED="0701";//用户信息添加失败
	public static final String USER_MODIFY_FAILED="0702";//用户信息修改失败
	public static final String USER_USERNAME_DUPLICATE="0703";//用户名称重复
	public static final String USER_DELETE_FAILED="0704";//用户名称重复
	
	//角色管理
	public static final String ROLE_ADD_FAILED="0801";//角色信息添加失败
	public static final String ROLE_MODIFY_FAILED="0802";//角色信息修改失败
	public static final String ROLE_DUPLICATE="0803";//角色信息重复
	public static final String ROLE_DELETE_FAILED="0804";//角色信息删除失败
	
	
	public static final String USER_SYSTEM_ERROR = "9801";		// 用户系统异常
}
