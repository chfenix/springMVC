package cn.solwind.framework.common;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * @author Zln
 * @version 2018-12-10 13:44
 * 
 * 结合PageHelper使用的分页数据Bean
 */

public class PageBean<T> {

	private List<T> data;
	
	private PageInfo<T> pageInfo;

	public PageBean (PageInfo<T> pageInfo) {
		this.data = pageInfo.getList();
		
		this.pageInfo = pageInfo;
	}

	public List<T> getData() {
		return data;
	}
	
	public int getTotalCount() {
		return Integer.parseInt(String.valueOf(pageInfo.getTotal()));
	}
	
	public int getCurPage() {
		return pageInfo.getPageNum();
	}
	
	public int getTotalPage() {
		return pageInfo.getPages();
	}
	
	public int getPageSize() {
		return pageInfo.getPageSize();
	}

}
