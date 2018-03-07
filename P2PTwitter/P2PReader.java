import java.util.ArrayList;
import java.util.Scanner;

public class P2PReader implements Runnable {

	public static ArrayList<Peer> peers;
	private P2PClient client;
	private String unikey;

	public P2PReader(P2PClient client, ArrayList<Peer> peers, String unikey) {
		this.client = client;
		P2PReader.peers = new ArrayList<Peer>(peers);
		this.unikey = unikey;
	}

	Scanner scan = new Scanner(System.in);

	String status;

	public void run() {
		while (true) {
			System.out.print("Status: ");
			status = scan.nextLine();
			if (status.length() == 0) {
				System.err.println("Status is empty. Retry.");
			} else if (status.length() > 140) {
				System.err.println("Status is too long, 140 characters max. Retry.");
			} else {
				client.newStatus(status);
				System.out.println("### P2P tweets ###");
				for (Peer peer : peers) {

					// find given unikey and update latest status in peer array list
					if (peer.unikey.equals(unikey)) {
						peer.status(status);
					}
					peer.print(unikey);
				}
				System.out.println("### End tweets ###");
			}
		}
	}
	public static void update(ArrayList<Peer> updated) {
		peers = new ArrayList<Peer>(updated);
	}
}
