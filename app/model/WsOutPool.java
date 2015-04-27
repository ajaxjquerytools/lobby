package model;

import java.util.LinkedList;
import java.util.List;

import play.mvc.WebSocket;

import com.fasterxml.jackson.databind.JsonNode;

public class WsOutPool {

	private List<WebSocket.Out<JsonNode>> wsList;

	private static WsOutPool members;

	public synchronized static WsOutPool getInstance() {
		if (members == null) {
			members = new WsOutPool();
		}
		return members;
	}

		private WsOutPool() {
		this.wsList = new LinkedList<>();
	}

	public void send(JsonNode message) {
		wsList.forEach(ws -> ws.write(message));
	}

	public void remove(WebSocket.Out wsOut) {
		wsList.remove(wsOut);
	}

	public void add(WebSocket.Out wsOut) {
		wsList.add(wsOut);
	}
}
