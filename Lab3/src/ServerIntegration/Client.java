package ServerIntegration;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;

public class Client {

	private static int port = 30000;

	public static void main(String[] args) throws Exception {
		System.out.println("Press enter for localhost or specify another host.");
		String hostname = "localhost";
		if (args.length > 0) {
			hostname = args[0];
		}
		new Client().startSession(hostname);
	}

	public void startSession(String host) {
		Socket socket = null;
		try {
			System.out.println("Session initiated");
			socket = new Socket(host, port);
			while (!socket.isClosed()) {
				Scanner scan = new Scanner(System.in);
				String message = scan.nextLine();

				if (message == "quit") {
					socket.close();
				} else {
					ClientReader ct = new ClientReader();
					ct.start();
					writeMessage(socket, message);
					readMessage(socket);
				}
			}

		} catch (UnknownHostException e) {
			System.out.println(e);
			System.exit(1);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

}
