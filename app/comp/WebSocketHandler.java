package comp;

public class WebSocketHandler implements MessageHandler {

	@Override
	public void send(String  message) {
		System.out.println("WS:"+message);
	}
}
