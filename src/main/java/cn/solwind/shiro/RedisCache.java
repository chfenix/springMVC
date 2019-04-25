package cn.solwind.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;

import cn.solwind.framework.redis.Redis;

@Component
public class RedisCache<K, V> implements Cache<K, V> {

	Logger log = LogManager.getLogger();
	
	public static final String KEY_PREFIX = "shiro-redis-session:";

    Redis redis;

    public RedisCache() {
    	// 使用fastjson序列化会丢失sessionid，需要使用apache序列化
    	redis = Redis.getComplexInstance();
    }

    @Override
    public V get(K key) throws CacheException {
        if (key == null) {
            return null;
        }
        if (key instanceof String) {
            String k = (String) key;

            k = KEY_PREFIX + k;
            return (V) redis.get(k);
        }

        return null;
    }

    @Override
    public V put(K key, V value) throws CacheException {

        if (key == null || value == null) {
            return null;
        }

        if (key instanceof String) {
            String k = (String) key;
            k = KEY_PREFIX + k;
            redis.set(k, value,0);
            return value;
        }

        return null;
    }

    @Override
    public V remove(K key) throws CacheException {

        if (key == null) {
            return null;
        }
        if (key instanceof String) {
            String k = (String) key;
            k = KEY_PREFIX + k;
            redis.del(k);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
