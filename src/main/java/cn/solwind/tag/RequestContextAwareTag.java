package cn.solwind.tag;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
* @author XJ
* @version  2018年12月6日 
* 使用内存中常量的select下拉框自定义标签
*/
public abstract class RequestContextAwareTag extends TagSupport implements TryCatchFinally {
	protected abstract int doStartTagInternal() throws Exception;
}


