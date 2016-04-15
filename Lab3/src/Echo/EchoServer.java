package Echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EchoServer extends Server {

	public final static int DEFAULT_PORT = 30000;

	public EchoServer() throws IOException {
		super();
	}

	@Override
	public void respond(Socket socket) {
		InputStream is = null;
		OutputStreamWriter os = null;
		try {
			is = socket.getInputStream();
			byte[] buff = new byte[4096];
			is.read(buff);
			
			String clientMessage = buff.toString();
			System.out.println("Client Message: '" + clientMessage + "' sent by client " + socket.getInetAddress() );

			os = new OutputStreamWriter(socket.getOutputStream());
			byte[] revBuff = new byte[buff.length];
			for (int i = 0; i < buff.length; i++) {
				revBuff[i] = buff[(buff.length-1) - i];
			}
			os.write(revBuff.toString());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Server server = new EchoServer();
			server.run();
		} catch (IOException i) {
			System.out.println("Could not start server.");
		}
	}

}
