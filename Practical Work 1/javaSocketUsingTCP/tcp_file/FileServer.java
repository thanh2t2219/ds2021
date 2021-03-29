package tcp_file;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
	private ServerSocket serverSocket;
	private static final int PORT = 12345;
	public FileServer() {
		
	}
	public void start() {
		try {
			serverSocket = new ServerSocket(PORT);
			while(true) {
				Socket socket = serverSocket.accept();
				FileServerProcess fileServerProcess = new FileServerProcess(socket);
				fileServerProcess.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[]args) {
		FileServer fileServer = new FileServer();
		fileServer.start();
	}
}
