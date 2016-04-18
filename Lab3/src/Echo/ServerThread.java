package Echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread extends Thread {
	public final static int DEFAULT_PORT = 30000;
	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		InputStream is = null;
		OutputStream os = null;
		try {
			while (!socket.isClosed()) {
				System.out.println("IN");
				is = socket.getInputStream();
				byte[] buff = new byte[4096];
				is.read(buff);

				String clientMessage = new String(buff);
				System.out.println("Client Message: '" + clientMessage + "' sent by client " + socket.getInetAddress());

				os = socket.getOutputStream();
				os.write(clientMessage.toUpperCase().getBytes());
				os.flush();
			}
			System.out.println("Socket closed");
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
