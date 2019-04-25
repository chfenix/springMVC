package cn.solwind.admin.dto;


import java.io.Serializable;
import java.util.List;

import cn.solwind.admin.entity.SysFunction;

/**
* @author Zhouluning
* @version 创建时间：2019-01-11
*
* 用户菜单
*/

public class MenuDTO implements Serializable {
	
	private static final long serialVersionUID = 5674703685534106500L;

	private SysFunction func;
	
	private List<SysFunction> children;

	public SysFunction getFunc() {
		return func;
	}

	public void setFunc(SysFunction func) {
		this.func = func;
	}

	public List<SysFunction> getChildren() {
		return children;
	}

	public void setChildren(List<SysFunction> children) {
		this.children = children;
	}
	
}
