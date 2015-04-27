package model;

import com.fasterxml.jackson.databind.JsonNode;
public class WebSocketHandler implements MessageHandler {

	public SimpleWsOutPool wsOutPool;

	public WebSocketHandler(SimpleWsOutPool wsOutPool){
		this.wsOutPool = wsOutPool;
	}

	@Override
	public void send(JsonNode jsonData) {
		wsOutPool.notifyMembers(jsonData);
	}
}
