import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class P2PClient implements Runnable {
	private DatagramSocket socket;
	private DatagramPacket packet;
	private String unikey;
	private String latestStatus = "";
	private ArrayList<Peer> peers;
	public P2PClient(DatagramSocket socket, String clientPort, String unikey, ArrayList<Peer> peers) {
		this.socket = socket;
		this.unikey = unikey;
		Integer.parseInt(clientPort);
		this.peers = new ArrayList<Peer>(peers);
	}

	public void run() {
		if (latestStatus.length() > 0) {
			newStatus(latestStatus);
		}
	}

	public void sendPacket(String status) {
		try {
			byte[] buffer = status.getBytes("ISO-8859-1");
			for (Peer peer : peers) {
				packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(peer.ip), peer.port);
				socket.send(packet);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void newStatus(String status) {
		latestStatus = status;
		sendPacket(unikey + ":" + status.replace(":", "\\:"));
	}
}
