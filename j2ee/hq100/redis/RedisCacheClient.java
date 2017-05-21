package com.up72.hq.redis;

import com.up72.hq.utils.ObjectAndByte;
import org.apache.commons.lang.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <desc> </desc>
 *
 * @version V1.1
 * @date 2013-3-20
 */
public class RedisCacheClient {

	private JedisPool jedisPool;

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}


	public void set(String key, Serializable value) {
		this.set(key, value, null);
	}

	public void set(String key, Serializable value, Integer expireSeconds) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key.getBytes(), SerializationUtils.serialize(value));
			if (expireSeconds != null) {
				jedis.expire(key.getBytes(), expireSeconds);
			}
		} finally {
			jedis.close();
		}
	}

	public void hset(String key, String fieid, Serializable value) {
		this.hset(key, fieid, value, null);
	}

	public void hset(String key, String fieid, Serializable value, Integer expireSeconds) {
		Jedis jedis = jedisPool.getResource();
		Object o = "";
		try {
			jedis.hset(key.getBytes(), fieid.getBytes(), SerializationUtils.serialize(value));
			if (expireSeconds != null) {
				jedis.expire(key.getBytes(), expireSeconds);
			}
		} finally {
			jedis.close();
		}
	}

	public void hset(String key, String fieid, Object value, Integer expireSeconds) {
		Jedis jedis = jedisPool.getResource();
		Object o = "";

		try {
			jedis.hset(key.getBytes(), fieid.getBytes(), ObjectAndByte.toByteArray(value));
			if (expireSeconds != null) {
				jedis.expire(key.getBytes(), expireSeconds);
			}
		} finally {
			jedis.close();
		}
	}

	/**
	 * 返回hash中指定存储位置的值
	 *
	 * @param key
	 * @param fieid 存储的名字
	 * @return 存储对应的值
	 */
	public <T> T hget(String key, String fieid) {
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] value = jedis.hget(key.getBytes(), fieid.getBytes());
			if (value != null) {
				return (T) SerializationUtils.deserialize(value);
			}
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 从Hash中删除对象
	 */

	public void hdel(String key, String... field) {
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.hdel(key, field);
		} finally {
			jedis.close();
		}


	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] value = jedis.get(key.getBytes());
			if (value != null) {
				return (T) SerializationUtils.deserialize(value);
			}
			return null;
		} finally {
			jedis.close();
		}
	}

	public void delete(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key.getBytes());
		} finally {
			jedis.close();
		}
	}

	public boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.exists(key.getBytes());
		} finally {
			jedis.close();
		}
	}

	public Set<String> keys(String pattern) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.keys(pattern);
		} finally {
			jedis.close();
		}
	}

	public long lLength(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.llen(key);
		} finally {
			jedis.close();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> lRangeAll(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			List<T> relist = new ArrayList<T>();
			List<byte[]> list = jedis.lrange(key.getBytes(), 0, -1);
			for (byte[] b : list) {
				if (b != null) {
					relist.add((T) SerializationUtils.deserialize(b));
				}
			}
			return relist;
		} finally {
			jedis.close();
		}
	}

	public long lAdd(String key, Serializable value) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.lpush(key.getBytes(), SerializationUtils.serialize(value));
		} finally {
			jedis.close();
		}
	}
}
