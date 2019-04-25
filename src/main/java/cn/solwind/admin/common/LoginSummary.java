package cn.solwind.admin.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zln
 * @version 2018-12-11 16:37
 * 
 * 登录用户信息
 */

public class LoginSummary implements Serializable {

	private static final long serialVersionUID = -1322536150536718521L;

	private Long id; // id

	private String name; // 姓名

	private String userName; // 账户名称

	private String roleName; // 类型

	private Date loginTime; // 登录时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "LoginSummary [id=" + id + ", name=" + name + ", userName="
				+ userName + ", roleName=" + roleName + ", loginTime="
				+ loginTime + "]";
	}
	
}
