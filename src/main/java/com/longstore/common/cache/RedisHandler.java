package com.longstore.common.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longstore.common.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(RedisHandler.class);
    
    private String prefix;
    private JedisPool jedisPool;
    
    public void put(String key, Object value, Integer expire) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null) {
            return;
        }
        byte[] key_b = getKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key_b, SerializeUtil.serialize(value));
            if (expire != null && expire > 0 && expire < Integer.MAX_VALUE) {
                jedis.expire(key_b, expire);
            }
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }

    public <T> T get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] result = jedis.get(getKey(key));
            if (result != null && result.length > 0) {
                return SerializeUtil.unserialize(result);
            }
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
            remove(key);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public void remove(String key){
        if (StringUtils.isBlank(key)) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(getKey(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public void put_str(String key, String value, Integer expire) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(getKey2(key), value);
            if (expire != null && expire > 0 && expire < Integer.MAX_VALUE) {
                jedis.expire(getKey2(key), expire);
            }
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public String get_str(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(getKey2(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
            remove_str(key);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public void remove_str(String key){
        if (StringUtils.isBlank(key)) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(getKey(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public long incr(String key, long value, Integer expire) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        key = getKey2(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long result = jedis.incrBy(key, value);
            if (expire != null && expire > 0 && expire < Integer.MAX_VALUE) {
                jedis.expire(key, expire);
            }
            return result;
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return 0;
    }
    
    public long decr(String key, long value, Integer expire) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        key = getKey2(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long result = jedis.decrBy(key, value);
            if (expire != null && expire > 0 && expire < Integer.MAX_VALUE) {
                jedis.expire(key, expire);
            }
            return result;
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return 0;
    }
    
    public Long getcr(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        key = getKey2(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.get(key);
            if (result != null && result.length() > 0) {
                return Long.valueOf(result);
            }
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Long hincrBy(String key, String field, long value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(getKey2(key), field, value);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public void rpush(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null) {
            return;
        }
        byte[] key_b = getKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpush(key_b, SerializeUtil.serialize(value));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public void rpush_str(String key, String value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpush(getKey2(key), value);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public void lpush(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null) {
            return;
        }
        byte[] key_b = getKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(key_b, SerializeUtil.serialize(value));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public void lpush_str(String key, String value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(getKey2(key), value);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public <T> T lpop(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] result = jedis.lpop(getKey(key));
            if (result != null && result.length > 0) {
                return SerializeUtil.unserialize(result);
            }
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
            remove(key);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public String lpop_str(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(getKey2(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public <T> T rpop(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] result = jedis.rpop(getKey(key));
            if (result != null && result.length > 0) {
                return SerializeUtil.unserialize(result);
            }
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
            remove(key);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public String rpop_str(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpop(getKey2(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public List<byte[]> list(String key, long start, long end) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(getKey(key), start, end);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public List<String> list_str(String key, long start, long end) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(getKey2(key), start, end);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Long llen(String key) {
        if (StringUtils.isBlank(key)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.llen(getKey(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return 0L;
    }
    
    public void zadd(String key, String value, double score) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.zadd(getKey2(key), score, value);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public void zadd(String key, Map<String, Double> scoreMembers) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (scoreMembers == null || scoreMembers.size() == 0) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.zadd(getKey2(key), scoreMembers);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public Set<String> zrange(String key, long start, long end) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrange(getKey2(key), start, end);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Set<String> zrangeByScore(String key, double min, double max) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(getKey2(key), min, max);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public void zrem(String key, String... value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null || value.length==0) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.zrem(getKey2(key), value);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public void publish(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        if (value == null) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.publish(getKey(key), SerializeUtil.serialize(value));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public Long hset_str(String key, String field, String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || value == null) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(getKey2(key), field, value);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Long hset(String key, String field, Object value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || value == null) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(getKey(key), field.getBytes(), SerializeUtil.serialize(value));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    public Long hset(String key, String field, Object value, Integer expire) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || value == null) {
            return null;
        }
        Jedis jedis = null;
        try {
            byte[] key_b = getKey(key);
            jedis = jedisPool.getResource();
            Long result = jedis.hset(key_b, field.getBytes(), SerializeUtil.serialize(value));
            if (expire != null && expire > 0 && expire < Integer.MAX_VALUE) {
                jedis.expire(key_b, expire);
            }
            return result;
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Long hdel(String key, String field) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(getKey(key), field.getBytes());
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Map<byte[], byte[]> hget(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(getKey(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public String hget_str(String key, String field) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(getKey2(key), field);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public <T> T hget(String key, String field) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] result = jedis.hget(getKey(key), field.getBytes());
            if (result != null && result.length > 0) {
                return SerializeUtil.unserialize(result);
            }
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
            remove(key);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Long sadd(String key, Object... members) {
        if (StringUtils.isBlank(key) || members == null || members.length == 0) {
            return null;
        }
        Jedis jedis = null;
        byte[][] ms = new byte[members.length][];
        try {
            for(int i = 0; i < members.length; i++){
                ms[i] = SerializeUtil.serialize(members[i]);
            }
            jedis = jedisPool.getResource();
            return jedis.sadd(getKey(key), ms);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Long sadd_str(String key, String... members) {
        if (StringUtils.isBlank(key) || members == null || members.length == 0) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(getKey2(key), members);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public Set<String> smembers_str(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(getKey2(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return null;
    }
    
    public void expire(String key, int expire) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        byte[] key_b = getKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key_b, expire);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
    }
    
    public Boolean exists(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        byte[] key_b = getKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key_b);
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return false;
    }
    
    public Boolean exists_str(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(getKey2(key));
        } catch (Exception e) {
            LOGGER.error(key + "\n" + e.getMessage(), e);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    LOGGER.error(key + "\n" + e.getMessage(), e);
                }
            }
        }
        return false;
    }
    
    private byte[] getKey(String key){
        return (prefix + key).getBytes();
    }
    
    private String getKey2(String key){
        return prefix + key;
    }

    public void setPrefix(String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            this.prefix = prefix.trim();
        }
    }
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

}
