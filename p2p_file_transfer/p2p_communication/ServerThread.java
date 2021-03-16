package p2p_communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThread extends Thread {
	private Server server;
	private Socket socket;
	private PrintWriter printWriter;
	public ServerThread(Socket socket,Server server) {
		this.server = server;
		this.socket = socket;
	}
	public void run() {
		try {
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.printWriter = new PrintWriter(socket.getOutputStream(),true);
			while(true)server.sendMessage(bufferReader.readLine());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			server.getServerThread().remove(this);
		}
	}
	public PrintWriter getPrinterWriter() {
		return printWriter;
	}
}
