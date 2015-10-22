package model.handlers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.domain.User;
import play.libs.Json;
import play.mvc.WebSocket;

import com.fasterxml.jackson.databind.JsonNode;

public class SimpleWsOutPool implements WebSocketPool<String> {

//	private LinkedBlockingQueue<WebSocket.Out<JsonNode>> wsList;

	private ConcurrentHashMap<WebSocket.Out<JsonNode>, User> wsMap;

	private static SimpleWsOutPool members;

	public synchronized static SimpleWsOutPool getInstance() {
		if (members == null) {
			members = new SimpleWsOutPool();
		}
		return members;
	}

	private SimpleWsOutPool() {
		this.wsMap = new ConcurrentHashMap<>();
	}

	@Override
	public void notifyMembers(String wsMessage) {
		wsMap.keySet().forEach(ws -> ws.write(Json.parse(wsMessage)));
	}

	@Override
	public void unregister(WebSocket.Out wsOut) {
		wsMap.remove(wsOut);
	}

	@Override
	public void register(User user, WebSocket.Out wsOut) {
		wsMap.put(wsOut, user);
	}

	public User getUserByWebSocket(WebSocket.Out wsOut) {
		return wsMap.get(wsOut);
	}

	public WebSocket.Out<JsonNode> getWebSocketByUser(User user) {
		Map.Entry<WebSocket.Out<JsonNode>, User> mapEntry = wsMap.entrySet().stream().filter(entry -> entry.getValue().equals(user)).findFirst().get();
		return mapEntry != null ? mapEntry.getKey() : null;
	}
}
