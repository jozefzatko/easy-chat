package sk.zatko.web_sockets.chat.tranformation;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import sk.zatko.web_sockets.chat.socket_messages.ServerSocketMessage;


public class ChatMessageEncoder implements Encoder.Text<ServerSocketMessage> {

	@Override
	public void init(EndpointConfig ec) {
		
	}

	@Override
	public String encode(ServerSocketMessage serverSocketMessage) throws EncodeException {
		return new Gson().toJson(serverSocketMessage);
	}

	@Override
	public void destroy() {
	
	}
	
}
