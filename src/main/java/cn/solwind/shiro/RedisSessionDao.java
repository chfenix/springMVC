package cn.solwind.shiro;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.stereotype.Component;

import cn.solwind.framework.redis.Redis;

@Component
public class RedisSessionDao extends EnterpriseCacheSessionDAO {
	
	Logger log = LogManager.getLogger();
	
	Redis redis;

    public RedisSessionDao() {
    	// 使用fastjson序列化会丢失sessionid，需要使用apache序列化
    	redis = Redis.getComplexInstance();
    }

    private long sessionExpireTime = 30 * 60;

    private void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }

        String key = RedisCache.KEY_PREFIX + session.getId();
        session.setTimeout(sessionExpireTime * 1000);
        redis.set(key, session, (int)sessionExpireTime);
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }

        String key = RedisCache.KEY_PREFIX + sessionId;
        Session session = (Session) redis.get(key);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {

        String key = RedisCache.KEY_PREFIX + session.getId();
        redis.del(key);
    }
}
