package sk.zatko.web_sockets.chat.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.websocket.EndpointConfig;

public class ChatModel {
	
	private static final String CHAT_MODEL_KEY = "chat_model";
	
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();
	private List<User> users = new ArrayList<User>();
    
    public static ChatModel getInstance(EndpointConfig ec) {
    	
        if (!ec.getUserProperties().containsKey(CHAT_MODEL_KEY)) {
            ec.getUserProperties().put(CHAT_MODEL_KEY, new ChatModel()); 
        }
        return (ChatModel) ec.getUserProperties().get(CHAT_MODEL_KEY);
    }
	
    public ChatMessage getLastMessage() {
        return messages.get(messages.size() -1);
    }
    
    public void removeUser(User user) {
    	
    	for (Iterator<User> iterator = this.users.iterator(); iterator.hasNext();) {
		    
			User u = iterator.next();
		    
		    if (u.equals(user)) {
				iterator.remove();
			}
		}
    }

	public List<ChatMessage> getMessages() {
		return messages;
	}

	public List<User> getUsers() {
		return users;
	}
}
