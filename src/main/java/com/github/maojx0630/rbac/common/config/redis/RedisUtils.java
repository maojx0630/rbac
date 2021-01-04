package com.github.maojx0630.rbac.common.config.redis;

import com.alibaba.fastjson.JSON;
import com.github.maojx0630.rbac.common.config.global.GlobalStatic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis工具
 * <br/>
 *
 * @author MaoJiaXing
 * @date 2019-07-31 18:18
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class RedisUtils {

    private static final String lua =
            "if redis.call('exists',KEYS[1]) == 1 then return '1' else " + "redis.call" + "('setex"
                    + "',KEYS[1],62,'0') return '0' end";

    private ValueOperations<String, String> valueOperations;

    private HashOperations<String, String, String> hashOperations;

    private StringRedisTemplate redisTemplate;

    public RedisUtils(StringRedisTemplate redisTemplate) {
        valueOperations = redisTemplate.opsForValue();
        hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }

    public <T> Optional<T> get(String key, Class<T> tClass) {
        return toObj(valueOperations.get(key), tClass);
    }

    public <T> Optional<List<T>> getArray(String key, Class<T> tClass) {
        return toArray(valueOperations.get(key), tClass);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void set(String key, Object obj) {
        valueOperations.set(key, toStr(obj));
    }

    public void set(String key, Object obj, long l) {
        valueOperations.set(key, toStr(obj), l, TimeUnit.SECONDS);
    }

    public void set(String key, Object obj, long l, TimeUnit timeUnit) {
        valueOperations.set(key, toStr(obj), l, timeUnit);
    }

    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Long del(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    public void hashPut(String key, String hashKey, Object obj) {
        hashOperations.put(key, hashKey, toStr(obj));
    }

    public <T> Optional<T> hashGet(String key, String hashKey, Class<T> tClass) {
        return toObj(hashOperations.get(key, hashKey), tClass);
    }

    public <T> Optional<List<T>> hashGetArray(String key, Class<T> tClass) {
        return toArray(hashOperations.values(key), tClass);
    }

    public long hashDel(String key, String... hashKey) {
        return hashOperations.delete(key, (Object[]) hashKey);
    }

    public Boolean hashOperationsHasKey(String key, String hashKey) {
        return hashOperations.hasKey(key, hashKey);
    }

    public boolean expire(String key, long l) {
        return redisTemplate.expire(key, l, TimeUnit.SECONDS);
    }

    public boolean expire(String key, long l, TimeUnit timeUnit) {
        return redisTemplate.expire(key, l, timeUnit);
    }

    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    public Set<String> scan(String prefix) {
        Set<String> set = new HashSet<>();
        redisTemplate.execute((RedisConnection connection) -> {
            Cursor<byte[]> cursor = null;
            try {
                cursor = connection.scan(ScanOptions.scanOptions().count(1000).match("*").build());
                cursor.forEachRemaining(next -> set.add(new String(next, GlobalStatic.UTF8)));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != cursor) {
                    try {
                        cursor.close();
                    } catch (Exception e) {
                    }
                }

            }
            return null;
        });
        return set;
    }

    public String repeatToken(String str) {
        return redisTemplate.execute(new DefaultRedisScript<>(lua, String.class),
                Collections.singletonList(str));
    }


    //json相关方法
    private String toStr(Object obj) {
        return JSON.toJSONString(obj);
    }

    private <T> Optional<T> toObj(String json, Class<T> clazz) {
        try {
            return Optional.of(JSON.parseObject(json, clazz));
        } catch (Exception e) {
            log.warn("Json format error | error info {} | Json {}", e.getMessage(), json);
            return Optional.empty();
        }
    }

    private <T> Optional<List<T>> toArray(String json, Class<T> clazz) {
        try {
            List<T> list = JSON.parseArray(json, clazz);
            if (list == null || list.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(list);
            }
        } catch (Exception e) {
            log.warn("Json format error | error info {} | Json {}", e.getMessage(), json);
            return Optional.empty();
        }
    }

    private <T> Optional<List<T>> toArray(List<String> jsonList, Class<T> clazz) {
        try {
            if (jsonList == null || jsonList.isEmpty()) {
                return Optional.empty();
            }
            List<T> list = new ArrayList<>();
            jsonList.forEach(s -> {
                list.add(JSON.parseObject(s, clazz));
            });
            if (list == null || list.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(list);
            }
        } catch (Exception e) {
            log.warn("Json format error | error info {} ", e.getMessage());
            return Optional.empty();
        }
    }

}
