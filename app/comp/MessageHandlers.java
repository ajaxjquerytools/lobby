package comp;

import play.api.libs.json.Json;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by volodymyrd on 24.04.15.
 */
public class MessageHandlers implements MessageHandler{

	private List<MessageHandler> messageHandlers;

	public MessageHandlers() {
		messageHandlers = new ArrayList<MessageHandler>(){{
			add(new JedisPubHandler());
			add(new WebSocketHandler());
		}};
	}

	@Override public void send(Json message) {
		messageHandlers.forEach(h -> h.send(message));
	}
}
