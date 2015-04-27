package model;


import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Akka;
import redis.clients.jedis.Jedis;
import scala.concurrent.duration.Duration;
import utils.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HandlerPool implements MessageHandler {

	private static HandlerPool handlerPool;

	static {
		//subscribe to the message channel
		Akka.system().scheduler().scheduleOnce(
				Duration.create(10, TimeUnit.MILLISECONDS),
				() -> {
					Jedis j = JedisUtil.getJedisResource();
					j.subscribe(new RedisSub(), CHANNEL_NAME);
				},
				Akka.system().dispatcher()
		);
	}


	public synchronized static HandlerPool getInstance() {
		if (handlerPool == null) {
			handlerPool = new HandlerPool();
		}
		return handlerPool;
	}

	private List<MessageHandler> messageHandlers;

	public HandlerPool() {
		messageHandlers = new ArrayList<MessageHandler>() {
			{
				add(new JedisPubHandler());
				add(new WebSocketHandler(SimpleWsOutPool.getInstance()));
			}
		};
	}

	@Override
	public void send(JsonNode jsonData) {
		messageHandlers.forEach(h -> h.send(jsonData));
	}
}
