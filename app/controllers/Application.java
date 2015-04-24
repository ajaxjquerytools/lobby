package controllers;


import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import comp.HandlerHolder;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
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
