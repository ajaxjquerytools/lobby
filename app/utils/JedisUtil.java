package utils;

import com.typesafe.plugin.RedisPlugin;
import redis.clients.jedis.Jedis;

public class JedisUtil {
	public static Jedis getJedisResource(){
		return play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
	}

	public static void returnJedisResource(Jedis j){
		play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
	}
}
