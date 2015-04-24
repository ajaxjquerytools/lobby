package controllers;

import java.util.concurrent.atomic.AtomicInteger;

import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import redis.clients.jedis.Jedis;
import utils.JedisUtil;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import comp.HandlerHolder;

public class Application extends Controller {

	public static final AtomicInteger counter = new AtomicInteger(0);

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result counter() {
		int counterNum = counter.incrementAndGet();
		String key = "key" + counterNum;
		String value = "value" + counterNum;

		Jedis j = JedisUtil.getJedisResource();
		String redisValue = "NULL";
		try {
			j.set(key, value);
			redisValue = j.get(key);
		} finally {
			JedisUtil.returnJedisResource(j);
		}

		return ok(views.html.counter.render(redisValue));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result message() {
		JsonNode json = request().body().asJson();
		String message = json.findPath("message").textValue();

		if(message == null){
			return badRequest("Missing parameter [message]");
		}

		HandlerHolder.getInstance().send(message);
		return ok();
	}

	public static WebSocket<JsonNode> wsEndpoint() {
		return new WebSocket<JsonNode>() {

			// Called when the Websocket Handshake is done.
			public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
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
