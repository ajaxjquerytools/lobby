package model.sub;

import java.util.concurrent.TimeUnit;

import model.handlers.MessageHandler;
import play.libs.Akka;
import redis.clients.jedis.JedisPubSub;
import scala.concurrent.duration.Duration;
import utils.JedisUtil;
/**
 * Created by volodymyrd on 29.04.15.
 */
public class Subscriber {

	public static void subscribeToMessageChannel(JedisPubSub jedisPubSub) {
		subscribeToChanel(jedisPubSub, MessageHandler.MESSAGE_CHANNEL);
	}

	public static void subscribeToChanel(JedisPubSub jedisPubSub, String chanel) {
		Akka.system().scheduler().scheduleOnce(Duration.create(10, TimeUnit.MILLISECONDS), () -> {
			JedisUtil.doJedisCall(j -> j.subscribe(jedisPubSub, chanel));
		}, Akka.system().dispatcher());
	}
}
