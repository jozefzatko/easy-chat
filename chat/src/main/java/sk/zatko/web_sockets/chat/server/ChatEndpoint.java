package sk.zatko.web_sockets.chat.server;

import java.io.IOException;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import sk.zatko.web_sockets.chat.models.ChatMessage;
import sk.zatko.web_sockets.chat.models.ChatModel;
import sk.zatko.web_sockets.chat.models.User;
import sk.zatko.web_sockets.chat.socket_messages.AllowLoginMessage;
import sk.zatko.web_sockets.chat.socket_messages.ClientSocketMessage;
import sk.zatko.web_sockets.chat.socket_messages.UpdateChatMessage;
import sk.zatko.web_sockets.chat.socket_messages.UserListUpdateMessage;

@ServerEndpoint(value = "/chat",
				subprotocols={"chat"},
				decoders = { sk.zatko.web_sockets.chat.tranformation.ChatMessageDecoder.class },
				encoders = { sk.zatko.web_sockets.chat.tranformation.ChatMessageEncoder.class })
public class ChatEndpoint {

	private static final Logger LOG = Logger.getLogger(ChatEndpoint.class);
	
	private static final String CURRENT_USER_MODEL_KEY = "username";
    
    private Session session;
    private EndpointConfig endpointConfig;
    
    @OnOpen
    public void startChatChannel(EndpointConfig endpointConfig, Session session) {
        
    	this.session = session;
    	this.endpointConfig = endpointConfig;
    	
    	ChatModel.getInstance(endpointConfig);
    }

    @OnMessage
    public void handleClientSocketMessage(Session session, ClientSocketMessage message) {
    	
    	message.processMessage(this);
    }
    
    @OnError
    public void myError(Throwable t) {
    	LOG.info("Error: " + t.getMessage());
        LOG.info(t);
    }
    
    @OnClose
    public void endChatChannel() {
        signOffUser();
    }
    
    public void signOffUser() {
    	
    	addNewMessage(new ChatMessage(getCurrentUser(), "Just signed off"));
		broadcastChatUpdate();
		
		removeUser(getCurrentUser());
    }
    
    public User getCurrentUser() {
        return (User) session.getUserProperties().get(CURRENT_USER_MODEL_KEY);
    }
    
    public List<User> getAllUsers() {
    	return getChatModel().getUsers();
    }
    
    public void addNewUser(User user) {
        session.getUserProperties().put(CURRENT_USER_MODEL_KEY, user);
        getChatModel().getUsers().add(user);
    }
    
    public void addNewMessage(ChatMessage message) {
        getChatModel().getMessages().add(message);
    }

    public void allowSignIn(User user) {
    	
    	AllowLoginMessage allowLoginMessage = new AllowLoginMessage();
		
        try {
            this.session.getBasicRemote().sendObject(allowLoginMessage);
        
            LOG.info("sending: " + new Gson().toJson(allowLoginMessage));
            
        } catch (IOException | EncodeException e) {
        	LOG.info("Error signing " + user.getName() + " into chat");
        }
    }
    
    public void broadcastUserListUpdate() {
    	
    	UserListUpdateMessage ulum = new UserListUpdateMessage(getAllUsers());

    	for (Session nextSession : session.getOpenSessions()) {
            
    		try {
    			nextSession.getBasicRemote().sendObject(ulum);
    			LOG.info("sending: " + new Gson().toJson(ulum));
            } catch (IOException | EncodeException e) {
            	LOG.info("Error updating a client : " + e.getMessage());
            }
        }
    }

	public void removeUser(User user) {

    	try {
    		
    		this.session.getUserProperties().remove(CURRENT_USER_MODEL_KEY);
        	getChatModel().removeUser(user);
    		
            this.broadcastUserListUpdate();
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "User logged off"));
            
        } catch (IOException e) {
        	LOG.info("Error removing user");
        }
    }

    
    public void broadcastChatUpdate() {
    	
    	UpdateChatMessage uchm = new UpdateChatMessage(getChatModel().getLastMessage());
        
    	for (Session nextSession : session.getOpenSessions()) {
    		
    		if (nextSession.isOpen()) {
    			
    			try {
                    nextSession.getBasicRemote().sendObject(uchm);
                    LOG.info("sending: " + new Gson().toJson(uchm));
                } catch (IOException | EncodeException ex) {
                	LOG.info("Error updating a client : " + ex.getMessage());
                }  
    		} 
        }
    }
    
    public ChatModel getChatModel() {
    	
    	return ChatModel.getInstance(this.endpointConfig);
    }

}
