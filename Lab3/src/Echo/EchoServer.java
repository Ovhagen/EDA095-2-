package Echo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class EchoServer extends Server{
	
	 public final static int DEFAULT_PORT = 30000;

	  public EchoServer() throws IOException {
	    super(); 
	  }
	  
	  @Override
	  public void respond(Socket socket) {
		  InputStream is = null;
		  OutputStream os = null;
		  try{
			     is = socket.getInputStream();
					int read = 0;
					byte[] buff = new byte[4096];
					read = is.read(buff);
					
					String clientMessage = buff.toString();
					Date date = new Date();
					System.out.println("Client Message: '" + clientMessage +  "' sent " + date.getYear() + ":" + date.getDate() + ":" + date.getTime());
					
					os = socket.getOutputStream();
					byte[] revBuff = new byte[buff.length];
					for(int i = 0; i < buff.length; i--){
						revBuff[i] = buff[buff.length - i];
					}
					os.write(revBuff, 0, read);
		  }catch (IOException e) {
				e.printStackTrace();
			}
			
	  }

	  public static void main(String[] args) {
	    try{	
	    	Server server = new EchoServer();
	    	server.run();
	    }catch(IOException i){
	    	System.out.println("Could not start server.");
	    }
	  }

}
