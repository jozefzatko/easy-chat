package sk.zatko.web_sockets.chat.socket_messages;

import java.util.List;

import sk.zatko.web_sockets.chat.models.User;

public class UserListUpdateMessage extends ServerSocketMessage {

	private static final String MESSAGE_CODE = "update_users";
	
	private List<User> users;
	
	public UserListUpdateMessage() {
		super(MESSAGE_CODE);
	}
	
	public UserListUpdateMessage(List<User> users) {
		this();
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
