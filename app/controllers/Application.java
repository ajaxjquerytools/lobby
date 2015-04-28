package controllers;

import model.SimpleWsOutPool;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import model.HandlerPool;

import java.util.concurrent.atomic.AtomicInteger;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}
	public static AtomicInteger ws = new AtomicInteger(0);

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
					    System.out.println("UNREGISTERING WS...");
						SimpleWsOutPool.getInstance().unregister(out);
				});

				try {
					//Subscribe to Redis channel; to receive a message
					System.out.println("Im ready ws=" + ws.incrementAndGet());
					SimpleWsOutPool.getInstance().register(out);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}
}
