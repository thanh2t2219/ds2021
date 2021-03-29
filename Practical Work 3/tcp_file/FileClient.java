package tcp_file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class FileClient {
	private Socket socket;
	private DataInputStream dataIS;
	private DataOutputStream dataOS;
	private BufferedReader bufferReader;
	public static final String HOST = "127.0.0.1";
	public static final int PORT = 12345;
	private String clientDir;
	public FileClient(){
		clientDir = "C:\\source";
	}
	public void request() {
		try {
			this.socket = new Socket(HOST,PORT);
			dataIS = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dataOS = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			bufferReader = new BufferedReader(new InputStreamReader(System.in));
			String line;
			while(true) {
				line = bufferReader.readLine();
				analysis(line);
				if("QUIT".equalsIgnoreCase(line)) {
					break;
				}
			}
			dataIS.close();
			dataOS.flush();
			dataOS.close();
			bufferReader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void analysis(String line) throws IOException {
		StringTokenizer st = new StringTokenizer(line);
		String command = st.nextToken();
		String sourceFile, destinyFile;
		switch(command) {
		case "SET_SERVER_DIR":
			dataOS.writeUTF(line);;
			break;
		case "SET_CLIENT_DIR":
			clientDir = st.nextToken();
			break;
		case "SEND":
			sourceFile = st.nextToken();
			destinyFile = st.nextToken();
			dataOS.writeUTF(command + " "+destinyFile);			
			sendFile(sourceFile);
			break;
		case "GET":
			sourceFile = st.nextToken();
			destinyFile = st.nextToken();
			dataOS.writeUTF(command +" "+sourceFile);
			dataOS.flush();
			receiveFile(destinyFile);
			break;
		case "QUIT":
			dataOS.writeUTF(line);
			break;
		default:
			break;
		}
		dataOS.flush();
		
	}
	public void sendFile(String sf) throws IOException {
		File file = new File(clientDir+File.separator+sf);
		BufferedInputStream bufferInput = new BufferedInputStream(new FileInputStream(file));
		dataOS.writeLong(file.length());
		byte[]buff = new byte[10 * 1024];
		int data;
		while((data = bufferInput.read(buff))!= -1) {
			dataOS.write(buff, 0, data);
			
		}
		dataOS.flush();
		bufferInput.close();
	}
	public void receiveFile(String df) throws IOException {
		File file = new File(clientDir+File.separator+df);
		BufferedOutputStream bufferOutput = new BufferedOutputStream(new FileOutputStream(file));
		long size = dataIS.readLong();
		int byteRead, byteNeedRead;
		byte[]buff = new byte[10*1024];
		long remain = size;
		while(remain>0) {
			byteNeedRead = (buff.length > remain)? (int)remain : buff.length;
			byteRead = dataIS.read(buff,0,byteNeedRead);
			bufferOutput.write(buff,0,byteNeedRead);
			remain-=byteRead;
		}
		dataOS.flush();
		bufferOutput.close();
	}
	public static void main(String[]args) {
		FileClient fileClient = new FileClient();
		fileClient.request();
	}
}
