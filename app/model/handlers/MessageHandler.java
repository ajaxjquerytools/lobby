package model.handlers;

import com.fasterxml.jackson.databind.JsonNode;

public interface MessageHandler {
	public static final String MESSAGE_CHANNEL = "message_channel";

	public void send(JsonNode jsonData);
}
