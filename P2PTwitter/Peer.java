public class Peer {

	public String ip;
	public String pseudo;
	public String unikey;
	public int port;
	public String lastStatus = "";

	public Peer(String ip, String pseudo, String unikey, String port) {
		this.ip = ip;
		this.pseudo = pseudo;
		this.unikey = unikey;
		this.port = Integer.parseInt(port);
	}

	public void print(String clientKey) {
		if (unikey.equals(clientKey)) {
			if (lastStatus.length() == 0) {
				System.out.println("# [" + pseudo + " (myself): not yet initialized]");
			} else {
				System.out.println("# " + pseudo + " (myself): " + lastStatus);
			}
		} else {
			if (lastStatus.length() == 0) {
				System.out.println("# [" + pseudo + " (" + unikey + "): not yet initialized]");
			} else {
				System.out.println("# " + pseudo + " (" + unikey + "): " + lastStatus);
			}
		}
	}

	public void status(String newStatus) {
		this.lastStatus = newStatus;
	}
}
