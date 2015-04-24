package comp;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by volodymyrd on 24.04.15.
 */
public class HandlerHolder implements MessageHandler {

	private static HandlerHolder handlerHolder;

	public static HandlerHolder getInstance() {
		if (handlerHolder == null) {
			handlerHolder = new HandlerHolder();
		}
		return handlerHolder;
	}

	private List<MessageHandler> messageHandlers;

	public HandlerHolder() {
		messageHandlers = new ArrayList<MessageHandler>() {
			{
				add(new JedisPubHandler());
				add(new WebSocketHandler());
			}
		};
	}

	@Override
	public void send(String message) {
		messageHandlers.forEach(h -> h.send(message));
	}
}
