package p2p_communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;


public class Server extends Thread{
	private ServerSocket serverSocket;
	private Set<ServerThread>serverThreads = new HashSet<>();
	public Server(String portNumb) throws IOException {
		serverSocket = new ServerSocket(Integer.valueOf(portNumb));
	}
	public void run(){
		try {
			while(true) {
				ServerThread serverThread = new ServerThread(serverSocket.accept(),this);
				serverThreads.add(serverThread);
				serverThread.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void sendMessage(String message) {
		try {
			serverThreads.forEach(t->t.getPrinterWriter().println(message));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public Set<ServerThread>getServerThread(){return serverThreads;}
}
