package cn.solwind.framework.redis;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * redis配置信息对象
 * 
 * @author kenny
 * 
 */
public class RedisConfig {

	public String ipInfo;
	public String paramInfo;

	public Map<String, String> getIpInfo() {
		Map<String, String> ips = new IdentityHashMap<String, String>();
		// 解析ＩＰ端口
		String[] ipList = ipInfo.split(",");
		for (String ip : ipList) {
			String[] temp = ip.split(":");
			ips.put(temp[0], temp[1]);
		}
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
