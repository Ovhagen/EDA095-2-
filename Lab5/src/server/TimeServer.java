package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class TimeServer implements Runnable {
	private static final int PORT = 30000;
	private static final int BUFFER_MAX = 1024 * 4;
	private DatagramSocket dgSocket;
	private DatagramPacket dgPacketReceive;
	private DatagramPacket dgPacketSend;

	public TimeServer(DatagramSocket dgSocket) {
		this.dgSocket = dgSocket;
	}

	public void run() {
		byte[] bufferReceive = new byte[BUFFER_MAX];
		byte[] bufferSend = new byte[BUFFER_MAX];

		try {

			while (true) {
				bufferReceive = new byte[BUFFER_MAX];
				dgPacketReceive = new DatagramPacket(bufferReceive, bufferReceive.length);
				dgSocket.receive(dgPacketReceive);
				
				String command = new String(dgPacketReceive.getData(), 0, dgPacketReceive.getLength(), "UTF-8");
				
				//String command = new String(dgPacketReceive.getData(),"UTF-8");

				System.out.println("Received command: '" + command + "' on timeserver with port " + dgSocket.getLocalPort() + " from " + dgPacketReceive.getAddress().toString());
				
				if(command.toLowerCase().equals("datetime"))
					bufferSend = new Date().toString().getBytes();
					else
						bufferSend = "Unknown command given.".getBytes();
				
				dgPacketSend = new DatagramPacket(bufferSend, bufferSend.length, dgPacketReceive.getAddress(),
						dgPacketReceive.getPort());

				dgSocket.send(dgPacketSend);
				
				System.out.println("Sending: " + new String(bufferSend));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
