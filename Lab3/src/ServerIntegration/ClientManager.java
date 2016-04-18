package ServerIntegration;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {
	private volatile List<ServerThread> participants;
	private volatile int chatSize;
	
	public ClientManager(int chatSize){
		this.chatSize = chatSize;
		participants = new ArrayList<ServerThread>(chatSize);
	}
	
	public synchronized void joinChat(ServerThread st){
		if(chatStatus())
		participants.add(st);
	}
	
	public void exitChat(ServerThread st){
		participants.remove(st);
	}
	
	public synchronized boolean chatStatus(){
		return (participants.size() < chatSize);
	}
	
	public synchronized void postMessage(String message){
		for(ServerThread st : participants){
			st.write(message);
		}
	}

}
