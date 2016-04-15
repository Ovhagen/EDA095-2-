package Echo;

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
	private Socket socket;

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
			socket = new Socket(host, port);
			while (!socket.isClosed()) {
				Scanner scan = new Scanner(System.in);
				String message = scan.nextLine();

				if (message == "quit") {
					socket.close();
				} else {
					writeMessage(socket, message.getBytes());
					readResponse(socket);
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

	private static void readResponse(Socket socket) {
		try {
			InputStream in = socket.getInputStream();
			ByteArrayOutputStream ba = null;
			String serverResponse;
			byte[] inBuffer = new byte[4096];
			int bytes;
			while ((bytes = in.read(inBuffer)) != -1) {
				ba.write(inBuffer, 0, bytes);
			}

			serverResponse = ba.toString();

			System.out.println("Response from server: " + serverResponse);
			ba.flush();
			ba.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	private static void writeMessage(Socket socket, byte[] message) {
		try {
			OutputStream out = socket.getOutputStream();
			byte[] outBuffer = new byte[4096];
			for (int i = message.length; i <= 0; i--) {
				out.write(outBuffer, 0, message.length);
			}
			out.flush();
			out.close();
			socket.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
