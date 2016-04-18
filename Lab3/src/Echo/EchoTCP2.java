package Echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**Abstract server heritage removed and EchoTCP2 
 * now represents the server.*/
public class EchoTCP2 {

	private final static int DEFAULT_PORT = 30000;
	private ServerSocket serverSocket;
	private ArrayList<InetAddress> connectedClients;
	private volatile boolean isShutDown = false;

	public EchoTCP2() throws IOException {
		serverSocket = new ServerSocket(DEFAULT_PORT);
		connectedClients = new ArrayList<InetAddress>();
		run();
	}

	public void run() {
		if (isShutDown)
			return;
		Socket socket;
		try {
			while ((socket = serverSocket.accept()) != null) {
				InetAddress clientAddress = socket.getInetAddress();
				connectedClients.add(clientAddress);
				System.out.println("Connection initiated with Client: " + clientAddress);

				ServerThread thread = new ServerThread(socket);
				thread.start();
			}
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public int connectedClients() {
		return connectedClients.size();
	}

	public void shutDown() {
		this.isShutDown = true;
	}

	public static void main(String[] args) {
		try {
			new EchoTCP2();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}