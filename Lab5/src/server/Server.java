package server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
	private static int port;
	private DatagramSocket dgSocket;

	public Server(int port) {
		this.port = port;
		try {
			dgSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		TimeServer time = new TimeServer(dgSocket);
		MCServerOffer multiCast = new MCServerOffer(dgSocket);

		Thread t1 = new Thread(multiCast);
		Thread t2 = new Thread(time);
		
		t1.start();
		t2.start();
	}

	public static void main(String[] args) {
		Server server1 = new Server(30000);
		Server server2 = new Server(30001);
		server1.init();
		server2.init();
	}

}
