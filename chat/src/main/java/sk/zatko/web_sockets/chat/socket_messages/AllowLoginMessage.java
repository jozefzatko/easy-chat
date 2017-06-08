package sk.zatko.web_sockets.chat.socket_messages;

public class AllowLoginMessage extends ServerSocketMessage {

	private static final String MESSAGE_CODE = "allow_login";

	public AllowLoginMessage() {
		super(MESSAGE_CODE);
	}
	
	
}
