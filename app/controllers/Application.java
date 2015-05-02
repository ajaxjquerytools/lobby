package controllers;

import java.util.concurrent.atomic.AtomicInteger;

import model.handlers.HandlerPool;
import model.handlers.SimpleWsOutPool;
import model.sub.RedisSub;
import model.sub.Subscriber;
import model.sub.WebSocketSub;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static AtomicInteger ws = new AtomicInteger(0);

	static {
		//subscribe to the message channel
		Subscriber.subscribeToMessageChannel(new RedisSub());
		Subscriber.subscribeToMessageChannel(new WebSocketSub(SimpleWsOutPool.getInstance()));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result message() {
		JsonNode jsonData = request().body().asJson();
		String message = jsonData.findPath("message").textValue();

		if (message == null) {
			return badRequest("Missing parameter [message]");
		}

		HandlerPool.getInstance().send(jsonData);
		return ok();
	}

	public static WebSocket<JsonNode> wsmessage() {
		return new WebSocket<JsonNode>() {

			// Called when the Websocket Handshake is done.
			public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
				// Join the chat room.
				in.onClose(() -> {
					Logger.debug("UNREGISTERING WS...");
					SimpleWsOutPool.getInstance().unregister(out);
				});

				try {
					//Subscribe to Redis channel; to receive a message
					Logger.debug("Im ready ws={}", ws.incrementAndGet());
					SimpleWsOutPool.getInstance().register(out);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}
}
