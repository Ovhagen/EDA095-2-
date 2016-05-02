package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUDP2 {
	private static final int BUFFER_MAX = 1024 * 4;
	private static int port;

	public static void main(String[] args) {
		if (args.length < 2 || args.length > 2)
			throw new IllegalArgumentException("java SendUDP2 port command");

		port = Integer.parseInt(args[0]);
		String command = args[1];

		DatagramSocket dgSocket;
		DatagramPacket dgPacketSend;
		DatagramPacket dgPacketReceive;

		try {
			DatagramPacket dgPacketDiscover;

			if ((dgPacketDiscover = discoverServer()) != null) {
				dgSocket = new DatagramSocket();
				int serverPort = dgPacketDiscover.getPort();
				InetAddress serverAddress = dgPacketDiscover.getAddress();

				String s = new String(command);
				System.out.println("Sending command: " + s + " to " + serverAddress);

				byte[] buf = s.getBytes();
				dgPacketSend = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
				dgSocket.send(dgPacketSend);

				byte[] rbuf = new byte[65536];
				dgPacketReceive = new DatagramPacket(rbuf, rbuf.length);
				dgSocket.receive(dgPacketReceive);
				String rec = new String(dgPacketReceive.getData());
				
				System.out.println("Received: " + rec + " from " + dgPacketDiscover.getAddress().getHostName());

			} else {
				throw new IOException("Failed to discover a server");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static DatagramPacket discoverServer() {
		try {
			DatagramSocket broadCast = new DatagramSocket();
			broadCast.setBroadcast(true);
			
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			
			byte[] buf = "DISCOVER".getBytes();
			DatagramPacket dgPacketDiscover = new DatagramPacket(buf, buf.length, ia, port);
			broadCast.send(dgPacketDiscover);
			
			byte[] rbuf = new byte[65536];
			DatagramPacket dgPacketReceived = new DatagramPacket(rbuf, rbuf.length);
			
			broadCast.setSoTimeout(2000);
			broadCast.receive(dgPacketReceived);
			System.out.println(dgPacketReceived.getData().toString());
			return dgPacketReceived;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
