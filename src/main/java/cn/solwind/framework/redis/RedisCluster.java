package cn.solwind.framework.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;

/**
 * redis集群工具类 
 * 
 * @author kenny
 * 
 */
public class RedisCluster extends Redis {

	private CustJedisCluster cluster;
	Logger log = LogManager.getLogger();
	
	/*
	 * 是否复杂对象，复杂对象使用apache序列化
	 */
	private boolean complexObj = false;

	protected RedisCluster(boolean complexObj) {
		this.complexObj = complexObj;
	}

	/**
	 * 初始化redis连接池
	 * 
	 * @param ips
	 *            ip=port
	 */
	public Redis init(Map<String, String> ips, Map<String, String> config) {
		log.info("init redis: " + ips);
		if (cluster != null) {
			try {
				cluster.close();
				cluster = null;
			} catch (Exception e) {
				log.error(e);
			}
		}
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		if (ips != null) {
			for (Entry<String, String> en : ips.entrySet()) {
				jedisClusterNode.add(new HostAndPort(en.getKey(), Integer
						.valueOf(en.getValue())));
			}
		} else {
			log.error("Can not be empty redis ip");
			throw new RuntimeException("Can not be empty redis ip ");
		}
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		if (config != null) {
			try {
				BeanUtils.copyProperties(poolConfig, config);
			} catch (Exception e) {
				log.error("Can not be Config redis ");
				throw new RuntimeException("Can not be Config redis ");
			}
		} else {
			poolConfig.setMaxTotal(10);
			poolConfig.setMaxIdle(30000);
			poolConfig.setMaxWaitMillis(3000);
			poolConfig.setTestOnBorrow(true);
		}

		cluster = new CustJedisCluster(jedisClusterNode, poolConfig);
		this.ready = true;
		return this;
	}

	/**
	 * 原生接口,用完须close
	 * 
	 * @return
	 */
	public Jedis getJedis(String key) {
		if (cluster == null) {
			log.error("Can not be init redis ");
			return null;
		}
		if (StringUtils.isBlank(key)) {
			log.error("Can not be null for key ");
			return null;
		}
		return cluster.getJedis(key);
	}
	
	/**
	 * 模糊查询
	 * 
	 * @param pattern
	 * @return
	 */
	public TreeSet<String> keys(String pattern) {
		log.info("keys  pattern :  " + pattern);
		TreeSet<String> keys = new TreeSet<String>();
		try {
			Map<String, JedisPool> clusterNodes = this.cluster.getClusterNodes();
			for (String k : clusterNodes.keySet()) {
				JedisPool jp = clusterNodes.get(k);
				Jedis connection = jp.getResource();
				try {
					keys.addAll(connection.keys(pattern));
				} catch (Exception e) {
					log.error("keys  pattern :  " + pattern, e);
				} finally {
					connection.close();
				}
			}
		} catch (Exception e) {
			log.error("keys  pattern :  " + pattern, e);
		}
		return keys;
	}

	/**
	 * 检查KEY是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		log.info("exists  key :  " + key);
		try {
			if(complexObj) {
				// Apache Serialize
				return cluster.exists(key.getBytes());
			}
			else {
				// FastJson
				return cluster.exists(key);	
			}
		} catch (Exception e) {
			log.error("exists  key :  " + key, e);
		}
		return false;
	}

	/**
	 * 将KEY中的值数字减1
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		log.info("decr  key :  " + key);
		try {
			if(complexObj) {
				// Apache Serialize
				return cluster.decr(key.getBytes());
			}
			else {
				// FastJson
				return cluster.decr(key);	
			}
		} catch (Exception e) {
			log.error("decr  key :  " + key, e);
		}
		return null;
	}
	/**
	 * 将KEY中的值数字加1
	 * 
	 * @param key
	 * @return
	 */
	public Long increament(String key) {
		log.info("increament  key :  " + key);
		try {
			if(complexObj) {
				// Apache Serialize
				return cluster.incr(key.getBytes());
			}
			else {
				// FastJson
				return cluster.incr(key);	
			}
		} catch (Exception e) {
			log.error("increament  key :  " + key, e);
		}
		return null;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public boolean expire(String key, int seconds) {
		log.info("set expire key :  " + key);
		try {
			Long expire = null;
			if(complexObj) {
				// Apache Serialize
				expire = cluster.expire(key.getBytes(), seconds);
			}
			else {
				// FastJson
				expire = cluster.expire(key, seconds);				
			}
			return isOk(expire);
		} catch (Exception e) {
			log.error("set expire key :  " + key, e);
		}
		return false;
	}
	
	/**
	 * 设置KEY永久有效，移除过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public boolean persist(String key) {
		log.info("set persist key :  " + key);
		try {
			Long expire = null;
			if(complexObj) {
				// Apache Serialize
				expire = cluster.persist(key.getBytes());
			}
			else {
				// FastJson
				expire = cluster.persist(key);				
			}
			return isOk(expire);
		} catch (Exception e) {
			log.error("set persist key :  " + key, e);
		}
		return false;
	}

	/**
	 * 删除KEY
	 * 
	 * @param key
	 * @return
	 */
	public boolean del(String key) {
		log.info("del  key :  " + key);
		try {
			// 锁的删除须调用delDistributedLock
			if (key.startsWith("lock_"))
				return false;
			if(complexObj) {
				// Apache Serialize
				return isOk(cluster.del(key.getBytes()));
			}
			else {
				// FastJson
				return isOk(cluster.del(key));
			}
		} catch (Exception e) {
			log.error("del  key :  " + key, e);
		}
		return false;
	}

	/**
	 * 用于设置指定 key 的值，并返回 key 旧的值
	 * 
	 * @param key
	 * @return
	 */
	public Object getSet(String key, Object value) {
		log.info("getSet  key :  " + key);
		try {
			if(complexObj) {
				// Apache Serialize
				byte[] set = cluster.getSet(key.getBytes(), SerializationUtils.serialize((Serializable)value));
				return this.parseObject(set);
			}
			else {
				// FastJson
				String set = cluster.getSet(key, this.json(value));
				return this.parseObject(set);
			}
			
		} catch (Exception e) {
			log.error("getSet  key :  " + key, e);
		}
		return null;
	}
	
	/**
	 * 返回KEY所关联的对象
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		log.info("get  key :  " + key);
		try {
			if(complexObj) {
				// Apache Serialize
				return this.parseObject(cluster.get(key.getBytes()));
			}
			else {
				// FastJsoncluster
				return this.parseObject(cluster.get(key));
			}
			
		} catch (Exception e) {
			log.error("get  key :  " + key, e);
		}
		return null;
	}

	/**
	 * 获取锁
	 * 
	 * @param key
	 * @param value
	 * @param seconds 超时时间（单位：秒）0：无有效期
	 * 
	 * @return
	 */
	public boolean setLock(String key, Object value, int seconds) {
		return setMilliLock(key, value, seconds * 1000);
	}
	
	/**
	 * 获取毫秒锁
	 * 
	 * @param key
	 * @param value
	 * @param milliSeconds 超时时间（单位：毫秒）0：无有效期
	 * 
	 * @return
	 */
	public boolean setMilliLock(String key, Object value, int milliSeconds) {
		try {
			Long result = null;
			if(complexObj) {
				// Apache Serialize
				result = cluster.setnx(key.getBytes(), SerializationUtils.serialize((Serializable)value));
			}
			else {
				// FastJson
				result = cluster.setnx(key, this.json(value));
			}
			
			if (isOk(result)) {
				// 获取锁成功，设置有效期
				if (milliSeconds > 0) {
					result = cluster.pexpire(key, milliSeconds);
					if (isOk(result)) {
						// 有效期设置成功，返回true
						log.info("setLock  key :  " + key + " timeout:"
								+ milliSeconds + "ms ");
						return true;
					} else {
						log.info("set lock expired failed! key:" + key);
						// 有效期设置失败，删除锁 KEY
						cluster.del(key);
						return false;
					}
				} else {
					log.info("setLock  key :  " + key + " timeout:" + milliSeconds + "ms");
					return true;
				}
			}
			else {
				log.info("can not get lock! key:" + key + " expire left:" + cluster.pttl(key) + "ms");
			}
		} catch (Exception e) {
			log.error("setLock  key[" + key + "] failed!", e);
		}
		return false;
	}

	/**
	 * 获取锁
	 * 
	 * @param key
	 * @param seconds
	 *            超时时间（单位：秒）0：无有效期
	 * @return
	 */
	public boolean setLock(String key, int seconds) {
		return setLock(key, "1", seconds);
	}
	
	/**
	 * 获取毫秒锁
	 * 
	 * @param key
	 * @param milliSeconds
	 *            超时时间（单位：毫秒）0：无有效期
	 * @return
	 */
	public boolean setMilliLock(String key, int milliSeconds) {
		return setMilliLock(key, "1", milliSeconds);
	}

	/**
	 * 将value关联到KEY， ex可选为指定的 key 设置其过期时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            超时时间，小于等于0是认为无超时时间
	 * @return
	 */
	public boolean set(String key, Object value, int seconds) {
		log.info("set  key :  " + key);
		String result = null;
		try {
			if (seconds > 0) {
				if(complexObj) {
					// Apache Serialize
					result = cluster.setex(key.getBytes(), seconds, SerializationUtils.serialize((Serializable)value));
				} else {
					// FastJson
					result = cluster.setex(key, seconds, this.json(value));
				}
			} else {
				if(complexObj) {
					// Apache Serialize
					result = cluster.set(key.getBytes(), SerializationUtils.serialize((Serializable)value));
				} else {
					// FastJson
					result = cluster.set(key, this.json(value));
				}
			}
			return isOk(result);
		} catch (Exception e) {
			log.error("set  key :  " + key, e);
		}
		return false;
	}

	/**
	 * 返回哈希表中的数量
	 * 
	 * @param key
	 * @return
	 */
	public Long hLen(String key) {
		log.info("hLen  key :  " + key);
		try {
			Long hlen = null;
			if(complexObj) {
				// Apache Serialize
				hlen = cluster.hlen(key.getBytes());
			} else {
				// FastJson
				hlen = cluster.hlen(key);
			}
			
			return hlen;
		} catch (Exception e) {
			log.error("hLen  key :  " + key, e);
		}
		return null;
	}

	/**
	 * 为哈希表中的字段赋值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public boolean hSet(String key, String field, Object value) {
		log.info("hSet  key :" + key + " field:" + field);
		try {
			Long result = null;
			if(complexObj) {
				// Apache Serialize
				result = cluster.hset(key.getBytes(), SerializationUtils.serialize(field), SerializationUtils.serialize((Serializable)value));
			}
			else {
				// FastJson
				result = cluster.hset(key, field, this.json(value));	
			}
			
			return isOk(result);
		} catch (Exception e) {
			log.error("hSet  key :  " + key, e);
		}
		return false;
	}

	/**
	 * 用于同时将多个 field-value (字段-值)对设置到哈希表中
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean hmSet(String key, Map<Object, Object> value) {
		log.info("hmSet  key :  " + key);
		try {
			String result = null;
			if(complexObj) {
				// Apache Serialize
				Map<byte[], byte[]> temp = new LinkedHashMap<byte[], byte[]>();
				for (Entry<Object, Object> next : value.entrySet()) {
					temp.put(
							SerializationUtils.serialize((Serializable)next.getKey()),
							SerializationUtils.serialize((Serializable)next.getValue()));
				}
				result = cluster.hmset(key.getBytes(), temp);	
			}
			else {
				// FastJson
				Map<String, String> temp = new LinkedHashMap<String, String>();
				for (Entry<Object, Object> next : value.entrySet()) {
					temp.put(next.getKey().toString(), this.json(next.getValue()));
				}
				result = cluster.hmset(key, temp);	
			}
			
			return isOk(result);
		} catch (Exception e) {
			log.error("hmSet  key :  " + key, e);
		}
		return false;
	}

	/**
	 * 用于返回哈希表中指定字段的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Object hGet(String key, String field) {
		log.info("hGet  key :  " + key + "." + field);
		try {
			if (field == null) {
				return null;
			}
			
			if(complexObj) {
				// Apache Serialize
				return this.parseObject(cluster.hget(key.getBytes(), SerializationUtils.serialize(field)));
			}
			else {
				// FastJson
				return this.parseObject(cluster.hget(key, field));
			}
		} catch (Exception e) {
			log.error("hGet  key :  " + key + "." + field, e);
		}
		return null;
	}

	/**
	 * 用于返回哈希表中，所有的字段和值
	 * 
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hGetAll(String key) {
		log.info("hGetAll  key :  " + key);
		try {
			if(complexObj) {
				// Apache Serialize
				Map<byte[], byte[]> hgetAll = cluster.hgetAll(key.getBytes());
				Map<Object, Object> map = new LinkedHashMap<Object, Object>();
				for (Entry<byte[], byte[]> en : hgetAll.entrySet()) {
					map.put(
							SerializationUtils.deserialize(en.getKey()),
							SerializationUtils.deserialize(en.getValue()));
				}
				return map;
			}
			else {
				// FastJson
				Map<String, String> hgetAll = cluster.hgetAll(key);
				Map<Object, Object> map = new LinkedHashMap<Object, Object>();
				for (Entry<String, String> en : hgetAll.entrySet()) {
					map.put(en.getKey(), this.parseObject(en.getValue()));
				}
				return map;	
			}
			
		} catch (Exception e) {
			log.error("hGetAll  key :  " + key, e);
		}
		return null;
	}

	/**
	 * 用于返回哈希表中，一个或多个给定字段的值
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<Object> hmGet(String key, Object... fields) {
		log.info("hmGet  key :  " + StringUtils.join(fields, ","));
		try {
			
			if(complexObj) {
				// Apache Serialize
				byte[][] fd = new byte[fields.length][];
				for (int i = 0; i < fields.length; i++) {
					fd[i] = SerializationUtils.serialize((Serializable)fields[i]);
				}
				
				List<byte[]> hmget = cluster.hmget(key.getBytes(), fd);
				List<Object> list = new ArrayList<Object>();
				for (byte[] object : hmget) {
					list.add(this.parseObject(object));
				}
				return list;
			}
			else {
				// FastJson
				String[] fd = new String[fields.length];
				for (int i = 0; i < fields.length; i++) {
					fd[i] = fields[i].toString();
				}
				
				List<String> hmget = cluster.hmget(key, fd);
				List<Object> list = new ArrayList<Object>();
				for (String object : hmget) {
					list.add(this.parseObject(object));
				}
				return list;
			}
			
		} catch (Exception e) {
			log.error("hmGet  key :  " + key, e);
		}
		return null;
	}
	
	public Long hDel(String key, Object... fields) {
		log.info("hmGet  key :  " + StringUtils.join(fields, ","));
		try {
			
			if(complexObj) {
				// Apache Serialize
				byte[][] fd = new byte[fields.length][];
				for (int i = 0; i < fields.length; i++) {
					fd[i] = SerializationUtils.serialize((Serializable)fields[i]);
				}
				
				Long hdel = cluster.hdel(key.getBytes(), fd);
				return hdel;
			}
			else {
				// FastJson
				String[] fd = new String[fields.length];
				for (int i = 0; i < fields.length; i++) {
					fd[i] = fields[i].toString();
				}
				
				Long hdel = cluster.hdel(key, fd);
				return hdel;
			}
			
		} catch (Exception e) {
			log.error("hdel  key :  " + key, e);
		}
		return null;
	}
	
	protected boolean isComplexObj() {
		return complexObj;
	}

	
	@Override
	public boolean expireAt(String key, Long timestamp) {
		log.info("expireAt  key :  " + key + ", timestamp : " + timestamp);
		try {
			if (complexObj) {
				Long expireAt = cluster.expireAt(key.getBytes(), timestamp);
				return isOk(expireAt);
			} else {
				Long expireAt = cluster.expireAt(key, timestamp);
				return isOk(expireAt);
			}
		} catch (Exception e) {
			log.error("expireAt  key :  " + key + ", timestamp : " + timestamp, e);
		}
		return false;
	}

	@Override
	public boolean move(String key, String db) {
		log.info("move  key :  " + key + ", db : " + db);
		try {
			Long move = cluster.move(key, Integer.parseInt(db));
			return isOk(move);
		} catch (Exception e) {
			log.error("move  key :  " + key + ", db : " + db, e);
		}
		return false;
	}

	@Override
	public Long pttl(String key) {
		log.info("pttl  key :  " + key );
		try {
			if (complexObj) {
				return -3l;
			} else {
				Long pttl = cluster.pttl(key);
				return pttl;
			}
		} catch (Exception e) {
			log.error("pttl  key :  " + key , e);
		}
		return -3l;
	}


	@Override
	public boolean rename(String key, String newKey) {
		log.info("rename  key :  " + key +", newKey : " + newKey );
		try {
			if (complexObj) {
				String rename = cluster.rename(key.getBytes(), newKey.getBytes());
				return isOk(rename);
			} else {
				String rename = cluster.rename(key, newKey);
				return isOk(rename);
			}
		} catch (Exception e) {
			log.error("rename  key :  " + key +", newKey : " + newKey , e );
		}
		return false;
	}

	@Override
	public boolean renameNx(String key, String newKey) {
		log.info("renameNx  key :  " + key +", newKey : " + newKey );
		try {
			if (complexObj) {
				Long renamenx = cluster.renamenx(key.getBytes(), newKey.getBytes());
				return isOk(renamenx);
			} else {
				Long renamenx = cluster.renamenx(key, newKey);
				return isOk(renamenx);
			}
		} catch (Exception e) {
			log.error("renameNx  key :  " + key +", newKey : " + newKey, e);
		}
		return false;
	}

	@Override
	public String type(String key) {
		log.info("type  key :  " + key );
		try {
			if (complexObj) {
				String type = cluster.type(key.getBytes());
				return type;
			} else {
				String type = cluster.type(key);
				return type;
			}
		} catch (Exception e) {
			log.error("type  key :  " + key, e);
		}
		return null;
	}

	@Override
	public long append(String key, String value) {
		log.info("append  key :  " + key + ", value : " + value);
		try {
			if (complexObj) {
				// Apache Serialize
				return 0;
			} else {
				// FastJson
				Long append = cluster.append(key, value);
				return append;
			}
		} catch (Exception e) {
			log.error("append  key :  " + key + "." + value, e);
		}
		return 0;
	}

	@Override
	public String getRange(String key, long start, long end) {
		log.info("getRange  key :  " + key + "," + start+ "," + end);
		try {
			if (complexObj) {
				return null;
			} else {
				String getrange = cluster.getrange(key, start, end);
				return getrange;
			}
		} catch (Exception e) {
			log.error("getRange  key :  " + key + "," + start+ "," + end, e);
		}
		return null;
	}

	@Override
	public Long incrBy(String key, long increment) {
		log.info("incrBy  key :  " + key + ", increment: " + increment);
		try {
			if (complexObj) {
				Long incrBy = cluster.incrBy(key.getBytes(), increment);
				return incrBy;
			} else {
				Long incrBy = cluster.incrBy(key, increment);
				return incrBy;
			}
		} catch (Exception e) {
			log.error("incrBy  key :  " + key + ", increment" + increment, e);
		}
		return null;
	}

	@Override
	public Long decrBy(String key, long decrement) {
		log.info("decrBy  key :  " + key + ", increment: " + decrement);
		try {
			if (complexObj) {
				Long decrBy = cluster.decrBy(key.getBytes(), decrement);
				return decrBy;
			} else{
				Long decrBy = cluster.decrBy(key, decrement);
				return decrBy;
			}
		} catch (Exception e) {
			log.error("decrBy  key :  " + key + ", increment" + decrement, e);
		}
		return null;
	}

	@Override
	public List mGet(String... key) {
		log.info("mGet  key :  " + StringUtils.join(key, ",") );
		try {
			if (complexObj) {
				// Apache Serialize
				byte[][] keys = new byte[key.length][];
				for (int i = 0; i < key.length; i++) {
					keys[i] = key[i].getBytes();
				}

				List<byte[]> mget = cluster.mget(keys);
				List<Object> result = new ArrayList<Object>();
				for (byte[] bs : mget) {
					Object parseObject = this.parseObject(bs);
					result.add(parseObject);
				}
				return result;
			} else {
				// FastJson
				List<String> mget = cluster.mget(key);
				List<Object> list = new ArrayList<Object>();
				for (String object : mget) {
					list.add(this.parseObject(object));
				}
				return list;
			}
		} catch (Exception e) {
			log.error("mGet  key :  " + key , e);
		}
		return null;
	}

	@Override
	public boolean mSet(Object... keysvalues) {
		log.info("mSet  keysvalues :  " + StringUtils.join(keysvalues, ","));
		try {
			if (complexObj) {
				// Apache Serialize
				byte[][] args = new byte[keysvalues.length][];
				for (int i = 0; i < keysvalues.length; i++) {
					if (i % 2 == 0) {
						args[i] = keysvalues[i].toString().getBytes();
					} else {
						args[i] = SerializationUtils.serialize((Serializable) keysvalues[i]);
					}
				}

				String mset = cluster.mset(args);
				return isOk(mset);
			} else {
				// FastJson
				String[] args = new String[keysvalues.length];
				for (int i = 0; i < args.length; i++) {
					if (i % 2 == 0) {
						args[i] = keysvalues[i].toString();
					} else {
						args[i] = this.json(keysvalues[i]);
					}
				}
				String mset = cluster.mset(args);
				return isOk(mset);
			}
		} catch (Exception e) {
			log.error("mSet  keysvalues :  " + keysvalues, e);
		}
		return false;
	}

	@Override
	public int setRange(String key, long offset, Object value) {
		log.info("setRange  key :  " + key + ", offset:" + offset + ", value:" + value);
		try {
			if (complexObj) {
				return 0;
			} else {
				// FastJson
				Long setrange = cluster.setrange(key, offset, value.toString());
				return setrange.intValue();
			}
		} catch (Exception e) {
			log.error("setRange  key :  " + key + ", offset:" + offset + ", value:" + value, e);
		}
		return 0;
	}

	@Override
	public int strLen(String key) {
		log.info("strLen  key :  " + key);
		try {
			if (complexObj) {
				Long strlen = cluster.strlen(key.getBytes());
				return strlen.intValue();
			} else {
				// FastJson
				Long strlen = cluster.strlen(key);
				return strlen.intValue();
			}
		} catch (Exception e) {
			log.error("strLen  key :  " + key, e);
		}
		return 0;
	}

	@Override
	public Set hKeys(String key) {
		log.info("hKeys  key :  " + key);
		try {
			if (complexObj) {
				Set<byte[]> hkeys = cluster.hkeys(key.getBytes());
				Set<Object> keys = new HashSet<Object>();
				for (byte[] bs : hkeys) {
					keys.add(SerializationUtils.deserialize(bs));
				}
				return keys;
			} else {
				// FastJson
				Set<String> hkeys = cluster.hkeys(key);
				return hkeys;
			}
		} catch (Exception e) {
			log.error("hKeys  key :  " + key, e);
		}
		return null;
	}

	@Override
	public List hVals(String key) {
		log.info("hVals  key :  " + key);
		try {
			if (complexObj) {
				Collection<byte[]> hvals = cluster.hvals(key.getBytes());
				List<Object> vals = new ArrayList<Object>();
				for (byte[] bs : hvals) {
					vals.add(this.parseObject(bs));
				}
				return vals;
			} else {
				// FastJson
				List<String> hvals = cluster.hvals(key);
				List<Object> vals = new ArrayList<Object>();
				for (String string : hvals) {
					vals.add(this.parseObject(string));
				}
				return vals;
			}
		} catch (Exception e) {
			log.error("hVals  key :  " + key, e);
		}
		return null;
	}

	@Override
	public List bLpop(Long timeout, String... key) {
		log.info("bLpop  key :  " + StringUtils.join(key, ",") + ", timeout  : " +timeout);
		try {
			if (complexObj) {
				byte[][] args = new byte[key.length][];
				for (int i = 0; i < key.length; i++) {
					args[i] = key[i].getBytes();
				}
				List<byte[]> blpop = cluster.blpop(timeout.intValue(), args);
				if(blpop == null || blpop.isEmpty()){
					return null;
				}
				String result = new String(blpop.get(0));
				if(this.isNull(result)){
					return null;
				}
				List<Object> vals = new ArrayList<Object>();
				vals.add(result);
				vals.add(this.parseObject(blpop.get(1)));
				return vals;
			} else {
				// FastJson
				List<String> blpop = cluster.blpop(timeout.intValue(), key);
				if(blpop == null || blpop.isEmpty()){
					return null;
				}
				String result = blpop.get(0);
				if(this.isNull(result)){
					return null;
				}
				List<Object> vals = new ArrayList<Object>();
				for (String string : blpop) {
					vals.add(this.parseObject(string));
				}
				return vals;
			}
		} catch (Exception e) {
			log.error("bLpop  key :  " + key + ", timeout  : " +timeout, e);
		}
		return null;
	}

	@Override
	public List bRpop(Long timeout, String... key) {
		log.info("brpop  key :  " + StringUtils.join(key, ",") + ", timeout  : " +timeout);
		try {
			if (complexObj) {
				byte[][] args = new byte[key.length][];
				for (int i = 0; i < key.length; i++) {
					args[i] = key[i].getBytes();
				}
				List<byte[]> brpop = cluster.brpop(timeout.intValue(), args);
				if(brpop == null || brpop.isEmpty()){
					return null;
				}
				String result = new String(brpop.get(0));
				if(this.isNull(result)){
					return null;
				}
				List<Object> vals = new ArrayList<Object>();
				vals.add(result);
				vals.add(this.parseObject(brpop.get(1)));
				return vals;
			} else {
				// FastJson
				List<String> brpop = cluster.brpop(timeout.intValue(), key);
				if(brpop == null || brpop.isEmpty()){
					return null;
				}
				String result = brpop.get(0);
				if(this.isNull(result)){
					return null;
				}
				List<Object> vals = new ArrayList<Object>();
				for (String string : brpop) {
					vals.add(this.parseObject(string));
				}
				return vals;
			}
		} catch (Exception e) {
			log.error("brpop  key :  " + key + ", timeout  : " +timeout, e);
		}
		return null;
	}

	@Override
	public Object bRpopLpush(Long timeout, String source, String destination) {
		log.info("bRpopLpush  source :  " + source + ", destination : " +destination +" , timeout : " + timeout);
		try {
			if (complexObj) {
				byte[] brpoplpush = cluster.brpoplpush(source.getBytes(), destination.getBytes(), timeout.intValue());
				String result = new String(brpoplpush);
				if(this.isNull(result)){
					return null;
				}
				return this.parseObject(brpoplpush);
			} else {
				// FastJson
				String brpoplpush = cluster.brpoplpush(source, destination, timeout.intValue());
				String result = brpoplpush;
				if(this.isNull(result)){
					return null;
				}
				return this.parseObject(brpoplpush);
			}
		} catch (Exception e) {
			log.error("bRpopLpush  source :  " + source + ", destination : " +destination +" , timeout : " + timeout, e);
		}
		return null;
	}

	@Override
	public Object lIndex(String key, long index) {
		log.info("lIndex  key :  " + key + " , index : " + index);
		try {
			if (complexObj) {
				byte[] lindex = cluster.lindex(key.getBytes(), index);
				String result = new String(lindex);
				if (this.isNull(result)) {
					return null;
				}
				return this.parseObject(lindex);
			} else {
				// FastJson
				String lindex = cluster.lindex(key, index);
				String result = lindex;
				if (this.isNull(result)) {
					return null;
				}
				return this.parseObject(lindex);
			}
		} catch (Exception e) {
			log.error("lIndex  key :  " + key + " , index : " + index, e);
		}
		return null;
	}

	@Override
	public Long lInsert(String key, LIST_POSITION position, Object pivot, Object value) {
		log.info("lInsert  key :  " + key + " , position : " + position + " , pivot :" + pivot +" , value : "+value);
		try {
			if (complexObj) {
				byte[] serialize = SerializationUtils.serialize((Serializable) value);
				byte[] pt = SerializationUtils.serialize((Serializable) pivot);
				Long linsert = cluster.linsert(key.getBytes(), position, pt, serialize);
				return linsert;
			} else {
				// FastJson
				Long linsert = cluster.linsert(key, position, this.json(pivot), this.json(value));
				return linsert;
			}
		} catch (Exception e) {
			log.error("lInsert  key :  " + key + " , position : " + position + " , pivot :" + pivot +" , value : "+value, e);
		}
		return null;
	}

	@Override
	public Long lLen(String key) {
		log.info("lLen  key :  " + key);
		try {
			if (complexObj) {
				Long llen = cluster.llen(key.getBytes());
				return llen;
			} else {
				// FastJson
				Long llen = cluster.llen(key);
				return llen;
			}
		} catch (Exception e) {
			log.error("lLen  key :  " + key, e);
		}
		return null;
	}

	@Override
	public Object lPop(String key) {
		log.info("lPop  key :  " + key);
		try {
			if (complexObj) {
				byte[] lpop = cluster.lpop(key.getBytes());
				return this.parseObject(lpop);
			} else {
				// FastJson
				String lpop = cluster.lpop(key);
				return this.parseObject(lpop);
			}
		} catch (Exception e) {
			log.error("lPop  key :  " + key, e);
		}
		return null;
	}

	@Override
	public long lPush(String key, Object... values) {
		log.info("lPush  key :  " + key + " , values : " + StringUtils.join(values, ","));
		try {
			if (complexObj) {
				byte[][] args = new byte[values.length][];
				for (int i = 0; i < values.length; i++) {
					byte[] serialize = SerializationUtils
							.serialize((Serializable) values[i]);
					args[i] = serialize;
				}
				Long lpush = cluster.lpush(key.getBytes(), args);
				return lpush;
			} else {
				// FastJson
				String[] args = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					args[i] = this.json(values[i]);
				}
				Long lpush = cluster.lpush(key, args);
				return lpush;
			}
		} catch (Exception e) {
			log.error("lPush  key :  " + key + " , values : " + values, e);
		}
		return -1l;
	}

	@Override
	public List lRange(String key, int start, int stop) {
		log.info("lRange  key :  " + key + " , start : " + start + ", stop : "
				+ stop);
		try {
			if (complexObj) {
				List<byte[]> lrange = cluster.lrange(key.getBytes(), start,
						stop);
				List<Object> result = new ArrayList<Object>();
				for (byte[] bs : lrange) {
					Object parseObject = this.parseObject(bs);
					result.add(parseObject);
				}
				return result;
			} else {
				// FastJson
				List<String> lrange = cluster.lrange(key, start, stop);
				List<Object> result = new ArrayList<Object>();
				for (String bs : lrange) {
					Object parseObject = this.parseObject(bs);
					result.add(parseObject);
				}
				return result;
			}
		} catch (Exception e) {
			log.error("lRange  key :  " + key + " , start : " + start
					+ ", stop : " + stop, e);
		}
		return null;
	}

	@Override
	public int lRem(String key, long count, Object value) {
		log.info("lRem  key :  " + key + " , count : " + count + ", value : "
				+ value);
		try {
			if (complexObj) {
				byte[] serialize = SerializationUtils
						.serialize((Serializable) value);
				Long lrem = cluster.lrem(key.getBytes(), count, serialize);
				return lrem.intValue();
			} else {
				// FastJson
				Long lrem = cluster.lrem(key, count, this.json(value));
				return lrem.intValue();
			}
		} catch (Exception e) {
			log.error("lRem  key :  " + key + " , count : " + count
					+ ", value : " + value, e);
		}
		return 0;
	}

	@Override
	public boolean lSet(String key, long index, Object value) {
		log.info("lSet  key : " + key + " , index : " + index + ", value : "
				+ value);
		try {
			if (complexObj) {
				byte[] serialize = SerializationUtils
						.serialize((Serializable) value);
				String lset = cluster.lset(key.getBytes(), index, serialize);
				return isOk(lset);
			} else {
				// FastJson
				String lset = cluster.lset(key, index, this.json(value));
				return isOk(lset);
			}
		} catch (Exception e) {
			log.error("lSet  key :  " + key + " , index : " + index
					+ ", value : " + value, e);
		}
		return false;
	}

	@Override
	public boolean lTrim(String key, Long start, Long stop) {
		log.info("lTrim  key : " + key + " , start : " + start + ", stop : "
				+ stop);
		try {
			if (complexObj) {
				String ltrim = cluster.ltrim(key.getBytes(), start, stop);
				return isOk(ltrim);
			} else {
				// FastJson
				String ltrim = cluster.ltrim(key, start, stop);
				return isOk(ltrim);
			}
		} catch (Exception e) {
			log.error("lTrim  key : " + key + " , start : " + start
					+ ", stop : " + stop, e);
		}
		return false;
	}

	@Override
	public Object rPop(String key) {
		log.info("rPop  key : " + key);
		try {
			if (complexObj) {
				byte[] rpop = cluster.rpop(key.getBytes());
				String result = new String(rpop);
				if (this.isNull(result)) {
					return null;
				} else {
					return this.parseObject(rpop);
				}
			} else {
				// FastJson
				String rpop = cluster.rpop(key);
				if (this.isNull(rpop)) {
					return null;
				} else {
					return this.parseObject(rpop);
				}
			}
		} catch (Exception e) {
			log.error("rPop  key : " + key, e);
		}
		return null;
	}

	@Override
	public Object rPopLpush(String srckey, String dstkey) {
		log.info("rPopLpush  srckey : " + srckey + ", dstkey :" + dstkey);
		try {
			if (complexObj) {
				byte[] rpoplpush = cluster.rpoplpush(srckey.getBytes(),
						dstkey.getBytes());
				String result = new String(rpoplpush);
				if (this.isNull(result)) {
					return null;
				}else{
					return this.parseObject(rpoplpush);
				}
			} else {
				// FastJson
				String rpoplpush = cluster.rpoplpush(srckey, dstkey);
				if (this.isNull(rpoplpush)) {
					return null;
				} else {
					return this.parseObject(rpoplpush);
				}
			}
		} catch (Exception e) {
			log.error("rPopLpush  srckey : " + srckey + ", dstkey :" + dstkey,
					e);
		}
		return null;
	}

	@Override
	public Long rPush(String key, Object... values) {
		log.info("rPush  key : " + key + ", value: " + StringUtils.join(values, ","));
		try {
			if (complexObj) {
				byte[][] args = new byte[values.length][];
				for (int i = 0; i < values.length; i++) {
					byte[] serialize = SerializationUtils
							.serialize((Serializable) values[i]);
					args[i] = serialize;
				}
				Long rpush = cluster.rpush(key.getBytes(), args);
				return rpush;
			} else {
				// FastJson
				String[] args = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					args[i] = this.json(values[i]);
				}
				Long rpush = cluster.rpush(key, args);
				return rpush;
			}
		} catch (Exception e) {
			log.error("rPush  key : " + key + ", value: " + values, e);
		}
		return null;
	}

	@Override
	public List sort(String key, SortingParams sortingParameters) {
		log.info("sort  key : " + key + ", sortingParameters: "
				+ sortingParameters);
		try {
			if (complexObj) {
				List<byte[]> sort = cluster.sort(key.getBytes(),
						sortingParameters);
				List<Object> result = new ArrayList<Object>();
				for (byte[] bs : sort) {
					Object parseObject = this.parseObject(bs);
					result.add(parseObject);
				}
				return result;
			} else {
				// FastJson
				List<String> sort = cluster.sort(key, sortingParameters);
				List<Object> result = new ArrayList<Object>();
				for (String bs : sort) {
					Object parseObject = this.parseObject(bs);
					result.add(parseObject);
				}
				return result;
			}
		} catch (Exception e) {
			log.error("sort  key : " + key + ", sortingParameters: "
					+ sortingParameters, e);
		}
		return null;
	}

	@Override
	public Long sort(String key, SortingParams sortingParameters, String dstkey) {
		log.info("sort  key : " + key + ", sortingParameters: "
				+ sortingParameters + ", dstkey:" + dstkey);
		try {
			if (complexObj) {
				Long sort = cluster.sort(key.getBytes(), sortingParameters,
						dstkey.getBytes());
				return sort;
			} else {
				// FastJson
				Long sort = cluster.sort(key, sortingParameters, dstkey);
				return sort;
			}
		} catch (Exception e) {
			log.error("sort  key : " + key + ", sortingParameters: "
					+ sortingParameters + ", dstkey:" + dstkey, e);
		}
		return 0l;
	}
}
