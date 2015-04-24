package comp;

/**
 * Created by volodymyrd on 24.04.15.
 */
public class JedisPubHandler implements MessageHandler {

	@Override
	public void send(String message) {
		System.out.println("JEDIS:"+message);
	}
}
