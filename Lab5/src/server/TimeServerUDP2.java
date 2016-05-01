package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class TimeServerUDP2 extends Thread {
	private static final int PORT = 30000;
	private static final int BUFFER_MAX = 1024 * 4;
	private DatagramSocket dgSocket;
	private DatagramPacket dgPacketReceive;
	private DatagramPacket dgPacketSend;

	public TimeServerUDP2(DatagramSocket dgSocket) {
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
				
				String command = new String(dgPacketReceive.getData(),"UTF-8");

				System.out.println("Command: " + command + "Length: "
						+ dgPacketReceive.getLength() + "Address: " + dgPacketReceive.getAddress().getHostAddress()
						+ ":" + dgPacketReceive.getPort());
				if(command.toLowerCase() == "datetime" )
					bufferSend = new Date().toString().getBytes();
					else
						bufferSend = "Unknown command given.".getBytes();
				
				dgPacketSend = new DatagramPacket(bufferSend, bufferSend.length, dgPacketReceive.getAddress(),
						dgPacketReceive.getPort());

				dgSocket.send(dgPacketSend);
				
				System.out.println("Sent " + new String(bufferSend));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
