package rmi_client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import nat.rmi.pac.FileImplement;
public class FileClient {
	BufferedReader bufferIn;
	BufferedInputStream inputStream;
	BufferedOutputStream outputStream;
	boolean login;
	public void start() throws RemoteException,NotBoundException, IOException {
		Registry registry = LocateRegistry.getRegistry("127.0.0.1",4444);
		registry.lookup("File");
		FileImplement file = (FileImplement)registry.lookup("File");
		StringTokenizer st;
		bufferIn = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while(true) {
			try {
			line = bufferIn.readLine();
			if("QUIT".equalsIgnoreCase(line))break;
			if(!login) {
				System.out.println("Please enter name");
				st = new StringTokenizer(line);
				String command = st.nextToken();
				switch(command) {
				case "NAME":
					try {
					String name = st.nextToken();
					if(file.findName(name)) {
						System.out.println("Succeed");
					}else {
						System.out.print("Failt to goin");
					}
					}catch(NoSuchElementException e) {
						System.out.println("Syntax error");
					}
					break;
				default:
					break;
				}
			}else {
				System.out.println("Please enter name");
				st = new StringTokenizer(line);
				String command = st.nextToken();
				String sourceFile, destinyFile;
				switch(command) {
				case "SEND":
					try {
						sourceFile = st.nextToken();
						destinyFile = st.nextToken();
						inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
						file.createDest(destinyFile);
						byte[]buff = new byte[10*1024];
						int length;
						while((length = inputStream.read(buff))!=-1) {
							file.writeData(buff, length);
						}
						inputStream.close();
						file.closeDest();
					}catch(NoSuchElementException e) {
						System.out.println("Syntax error");
					}catch(FileNotFoundException e) {
						System.out.print("Can not open the client file");
					}
					break;
				case "GET":
					sourceFile = st.nextToken();
					destinyFile = st.nextToken();
					outputStream = new BufferedOutputStream(new FileOutputStream(destinyFile));
					byte[]data;
					
					while((data = file.readData())!= null) {
						outputStream.write(data, 0, data.length);
					}
					file.closeSource(destinyFile);
					outputStream.close();
					break;
				default:
					break;
				}
			}
			}catch(RemoteException e) {
				System.out.print(e.getMessage());
			}
		}
		bufferIn.close();
	}
	public static void main(String[]args) throws NotBoundException, IOException {
		FileClient client = new FileClient();
		client.start();
	}
}
