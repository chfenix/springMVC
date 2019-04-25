package cn.solwind.framework.helper;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
* @author Zhouluning
* @version 创建时间：2019-03-11
*
* Velocity Helper类
*/

public class VelocityHelper {

	/**
	 * 根据模板及参数获取填充后的完成模板信息
	 * 
	 * @param templageFile
	 * @param params
	 * @return
	 */
	public static String getTemplate(String templageFile, Map<String, Object> params) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
	    ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		Template template = ve.getTemplate(templageFile,"UTF-8");

		VelocityContext context = new VelocityContext();
		if(params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				context.put(entry.getKey(),entry.getValue());
			}
		}
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}
}
