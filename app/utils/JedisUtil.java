package utils;

import java.util.function.Consumer;

import redis.clients.jedis.Jedis;

import com.typesafe.plugin.RedisPlugin;

public class JedisUtil {

	public static void doJedisCall(Consumer<Jedis> consumer) {
		Jedis jedis = JedisUtil.getJedisResource();
		try {
			consumer.accept(jedis);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (jedis != null) {
				JedisUtil.returnJedisResource(jedis);
			}
		}
	}

	public static Jedis getJedisResource() {
		return play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
	}

	public static void returnJedisResource(Jedis j) {
		play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
	}
}
