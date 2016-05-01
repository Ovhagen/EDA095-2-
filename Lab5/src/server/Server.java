package server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
	private static int port;
	private DatagramSocket dgSocket;
	private ClientHandler ch;
	
	public Server(int port, ClientHandler ch){
		this.port = port;
		this.ch = ch;
		try {
			dgSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void init(){
		TimeServerUDP2 time = new TimeServerUDP2(dgSocket);
		MCServerOffer multiCast = new MCServerOffer(ch, dgSocket);
		multiCast.run();	
		time.run();
	}

	
	public static void main(String[] args) {
		ClientHandler ch = new ClientHandler();
		Server server1 = new Server(30000, ch);
		Server server2 = new Server(30001, ch);	
		server1.init();
		server2.init();
	}

}
