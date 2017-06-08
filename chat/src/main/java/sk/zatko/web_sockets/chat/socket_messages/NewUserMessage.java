package sk.zatko.web_sockets.chat.socket_messages;

import sk.zatko.web_sockets.chat.models.User;
import sk.zatko.web_sockets.chat.server.ChatEndpoint;
public class NewUserMessage extends ClientSocketMessage {

	private static final String MESSAGE_CODE = "new_user";
	
	private User user;

	public NewUserMessage() {
		super(MESSAGE_CODE);
	}
	
	public NewUserMessage(User user) {	
		this();
		this.user = user;
	}
	
	@Override
	public void processMessage(ChatEndpoint chatEndpoint) {
		
		chatEndpoint.allowSignIn(this.user);
        chatEndpoint.addNewUser(this.user);
        chatEndpoint.broadcastUserListUpdate();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
