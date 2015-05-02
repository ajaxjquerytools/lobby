package model.handlers;

import java.util.concurrent.LinkedBlockingQueue;

import model.domain.Event;
import model.domain.StatusResponse;
import model.domain.WsMessage;
import play.libs.Json;
import play.mvc.WebSocket;

import com.fasterxml.jackson.databind.JsonNode;

public class SimpleWsOutPool implements WebSocketPool<String> {

	private LinkedBlockingQueue<WebSocket.Out<JsonNode>> wsList;

	private static SimpleWsOutPool members;

	public synchronized static SimpleWsOutPool getInstance() {
		if (members == null) {
			members = new SimpleWsOutPool();
		}
		return members;
	}

	private SimpleWsOutPool() {
		this.wsList = new LinkedBlockingQueue<>();
	}

	@Override
	public void notifyMembers(String message) {
		wsList.forEach(ws -> ws.write(Json.toJson(new WsMessage<>(Event.MESSAGE, StatusResponse.OK, Json.parse(message)))));
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
