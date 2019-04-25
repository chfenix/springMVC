package cn.solwind.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.solwind.framework.common.PageBean;

/**
 * @author Zln
 * @version 2018-12-10 13:28
 * 
 *  自定义分页标签
 */

public class PaginateTag extends TagSupport {

	private static final long serialVersionUID = 8690111313418369442L;

	private final Logger log = LogManager.getLogger();

	private PageBean<?> pageBean;

	private Integer curPage;
	private Integer totalPage;
	private Integer pageSize;
	private Integer totalCount = 0;
	private String formId;

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public void setPageBean(PageBean<?> pageBean) {
		this.pageBean = pageBean;
	}

	public int doStartTag() throws JspException {
		if(this.pageBean!=null) {
		init();
		JspWriter out = pageContext.getOut();

		int pageNumber = 0;
		if (totalPage % pageSize == 0) {
			pageNumber = totalPage / pageSize;
		} else {
			pageNumber = (totalPage / pageSize) + 1;
		}
		if (curPage < 1) {
			curPage = 1;
		}
		try {
			if (pageNumber > 0) {
				out.print("<script type='text/javascript'>"
						+"function paginateGo(pageNum) {"
						+"document.getElementById(\"pageNum\").value=pageNum;"
						+"document.getElementById(\"" + formId + "\").submit();}</script>");
				
				
				out.print("<div class=\"row\"><div class=\"col-sm-5\">");
				out.print("<div class=\"dataTables_info\" id=\"paginateTables\" role=\"status\" aria-live=\"polite\">");
				out.print("共" + totalPage + "页" + this.totalCount + "条");
				out.print("</div></div>");
				int start = 1;
				int end = totalPage;
				for (int i = 4; i >= 1; i--) {
					if ((curPage - i) >= 1) {
						start = curPage - i;
						break;
					}
				}
				for (int i = 4; i >= 1; i--) {
					if ((curPage + i) <= totalPage) {
						end = curPage + i;
						break;
					}
				}
				// 如果小于9则右侧补齐
				if (end - start + 1 <= 9) {
					Integer padLen = 9 - (end - start + 1);
					for (int i = padLen; i >= 1; i--) {
						if ((end + i) <= totalPage) {
							end = end + i;
							break;
						}
					}
				}

				// 如果还小于9左侧补齐
				if (end - start + 1 <= 9) {
					Integer padLen = 9 - (end - start + 1);
					for (int i = padLen; i >= 1; i--) {
						if ((start - i) >= 1) {
							start = start - i;
							break;
						}
					}
				}
				
				out.print("<div class=\"col-sm-7\"><div class=\"dataTables_paginate paging_simple_numbers\" id=\"paginateNums\">");
				out.print("<ul class=\"pagination\">");
				
				// 首页/上一页按钮
				if (curPage > 1) {
					out.print("<li class=\"paginate_button previous \" id=\"paginate_previous\">");
					if (start > 1) {
						out.print("<a href='javascript:paginateGo(1)'>首页</a>");
					}
					else {
						out.print("<a href='javascript:paginateGo(" + (curPage - 1) + ")'>上一页</a>");
					}
					out.print("</li>");
				}

				// 中间页码
				for (int i = start; i <= end; i++) {
					if (i == curPage) {
						out.print("<li class=\"paginate_button active\"><a  href='#'>" + i + "</a></li>");
					} else {
						out.print("<li class=\"paginate_button\"><a href='javascript:paginateGo(" + i + ")'>" + i + "</a></li>");
					}
				}
				
				// 下一页、尾页
				
				if (curPage < totalPage) {
					out.print("<li class=\"paginate_button next\" id=\"pageinate_next\">");
					
					if (end < totalPage) {
						out.print("<a href='javascript:paginateGo(" + totalPage + ")'>尾页</a>");
					}
					else {
						out.print("<a href='javascript:paginateGo(" + (curPage + 1) + ")'>下一页</a>");
					}
					out.print("</li>");
				}
				out.print("</ul></div></div>");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		return super.doStartTag();

	}

	public static Integer getStartIndex(Integer pageNum, Integer pageSize) {
		Integer res = 0;
		if (pageNum > 0) {
			res = (pageNum - 1) * pageSize;
		}
		return res;
	}

	private void init() {
		if(pageBean!=null) {
		curPage = pageBean.getCurPage();
		totalPage = pageBean.getTotalPage();
		pageSize = pageBean.getPageSize();
		totalCount = pageBean.getTotalCount();
		}
	}

}
