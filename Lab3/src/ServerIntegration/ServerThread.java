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
				byte[] buff = new byte[4096];
				is.read(buff);
				String clientMessage = new String(buff);
				
				if(clientMessage.startsWith("M:")){
					cm.postMessage(clientMessage);
				}else if(clientMessage.startsWith("E:")){
					write(clientMessage);
				}else if(clientMessage.startsWith("q")){
					write("Session closed.");
					socket.close();
				}
				else{
					write("Unknown command.");
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
			os.write(("Client" + socket.getPort() + ":" + message.substring(2)).getBytes());
			os.flush();
			System.out.println("Client" + socket.getPort() + ": '" + message);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
