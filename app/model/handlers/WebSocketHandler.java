package model.handlers;

import com.fasterxml.jackson.databind.JsonNode;
/**
 * This class possible to use not in multi node env
 */
public class WebSocketHandler implements MessageHandler {

	public WebSocketPool webSocketPool;

	public WebSocketHandler(SimpleWsOutPool wsOutPool){
		this.webSocketPool = wsOutPool;
	}

	@Override
	public void send(JsonNode jsonData) {
		webSocketPool.notifyMembers(jsonData);
	}
}
