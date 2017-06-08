var wsUri = getServerRootUri() + "/chat/chat";
var currentUsername = "";
var chatWebsocket;

function getServerRootUri() {
    
	var host = "localhost";
	var port = "8080";
	
	if (document.location.hostname != "") {
		host = document.location.hostname;
	}
	
	if (document.location.port != "") {
		port = document.location.port
	}
	
	return "ws://" + host + ":" + port;
}

function signIn() {
		
    var retVal = prompt("Please enter your name : ", "", "");
	
    if (retVal != "") {
			
        currentUsername = retVal;
        chatWebsocket = new WebSocket(wsUri, "chat");
		
        var newUserMsg = {
        		
        		user : {
        			name : currentUsername
        		},
        		messageCode : "new_user"
        }
        
        chatWebsocket.onopen = function (evt) {
        	alert("before send");
            chatWebsocket.send(JSON.stringify(newUserMsg));
            alert("send");
        };
        chatWebsocket.onmessage = function (evt) {
            onMessage(evt)
        };
        chatWebsocket.onerror = function (evt) {
            onError(evt)
        };
        chatWebsocket.onclose = function (evt) {
            onClose(evt);
        }
    }
};

function signOut() {
	
	var signOffMessage = {
        		
        message : {
        	user : {
        		name : currentUsername
        	},
        },
        messageCode : "user_sign_off"
    }

	chatWebsocket.send(JSON.stringify(signOffMessage));
    chatWebsocket.close();
	
    currentUsername = "";
    setupForms();
}

function onMessage(evt) {
    
	var message = JSON.parse(evt.data.toString());
    
    if (message.messageCode == "allow_login") {
    	console.log(JSON.stringify(message));
        setupForms();
    }

    if (message.messageCode == "update_chat") {
    	console.log(JSON.stringify(message));
    	addMessageToChat(message.lastMessage);
    }
    
    if (message.messageCode == "update_users") {
    	console.log(JSON.stringify(message));
        writeUserlist(message.users);
    }
}

function onError(evt) {
    alert("Error: " + evt.data);
}

function onClose(evt) {
    currentUsername = "";
    setupForms();
}

function isSignedIn() {
    return (currentUsername != "");
}

function signInOut() {
    
	if (isSignedIn()) {
    	
    	signOut();
    	
    } else {
        signIn();
    }
}

function sendMessage() {
	
    var chatString = newMessageText.value;
	
    if (chatString.length > 0) {
    	
    	var newMessage = {
        		
        		message : {
        			user : {
        				name : currentUsername
        			},
        			text : chatString
        		},
        		messageCode : "new_message"
        }

        chatWebsocket.send(JSON.stringify(newMessage));
        sendTextInput.value = "";
    }
}

function setupForms() {

    if (isSignedIn()) {
    	
		btnSignIn.innerHTML = "Sign out";
        btnSend.disabled = false;
        sendTextInput.disabled = false;
        
    } else {
    	
		btnSignIn.innerHTML = "Sign in";
        btnSend.disabled = true;
        sendTextInput.disabled = true;
		
        chat_content.innerHTML = "";
        users_content.innerHTML = "";
    }

}

function close() {
	
	chatWebsocket.close();
}

function addMessageToChat(lastMessage)  {
	
	chat_content.innerHTML = chat_content.innerHTML
			+ "<div class=\"message\"><div class=\"message_sender\">"
			+ lastMessage.user.name
			+ "</div><div class=\"message_separator\">:</div><div class=\"message_text\">"
			+ lastMessage.text
			+ "</div></div>";
}

function writeUserlist(users) {
 	
	users_content.innerHTML = "";
	
	for (var i = 0; i < users.length; i++) {
		
		users_content.innerHTML = users_content.innerHTML + "<div class=\"user\"> "+ users[i].name + "</div>"
    }
}


window.addEventListener("load", setupForms, false);
window.addEventListener("beforeunload", close, false);


