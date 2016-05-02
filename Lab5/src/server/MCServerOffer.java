package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCServerOffer implements Runnable{
	
	private DatagramSocket dgSocket;
	
	public MCServerOffer(DatagramSocket dgSocket){
		this.dgSocket = dgSocket;
	}

	public void run() {
		try {
			MulticastSocket ms = new MulticastSocket(4099);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			ms.joinGroup(ia);

			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ms.receive(dp);
				
				String s = new String(dp.getData(), 0, dp.getLength(), "UTF-8");
				System.out.println("Received: '" + s + "' on multicast server with port " + dgSocket.getLocalPort());
				
				if(s.equals("DISCOVER")){
					String message = "My identity is: " + InetAddress.getLocalHost().getHostName() + " and I offer time service";
					byte[] sendBuf = message.getBytes();
					DatagramPacket sdp = new DatagramPacket(sendBuf, sendBuf.length, dp.getAddress(), dp.getPort());
					dgSocket.send(sdp);
				}

			}
		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

}
