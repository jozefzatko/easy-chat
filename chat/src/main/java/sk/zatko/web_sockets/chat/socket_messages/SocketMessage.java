package sk.zatko.web_sockets.chat.socket_messages;

public abstract class SocketMessage {
	
	private String messageCode;
	
	public SocketMessage(String messageCode) {
		super();
		this.messageCode = messageCode;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
}
