import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class P2PServer implements Runnable {

	private DatagramSocket socket;
	private ArrayList<Peer> peers;

	public P2PServer(DatagramSocket socket, ArrayList<Peer> peers) {
		this.socket = socket;
		this.peers = new ArrayList<Peer>(peers);
	}

	public void run() {

		try {
			while (socket != null) {
				byte[] buffer = new byte[500];
				DatagramPacket packet = new DatagramPacket(buffer, 500);
				socket.receive(packet);

				Charset charset = Charset.forName("ISO-8859-1");
				CharBuffer buff = charset.decode(ByteBuffer.wrap(buffer));

				String str = buff.toString().substring(0, packet.getLength());

				str.replace("\\:", ":");
				if (str.indexOf(":")<0)
					continue;
				String unikey = str.substring(0, str.indexOf(":"));
				for (Peer peer : peers) {
					if (unikey.equals(peer.unikey)) {
						peer.status(str);
						break;
					}
				}
				P2PReader.update(peers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
