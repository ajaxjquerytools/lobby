package model;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.Event;
import model.domain.StatusResponse;
import model.domain.WsMessage;
import play.libs.Json;
import play.mvc.WebSocket;

public class SimpleWsOutPool implements WebSocketPool<JsonNode> {

	private List<WebSocket.Out<JsonNode>> wsList;

	private static SimpleWsOutPool members;

	public synchronized static SimpleWsOutPool getInstance() {
		if (members == null) {
			members = new SimpleWsOutPool();
		}
		return members;
	}

	private SimpleWsOutPool() {
		this.wsList = new LinkedList<>();
	}

	@Override
	public void notifyMembers(JsonNode message) {
		wsList.forEach(ws -> ws.write(Json.toJson(new WsMessage<>(Event.MESSAGE, StatusResponse.OK, message))));
	}

	@Override
	public void unregister(WebSocket.Out wsOut) {
		wsList.remove(wsOut);
	}

	@Override
	public void register(WebSocket.Out wsOut) {
		wsList.add(wsOut);
	}
}
