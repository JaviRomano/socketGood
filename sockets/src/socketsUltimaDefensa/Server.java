package socketsUltimaDefensa;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private int port;
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(this.port);
	}

	public static void main(String[] args) {
		System.out.println("Esperando peticion . . .");
		try {
			Server server = new Server(6000);
            System.out.println("Conexion socket iniciada en el puerto[" + 6000 + "]");
			while (true) {				
				Socket mySocket = server.serverSocket.accept();
				System.out.println("Petición aceptada.");
				new Manager(mySocket).start();
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}