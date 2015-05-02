package model.handlers;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;
import java.util.List;

public class HandlerPool implements MessageHandler {

	private static HandlerPool handlerPool;

	public synchronized static HandlerPool getInstance() {
		if (handlerPool == null) {
			handlerPool = new HandlerPool();
		}
		return handlerPool;
	}

	private List<MessageHandler> messageHandlers;

	public HandlerPool() {
		messageHandlers = Arrays.asList(new PublishHandler());
	}

	@Override
	public void send(JsonNode jsonData) {
		messageHandlers.forEach(h -> h.send(jsonData));
	}
}
