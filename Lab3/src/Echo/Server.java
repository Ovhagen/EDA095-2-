package Echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.net.ssl.SSLSocket;

public abstract class Server {

	private final static int DEFAULT_PORT = 30000;
	private ServerSocket serverSocket;
	private ArrayList<InetAddress> connectedClients;
	private InputStream input;
	private volatile boolean isShutDown = false;

	public Server() throws IOException {
		serverSocket = new ServerSocket(DEFAULT_PORT);
		connectedClients = new ArrayList<InetAddress>();
	}

	public void run() {
		while (true) {
			if (isShutDown)
				return;
			try (Socket socket = serverSocket.accept()) {
				InetAddress clientAddress = socket.getInetAddress();
				connectedClients.add(clientAddress);
				System.out.println("Connection initiated with Client: " + clientAddress);
				while(socket.getInputStream().read() != -1){
					this.respond(socket);
				}
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
	}

	public abstract void respond(Socket socket);

	public int connectedClients() {
		return connectedClients.size();
	}

	public void shutDown() {
		this.isShutDown = true;
	}
}