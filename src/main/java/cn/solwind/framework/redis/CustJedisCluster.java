package cn.solwind.framework.redis;

import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSlotBasedConnectionHandler;
import redis.clients.util.JedisClusterCRC16;
import redis.clients.util.SafeEncoder;

/**
 * redis Cluster类：
 * 
 * @author kenny
 * 
 */
public class CustJedisCluster extends JedisCluster {

	public CustJedisCluster(Set<HostAndPort> nodes,
			GenericObjectPoolConfig poolConfig) {
		super(nodes, poolConfig);
	}

	/**
	 * 根据KEY返回相应的节点连接
	 * 
	 * @param key
	 * @return
	 */
	public Jedis getJedis(String key) {
		JedisSlotBasedConnectionHandler handler = (JedisSlotBasedConnectionHandler) this.connectionHandler;
		Jedis connectionFromSlot = handler
				.getConnectionFromSlot(JedisClusterCRC16.getSlot(SafeEncoder
						.encode(key)));
		return connectionFromSlot;
	}
}
