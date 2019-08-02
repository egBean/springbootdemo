package bootstrap.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void hmset(String key, Map<String,Object> map){
        redisTemplate.opsForHash().putAll(key,map);
    }

    public Map<Object, Object> hgetAll(String key){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        return entries;
    }

    public void expire(String key,long times){
        redisTemplate.expire(key,times, TimeUnit.SECONDS);
    }
}
