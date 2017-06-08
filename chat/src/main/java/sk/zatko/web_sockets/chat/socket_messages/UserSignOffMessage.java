package sk.zatko.web_sockets.chat.socket_messages;

import sk.zatko.web_sockets.chat.models.User;
import sk.zatko.web_sockets.chat.server.ChatEndpoint;

public class UserSignOffMessage extends ClientSocketMessage {

	private static final String MESSAGE_CODE = "user_sign_off";
	
	private User user;

	public UserSignOffMessage() {
		super(MESSAGE_CODE);
	}
	
	public UserSignOffMessage(User user) {	
		this();
		this.user = user;
	}
	
	@Override
	public void processMessage(ChatEndpoint chatEndpoint) {
		
		chatEndpoint.signOffUser();
		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}

