package cn.solwind.tag;

import javax.servlet.jsp.JspException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.solwind.framework.common.CacheKey;
import cn.solwind.framework.redis.Redis;

/**
* @author XJ
* @version  2018年12月18日 
*/
public class ListNameTag extends RequestContextAwareTag {

	private static final long serialVersionUID = -5780771096752935740L;
	
	Logger log = LogManager.getLogger();
	
	private String typeCode;
	
	private String listCode;
	
	@Override
	public int doStartTag() throws JspException {
		log.info("doStartTag...");
		typeCode=(String) pageContext.getAttribute("typeCode");
		listCode=(String) pageContext.getAttribute("listCode");
    	//从redis中获取list_name
    	String listName=(String) Redis.getInstance().hGet(
    					CacheKey.SYS_BOOK_LIST_VALUE, typeCode+"|"+listCode);
        pageContext.setAttribute("listName", listName);
        return 0;
	}
	
	@Override
	public void doCatch(Throwable t) throws Throwable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFinally() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
