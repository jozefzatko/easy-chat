package sk.zatko.web_sockets.chat.models;

public class ChatMessage {

	private User user;
	private String text;
	
	public ChatMessage(User user, String text) {
		super();
		this.user = user;
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
