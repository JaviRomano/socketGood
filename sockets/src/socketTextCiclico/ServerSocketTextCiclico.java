package socketTextCiclico;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTextCiclico {
	private Socket socket;
	private ServerSocket server;

	public ServerSocketTextCiclico(int port) throws IOException {
		server = new ServerSocket(port);
		System.out.println("[Server] Iniciando en el puerto: " + port);

		while (true) {
			socket = server.accept();
			System.out.printf("[Server] Cliente conectado desde: %d", port);
			new GestorProcesosCiclico(socket).start();
		}
	}

	public static void main(String[] args) {
		try {
			new ServerSocketTextCiclico(8081);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}