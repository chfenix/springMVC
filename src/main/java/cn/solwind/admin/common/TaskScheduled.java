package cn.solwind.admin.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.solwind.admin.service.common.DBDataService;
import cn.solwind.admin.service.user.FunctionService;

/**
* @author Zln
* @version 2018-12-04 17:37
* 
* 定时任务配置类
*/

@Component
public class TaskScheduled {

	Logger log = LogManager.getLogger();
	
	@Autowired
	DBDataService dbDataService;
	
	@Autowired
	FunctionService functionService;
	
	/**
	 * 刷新数据库配置数据至缓存
	 */
//	@Scheduled(cron="0 0 */12 * * ? ")	// 每12小时执行一次
	@Scheduled(initialDelay=5000, fixedRate=12*60*60*1000)
	public void loadDBData() {
		long startTime = System.currentTimeMillis();
		log.info("DBData load begin-------------------------------------------------");
		// 载入OrderSysbook常量
		dbDataService.loadSysbook();
		
		// 载入Function
		functionService.loadAllFunc();
		
		log.info("DBDataContainer.loadDBData() invoked end time: "+ (System.currentTimeMillis() - startTime) + " ms.");
	}

}
