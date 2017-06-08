package sk.zatko.web_sockets.chat.tranformation;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

import sk.zatko.web_sockets.chat.socket_messages.ClientSocketMessage;
import sk.zatko.web_sockets.chat.socket_messages.NewChatMessageMessage;
import sk.zatko.web_sockets.chat.socket_messages.NewUserMessage;
import sk.zatko.web_sockets.chat.socket_messages.UserSignOffMessage;

public class ChatMessageDecoder implements Decoder.Text<ClientSocketMessage> {

	private static final Logger LOG = Logger.getLogger(ChatMessageDecoder.class);
	
	private static final List<ClientSocketMessage> MESSAGE_TYPES;
	
	static {
		
		MESSAGE_TYPES = new ArrayList<ClientSocketMessage>();
		
		MESSAGE_TYPES.add(new NewUserMessage());
		MESSAGE_TYPES.add(new NewChatMessageMessage());
		MESSAGE_TYPES.add(new UserSignOffMessage());
		
	}
	
	@Override
	public void init(EndpointConfig config) {
		
	}
	
	@Override
	public ClientSocketMessage decode(String rawMessage) throws DecodeException {
		
		String actualMessageCode = new JSONObject(rawMessage).getString("messageCode");

		for(ClientSocketMessage messageType : MESSAGE_TYPES) {
			
			String expectedMessageCode = messageType.getMessageCode();
			
			if (actualMessageCode.equals(expectedMessageCode)) {
					
				return new Gson().fromJson(rawMessage, messageType.getClass());
			}

		}
		
		return null;
	}

	@Override
	public boolean willDecode(String rawMessage) {

		LOG.info("received: " + rawMessage);
		
		String actualMessageCode = new JSONObject(rawMessage).getString("messageCode");

		for(ClientSocketMessage messageType : MESSAGE_TYPES) {
			
			String expectedMessageCode = messageType.getMessageCode();
				
			if (actualMessageCode.equals(expectedMessageCode)) {
					
				return true;
			}

		}
		
		return false;
	}
	
	@Override
	public void destroy() {
	
	}

}
