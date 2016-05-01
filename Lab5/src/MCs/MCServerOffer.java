package MCs;

import java.net.*;
import java.io.*;

public class MCServerOffer {

	public static void main(String args[]) {
		try {
			MulticastSocket ms = new MulticastSocket(4099);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			DatagramSocket dgSocket = new DatagramSocket(30000);

			ms.joinGroup(ia);

			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ms.receive(dp);
				String s = new String(dp.getData(), 0, dp.getLength());
				System.out.println("Received: " + s);

				String message = "My identity is: " + InetAddress.getLocalHost().getHostName();
				byte[] sendBuf = message.getBytes();
				DatagramPacket sdp = new DatagramPacket(sendBuf, sendBuf.length, dp.getAddress(), dp.getPort());
				dgSocket.send(sdp);

			}
		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

}
