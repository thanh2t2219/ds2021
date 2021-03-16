package p2p_communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import javax.json.*;
public class Peer {
	public static void main(String []args) throws Exception{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please enter username and port for this peer:");
			String[]setUpValues = bufferReader.readLine().split(" ");
			Server server = new Server(setUpValues[1]);
			server.start();
			new Peer().updateListenToPeers(bufferReader, setUpValues[0], server);
		
	}
	public void updateListenToPeers(BufferedReader bufferReader, String username, Server server)throws Exception{
		System.out.println("enter (space separate)hostname:port#");
		System.out.println("peer to receive message from (s to skip): ");
		String input = bufferReader.readLine();
		String[]inputValues = input.split(" ");
		if(!input.equals("s")) for(int i = 0; i < inputValues.length; i++) {
			String[]address = inputValues[i].split(":");
			Socket socket = null;
			try {
				socket = new Socket(address[0],Integer.valueOf(address[1]));
				new PeerThread(socket).start();
			}catch(Exception e) {
				if (socket!=null)socket.close();
				else System.out.println("Invalid input, skipping this step");
			}
		}
		communicate(bufferReader,username,server);
	}
	public void communicate(BufferedReader bufferedReader,String username,Server server) {
		try {
		System.out.println("You can now communicate (e to exit, c to change)");
		boolean flag = true;
		while(flag) {
				String message = bufferedReader.readLine();
				if(message.equals("e")) {
					flag = false;
					break;
				}else if(message.equals("c")) {
					updateListenToPeers(bufferedReader,username,server);
				}else {
					StringWriter stringWriter = new StringWriter();
					Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
							.add("username", username)
							.add("message", message)
							.build());
					server.sendMessage(stringWriter.toString());
				}
		}
		System.exit(0);
		}catch(Exception e) {
			
		}
	};
}
