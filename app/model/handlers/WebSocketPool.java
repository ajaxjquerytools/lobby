package model.handlers;

import model.domain.User;
import play.mvc.WebSocket;
/**
 * Created by volodymyrd on 27.04.15.
 */
public interface WebSocketPool<T> {
	void register(User user, WebSocket.Out<T> out);
	void unregister(WebSocket.Out<T> out);
	void notifyMembers(T message);
}
