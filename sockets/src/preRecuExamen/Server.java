package preRecuExamen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private int port;
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(4500);	
			while (true) {				
				System.out.println("Esperando peticion . . .");
				Socket mySocket = serverSocket.accept();
				System.out.println("Petición aceptada.");
				new Manager(mySocket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
