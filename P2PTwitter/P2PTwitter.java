import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Properties;

public class P2PTwitter {

	public static ArrayList<Peer> peers = new ArrayList<Peer>();

	public static void main(String[] args) {

		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String unikey = args[0];
		String clientPort = "";

		// load properties
		Properties participants = new Properties();
		try {
			participants.load(new FileInputStream("participants.properties"));
			String[] userID = participants.getProperty("participants").split(",");
			for (int i = 0; i < userID.length; i++) {
				Peer temp = new Peer(participants.getProperty(userID[i] + ".ip"),
						participants.getProperty(userID[i] + ".pseudo"),
						participants.getProperty(userID[i] + ".unikey"), participants.getProperty(userID[i] + ".port"));
				peers.add(temp);
				if (unikey.equals(participants.getProperty(userID[i] + ".unikey"))) {
					clientPort = participants.getProperty(userID[i] + ".port");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// check if unikey was not found in properties file
		if (clientPort.length() == 0) {
			System.out.println("unikey not found in properties file");
			return;
		}

		// make and start threads
		P2PServer server = new P2PServer(socket,peers);
		P2PClient client = new P2PClient(socket, clientPort, unikey,peers);
		P2PReader reader = new P2PReader(client, peers, unikey);
		new Thread(server).start();
		new Thread(client).start();
		new Thread(reader).start();

	}
}