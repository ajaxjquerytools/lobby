package model;

import com.fasterxml.jackson.databind.JsonNode;
public interface MessageHandler {
	public static final String CHANNEL_NAME = "message_channel";

	public void send(JsonNode jsonData);
}
