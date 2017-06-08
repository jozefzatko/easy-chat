package sk.zatko.web_sockets.chat.socket_messages;

import sk.zatko.web_sockets.chat.server.ChatEndpoint;

public abstract class ClientSocketMessage extends SocketMessage {
	
	public ClientSocketMessage(String messageCode) {
		super(messageCode);
	}
	
	public abstract void processMessage(ChatEndpoint chatEndpoint);
}
