package tcp_file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class FileServerProcess extends Thread{
	private Socket socket;
	private DataOutputStream dataOutput;
	private DataInputStream dataInput;
	private String serverDir;
	private BufferedReader bufferReader;
	public FileServerProcess(Socket socket) {
		this.socket = socket;
		try {
			dataInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dataOutput = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	@Override
	public void run() {
		String line;
		try {
			while(true) {
			try {
				line = dataInput.readUTF();
				analysis(line);
				if("QUIT".equalsIgnoreCase(line)) {
					break;
				}
				analysis(line);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		dataInput.close();
		dataOutput.flush();
		dataOutput.close();	
		socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void analysis(String line) throws IOException {
		StringTokenizer st = new StringTokenizer(line);
		String command = st.nextToken();
		String sourceFile, destinyFile;
		switch(command) {
		case "SET_SERVER_DIR":
			serverDir = st.nextToken();
			break;
		case "SEND":
			destinyFile = st.nextToken();
			receiveFile(destinyFile);
			break;
		case "GET":
			sourceFile = st.nextToken();
			sendFile(sourceFile);
			break;
		default:
			break;
		}
		
	}
	public void sendFile(String sourceFile) throws IOException {
		File file = new File(serverDir + File.separator + sourceFile);
		BufferedInputStream bufferInput = new BufferedInputStream(new FileInputStream(file));
		dataOutput.writeLong(file.length());
		byte[]buff = new byte[10*1024];
		int data;
		while((data = dataInput.read(buff))!=-1) {
			dataOutput.write(buff, 0, data);
		}
		dataOutput.flush();
		bufferInput.close();
	}
	public void receiveFile(String destinyFile) throws IOException {
		File file = new File(serverDir +File.separator + destinyFile);
		BufferedOutputStream bufferOutput = new BufferedOutputStream(new FileOutputStream(file));
		long size = dataInput.readLong();
		int byteRead, byteNeedRead;
		byte[]buff = new byte[10*1024];
		long remain = size;
		while(remain>0) {
			byteNeedRead = (buff.length > remain)? (int)remain : buff.length;
			byteRead = dataInput.read(buff,0,byteNeedRead);
			bufferOutput.write(buff,0,byteNeedRead);
			remain-=byteRead;
		}
		bufferOutput.close();
	}
}
