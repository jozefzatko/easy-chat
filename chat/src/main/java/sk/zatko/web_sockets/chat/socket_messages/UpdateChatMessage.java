package sk.zatko.web_sockets.chat.socket_messages;

import sk.zatko.web_sockets.chat.models.ChatMessage;

public class UpdateChatMessage extends ServerSocketMessage {

	private static final String MESSAGE_CODE = "update_chat";
	
	private ChatMessage lastMessage;

	public UpdateChatMessage() {
		super(MESSAGE_CODE);
	}
	
	public UpdateChatMessage(ChatMessage lastMessage) {
		this();
		this.setLastMessage(lastMessage);
	}

	public ChatMessage getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(ChatMessage lastMessage) {
		this.lastMessage = lastMessage;
	}

}
