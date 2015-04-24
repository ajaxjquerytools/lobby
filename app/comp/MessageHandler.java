package comp;

import play.api.libs.json.Json;
/**
 * Created by volodymyrd on 24.04.15.
 */
public interface MessageHandler {
	public void send(String message);
}
