package model.sub;

import play.Logger;
import redis.clients.jedis.JedisPubSub;
import utils.JedisUtil;

public class RedisSub extends JedisPubSub {

	@Override
	public void onMessage(String channel, String message) {
		Logger.debug("Redis | message received. Channel: {}, Msg: {}", channel, message);

		JedisUtil.doJedisCall(j -> {
			//multi node support
				long keyIndex = j.incr("key_index");
				String key = String.format("key%d", keyIndex);
				j.set(key, message);
				Logger.debug("Redis | key={};value={}", key, j.get(key));
			});
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
