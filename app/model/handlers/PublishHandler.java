package model.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import redis.clients.jedis.Jedis;
import utils.JedisUtil;
/**
 * Created by volodymyrd on 24.04.15.
 */
public class PublishHandler implements MessageHandler {

	@Override
	public void send(JsonNode message) {
		System.out.println("Publisher | sending msg to channel");
		Jedis jedis = JedisUtil.getJedisResource();
		try {
			jedis.publish(MESSAGE_CHANNEL, message.toString());
		} finally {
			JedisUtil.returnJedisResource(jedis);
		}
	}
}
