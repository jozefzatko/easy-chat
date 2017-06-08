package sk.zatko.web_sockets.chat.socket_messages;

import sk.zatko.web_sockets.chat.models.ChatMessage;
import sk.zatko.web_sockets.chat.server.ChatEndpoint;

public class NewChatMessageMessage extends ClientSocketMessage {

	private static final String MESSAGE_CODE = "new_message";
	
	private ChatMessage message;
	
	public NewChatMessageMessage() {
		super(MESSAGE_CODE);
	}
	
	public NewChatMessageMessage(ChatMessage message) {
		this();
		this.setMessage(message);
	}
	
	@Override
	public void processMessage(ChatEndpoint chatEndpoint) {
		
		chatEndpoint.addNewMessage(message);
		chatEndpoint.broadcastChatUpdate();
		
	}

	public ChatMessage getMessage() {
		return message;
	}

	public void setMessage(ChatMessage message) {
		this.message = message;
	}

}
