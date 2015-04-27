package comp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
public class WebSocketHandler implements MessageHandler {

	public WsOutPool wsOutPool;

	public WebSocketHandler(WsOutPool wsOutPool){
		this.wsOutPool = wsOutPool;
	}

	@Override
	public void send(String  message) {

		ObjectNode event = Json.newObject();
		event.put("message", message);
		wsOutPool.send(event);
	}
}
