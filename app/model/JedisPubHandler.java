package model;

import redis.clients.jedis.Jedis;
import utils.JedisUtil;
/**
 * Created by volodymyrd on 24.04.15.
 */
public class JedisPubHandler implements MessageHandler {

	@Override
	public void send(String message) {
		System.out.println("Publisher | sending msg to channel");
		Jedis jedis = JedisUtil.getJedisResource();
		try {
			jedis.publish(CHANNEL_NAME, message);
		} finally {
			JedisUtil.returnJedisResource(jedis);
		}
	}
}
