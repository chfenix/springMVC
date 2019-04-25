package cn.solwind.tag;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import cn.solwind.admin.entity.Sysbook;
import cn.solwind.framework.common.CacheKey;
import cn.solwind.framework.redis.Redis;

/**
* @author XJ
* @version  2018年12月6日 
*/

public class LevelCodeTag extends RequestContextAwareTag {
	private static final long serialVersionUID = -2204700464695403332L;
	Logger log = LogManager.getLogger();

	private String typeCode;
    
	@Override
	public int doStartTag() throws JspException {
		log.info("doStartTag...");
		typeCode=(String) pageContext.getAttribute("typeCode");
    	//从redis中获取
    	List<Sysbook> listSysbook =(List) Redis.getInstance().hGet(
    					CacheKey.SYS_BOOK_KEY, typeCode);
    	
    	log.info("sysbook data:"+new Gson().toJson(listSysbook));
    	
		Collections.sort(listSysbook, new Comparator<Sysbook>() {
            @Override
            public int compare(Sysbook o1, Sysbook o2) {
                //升序
                return o1.getPri().compareTo(o2.getPri());
            }
		});
		
        pageContext.setAttribute("sysbookList", listSysbook);
        return 0;
	}
	
	
	@Override
	public int doEndTag() throws JspException {
		log.info("doEndTag...");
		return super.doEndTag();
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
