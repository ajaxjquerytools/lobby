package controllers;

import java.util.concurrent.atomic.AtomicInteger;

import model.domain.*;
import model.handlers.HandlerPool;
import model.handlers.SimpleWsOutPool;
import model.repository.UserRepository;
import model.sub.RedisSub;
import model.sub.Subscriber;
import model.sub.WebSocketSub;
import play.Logger;
import play.libs.Json;
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

	public static UserRepository userRepository = UserRepository.getInstance();

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
				in.onClose(() -> {
					Logger.debug("UNREGISTERING WS...");
					User wsUser = SimpleWsOutPool.getInstance().getUserByWebSocket(out);

					if (wsUser != null) {
						UserRepository.getInstance().remove(wsUser);
					}

					SimpleWsOutPool.getInstance().unregister(out);
				});

				in.onMessage(callback -> {
					JsonNode destination = callback.get("destination");
					JsonNode body = callback.get("body");

					Logger.debug(callback.toString());

					switch (destination.textValue()) {
						case "LOGIN":
							User user = new User(body.textValue());
							if (userRepository.add(user)) {
								Logger.debug("USER ADDED TO ONLINE USERS");
								SimpleWsOutPool.getInstance().register(user, out);
								out.write(Json.toJson(new WsMessage<>(Event.LOGGED_IN, StatusResponse.OK, user.getUsername())));

								//publish event to channel
								HandlerPool.getInstance().send(Json.toJson(new WsMessage<>(Event.ON_USER_CONNECTED, StatusResponse.OK, user.getUsername())));
							} else {
								Logger.debug("user with username {} are already online", user.getUsername());
								out.write(Json.toJson(new WsMessage<>(Event.ERROR, StatusResponse.ERROR, "USER ALREADY ONLINE, TRY OTHER USER")));
							}
							break;
						case "GET_USERS":
							out.write(Json.toJson(new WsMessage<>(Event.ON_ONLINE_USERS, StatusResponse.OK, userRepository.getAll())));
							break;
						case "START_GAME":
							break;
						case "INVITE":
							Invite invite = Json.fromJson(body, Invite.class);
							Logger.debug("invitation sent {}", invite);
							WebSocket.Out<JsonNode> toWebSocket = SimpleWsOutPool.getInstance().getWebSocketByUser(new User(invite.getToUser()));
							toWebSocket.write(Json.toJson(new WsMessage<>(Event.INVITE, StatusResponse.OK, invite)));

							break;
						default:
							Logger.debug("NOTHING TO DO");
					}

				});
			}
		};
	}
}
