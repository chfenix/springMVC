package cn.solwind.admin.common;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
* @author Zln
* @version 2018-11-25 15:33
* 
* 
* 
* Spring容器启动后，初始化数据
*/

@Component
public class StartUpListener{

	Logger log = LogManager.getLogger();

	/**
	 * 初始化配置
	 */
	@PostConstruct
	public void init() {
		log.info("init properties...");
		Constants.initProperties();
		Constants.pageSize = Integer.parseInt(Constants.get("pageSize"));
	}
}
