package model;

import play.mvc.WebSocket;
/**
 * Created by volodymyrd on 27.04.15.
 */
public interface WebSocketPool<T> {
	void register(WebSocket.Out<T> out);
	void unregister(WebSocket.Out<String> out);
	void notifyMembers(T message);
}
