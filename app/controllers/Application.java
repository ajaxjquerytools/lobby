package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.plugin.RedisPlugin;
import play.mvc.*;

import redis.clients.jedis.Jedis;
import views.html.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Application extends Controller {

	public static final AtomicInteger counter = new AtomicInteger(0);

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

	public static Result counter() {
		int counterNum = counter.incrementAndGet();
		String key = "key"+counterNum;
		String value = "value"+counterNum;

		Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
		String redisValue ="NULL";
		try {
			j.set(key , value);
			redisValue = j.get(key);
		}finally {
			play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
		}

		return ok(views.html.counter.render(redisValue));
	}

	public static WebSocket<JsonNode> wsEndpoint() {
		return new WebSocket<JsonNode>() {
			// Called when the Websocket Handshake is done.
			public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){
				// Join the chat room.
				try {
					//Subscribe to Redis channel; to receive a message
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

}
