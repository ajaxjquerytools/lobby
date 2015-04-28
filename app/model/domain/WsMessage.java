package model.domain;

public class WsMessage<T> {

	private Event type;
	private StatusResponse status;
	private T eventData;

	public WsMessage(Event type, StatusResponse status, T eventData) {
		this.type = type;
		this.status = status;
		this.eventData = eventData;
		//this.host = play.
	}

	public Event getType() {
		return type;
	}

	public StatusResponse getStatus() {
		return status;
	}

	public T getEventData(){
		return eventData;
	}

}
