package cn.solwind.admin.config;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import cn.solwind.framework.redis.Redis;
import cn.solwind.framework.redis.RedisConfig;

/**
 * redis配置信息对象
 * 
 * @author zln
 * 
 */

@Configuration
@PropertySource("classpath:redis.properties")
public class CacheConfig {
	
	@Value("${host}")
    private String host;

    @Value("${port}")
    private String port;
    
    @Value("${maxIdle}")
    private String maxIdle;
    
    @Value("${maxTotal}")
    private String maxTotal;
    
    @Value("${maxWaitMillis}")
    private String maxWaitMillis;
    
    @Bean
    public RedisConfig loadConfig() {
    	RedisConfig config = new RedisConfig();
    	config.setIpInfo(host + ":" + port);
    	config.setParamInfo("maxIdle=" + maxIdle + ",maxTotal=" + maxTotal + ",maxWaitMillis=" + maxWaitMillis);
    	
    	Map<String, String> ips = config.getIpInfo();
		Map<String, String> param = config.getParamInfo();
		Redis redis = Redis.getInstance().init(ips, param);
		Redis complexRedis = Redis.getComplexInstance().init(ips, param);
    	return config;
    	
    }
    
    public void init() {
		Map<String, String> ips = new HashMap<>();
		Map<String, String> param = null;
		ips.put(host, port);
		
		Redis redis = Redis.getInstance().init(ips, param);
		Redis complexRedis = Redis.getComplexInstance().init(ips, param);
    }
    

	public String ipInfo;
	public String paramInfo;

	public Map<String, String> getIpInfo() {
		Map<String, String> ips = new IdentityHashMap<String, String>();
		// 解析ＩＰ端口
		/*String[] ipList = ipInfo.split(",");
		for (String ip : ipList) {
			String[] temp = ip.split(":");
			ips.put(temp[0], temp[1]);
		}*/
		
		ips.put(host, port);
		return ips;
	}

	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}

	public Map<String, String> getParamInfo() {
		Map<String, String> conf = null;
		// 解析ＰＯＯＬ参数
		if (StringUtils.isNotBlank(paramInfo)) {
			String[] params = paramInfo.split(",");
			conf = new HashMap<String, String>();
			for (String param : params) {
				String[] temp = param.split("=");
				if (temp.length <= 1) {
					return null;
				}
				conf.put(temp[0], temp[1]);
			}
		}
		return conf;
	}

	public void setParamInfo(String paramInfo) {
		this.paramInfo = paramInfo;
	}

}
