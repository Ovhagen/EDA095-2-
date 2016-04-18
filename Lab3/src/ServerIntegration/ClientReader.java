package ServerIntegration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientReader extends Thread{
	
	public ClientReader(){
	}
	
	public synchronized void readMessage(Socket socket) {
		try {
			InputStream in = socket.getInputStream();
			String message;
			byte[] inBuffer = new byte[4096];
			in.read(inBuffer);

			message = inBuffer.toString();

			System.out.println("Message from a participant: " + message);

		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public synchronized void writeMessage(Socket socket, String message) {
		try {
			OutputStream out = socket.getOutputStream();
			out.write(message.getBytes());
			out.flush();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
