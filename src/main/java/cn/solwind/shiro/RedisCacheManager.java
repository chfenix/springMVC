package cn.solwind.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheManager implements CacheManager {

	Logger log = LogManager.getLogger();

	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache = caches.get(name);
        if (cache == null) {
            cache = new RedisCache();
            caches.put(name, cache);
        }
        return cache;
    }
}
