package cn.solwind.admin.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.solwind.admin.dao.SysbookDao;
import cn.solwind.admin.entity.Sysbook;
import cn.solwind.framework.common.CacheKey;
import cn.solwind.framework.redis.Redis;

/**
* @author Zln
* @version 2018-12-04 13:40
* 
* 系统数据预处理Service
*/

@Service
public class DBDataServiceImpl implements DBDataService {
	
	private static Logger log = LogManager.getLogger();
	
	@Autowired
	SysbookDao sysbookDao;

	@Override
	public void loadSysbook() {
		log.info("DBDataService.loadSysbook invoke!");
		List<Sysbook> listSysbook = sysbookDao.findAllValidSysbook();
		
		if(listSysbook != null && listSysbook.size() > 0) {
			Map<Object, Object> mapSysBook = new HashMap<Object, Object>();
			Map<Object, Object> mapSysBookValue = new HashMap<Object, Object>();
			
			for (Sysbook objSysbook : listSysbook) {
				
				// 封装sysbook结构数据
				List<Sysbook> typeSysbook = null;
				
				if(mapSysBook.get(objSysbook.getTypeCode()) == null) {
					typeSysbook = new ArrayList<Sysbook>();
				}
				else {
					typeSysbook = (List<Sysbook>)mapSysBook.get(objSysbook.getTypeCode());
				}
				
				typeSysbook.add(objSysbook);
				mapSysBook.put(objSysbook.getTypeCode(), typeSysbook);
				
				// 封装sysbook缓存平铺数据
				mapSysBookValue.put(objSysbook.getTypeCode() + "|" + objSysbook.getListCode(), objSysbook.getListName());
			}
		
			Redis redis = Redis.getInstance();
			redis.hmSet(CacheKey.SYS_BOOK_KEY, mapSysBook);
			redis.hmSet(CacheKey.SYS_BOOK_LIST_VALUE, mapSysBookValue);
			
			log.info("load sysbook (" + listSysbook.size() + ")");
		}
		
		log.info("DBDataService.loadSysbook invoke end!");
	}

}
