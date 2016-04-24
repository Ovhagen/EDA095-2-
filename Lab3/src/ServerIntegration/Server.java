package ServerIntegration;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private final static int DEFAULT_PORT = 30000;
	private ServerSocket serverSocket;
	private ClientManager cm;

	public Server(ClientManager cm) throws IOException {
		serverSocket = new ServerSocket(DEFAULT_PORT);
		this.cm = cm;
		run();
	}

	public void run() {
		Socket socket;
		try {
			while ((socket = serverSocket.accept()) != null) {
				if (cm.chatStatus()) {
					int client = socket.getPort();
					System.out.println("Connection initiated with Client: " + client);

					ServerThread thread = new ServerThread(socket, cm);
					cm.joinChat(thread);
					thread.start();
				} else {
					OutputStream os = socket.getOutputStream();
					String status = "The chat is currently full. Please try again later.";
					os.write(status.getBytes());
					os.flush();
				}
			}
		} catch (IOException i) {
			i.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		try {
			new Server(new ClientManager(10));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}