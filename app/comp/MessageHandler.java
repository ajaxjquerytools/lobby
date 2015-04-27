package comp;

public interface MessageHandler {
	public static final String CHANNEL_NAME = "message_channel";

	public void send(String message);
}
