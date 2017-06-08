package sk.zatko.web_sockets.chat.socket_messages;

public abstract class ServerSocketMessage extends SocketMessage {
	
	public ServerSocketMessage(String messageCode) {
		super(messageCode);
	}
}
