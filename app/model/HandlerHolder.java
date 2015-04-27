package model;


import play.libs.Akka;
import redis.clients.jedis.Jedis;
import scala.concurrent.duration.Duration;
import utils.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HandlerHolder implements MessageHandler {

	private static HandlerHolder handlerHolder;

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


	public synchronized static HandlerHolder getInstance() {
		if (handlerHolder == null) {
			handlerHolder = new HandlerHolder();
		}
		return handlerHolder;
	}

	private List<MessageHandler> messageHandlers;

	public HandlerHolder() {
		messageHandlers = new ArrayList<MessageHandler>() {
			{
				add(new JedisPubHandler());
				add(new WebSocketHandler(SimpleWsOutPool.getInstance()));
			}
		};
	}

	@Override
	public void send(String message) {
		messageHandlers.forEach(h -> h.send(message));
	}
}
