package Echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EchoTCP2 extends Server {

	public final static int DEFAULT_PORT = 30000;

	public EchoTCP2() throws IOException {
		super();
	}

	@Override
	public void respond(Socket socket) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = socket.getInputStream();
			byte[] buff = new byte[4096];
			is.read(buff);
			
			String clientMessage = new String(buff);
			System.out.println("Client Message: '" + clientMessage + "' sent by client " + socket.getInetAddress() );

			os = socket.getOutputStream();
			os.write(clientMessage.toUpperCase().getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Server server = new EchoTCP2();
			server.run();
		} catch (IOException i) {
			System.out.println("Could not start server.");
		}
	}

}
