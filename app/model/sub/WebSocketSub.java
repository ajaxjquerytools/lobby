package model.sub;

import model.handlers.WebSocketPool;
import play.Logger;
import redis.clients.jedis.JedisPubSub;
/**
 * Created by volodymyrd on 29.04.15.
 */
public class WebSocketSub extends JedisPubSub {

	private WebSocketPool<String> webSocketPool;

	public WebSocketSub(WebSocketPool<String> webSocketPool){
		this.webSocketPool = webSocketPool;
	}

	@Override public void onMessage(String channel, String message) {
		Logger.debug("WS sub | message for received message:{}", message);
		webSocketPool.notifyMembers(message);
	}

	@Override public void onPMessage(String s, String s1, String s2) {

	}

	@Override public void onSubscribe(String s, int i) {

	}

	@Override public void onUnsubscribe(String s, int i) {

	}

	@Override public void onPUnsubscribe(String s, int i) {

	}

	@Override public void onPSubscribe(String s, int i) {

	}
}
