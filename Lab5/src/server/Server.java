package server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
	private static int port;
	private DatagramSocket dgSocket;
	
	public Server(int port){
		this.port = port;
		try {
			dgSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void init(){
		TimeServerUDP2 time = new TimeServerUDP2(dgSocket);
		MCServerOffer multiCast = new MCServerOffer(dgSocket);
		multiCast.run();	
		time.run();
	}

	
	public static void main(String[] args) {
		ClientHandler ch = new ClientHandler();
		Server server1 = new Server(30000);
		Server server2 = new Server(30001);	
		server1.init();
		server2.init();
	}

}
