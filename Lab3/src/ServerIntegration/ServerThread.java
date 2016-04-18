package ServerIntegration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	public final static int DEFAULT_PORT = 30000;
	private Socket socket;
	InputStream is;
	OutputStream os;
	private ClientManager cm;

	public ServerThread(Socket socket, ClientManager cm) {
		this.socket = socket;
		this.cm = cm;
		try{
			is = socket.getInputStream();
			os = socket.getOutputStream();
		}catch(IOException i){
			i.printStackTrace();
		}
	}

	public void run() {
		try {
			while (!socket.isClosed()) {
				System.out.println("IN");
				
				byte[] buff = new byte[4096];
				is.read(buff);
				String clientMessage = new String(buff);
				
				if(clientMessage.startsWith("M:")){
					cm.postMessage(clientMessage);
				}else if(clientMessage.startsWith("E:")){
					write(clientMessage);
				}
			}
			cm.exitChat(this);
			System.out.println("Socket closed.");
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void write(String message){
		try{
			os.write(message.substring(2).getBytes());
			os.flush();
			System.out.println("Client Message: '" + message + "' sent by client " + socket.getInetAddress());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
