package model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
public class WebSocketHandler implements MessageHandler {

	public SimpleWsOutPool wsOutPool;

	public WebSocketHandler(SimpleWsOutPool wsOutPool){
		this.wsOutPool = wsOutPool;
	}

	@Override
	public void send(String  message) {

		ObjectNode event = Json.newObject();
		event.put("message", message);
		wsOutPool.notifyMembers(event);
	}
}
