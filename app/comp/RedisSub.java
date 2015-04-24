package comp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import utils.JedisUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class RedisSub extends JedisPubSub {

	private static AtomicInteger keyNum = new AtomicInteger(0);

	private static Logger logger = LoggerFactory.getLogger(RedisSub.class);

	@Override
	public void onMessage(String channel, String message) {
		System.out.println(String.format("Message received. Channel: %s, Msg: %s",channel,message));
		logger.info("Message received. Channel: {}, Msg: {}", channel, message);

		int counterNum = keyNum.incrementAndGet();
		String key = "key" + counterNum;
		Jedis j = JedisUtil.getJedisResource();
		try {
			j.set(key, message);
			System.out.println("Redis | key=" + key + ";value=" + j.get(key));
		} finally {
			JedisUtil.returnJedisResource(j);
		}
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {

	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {

	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {

	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {

	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {

	}
}
