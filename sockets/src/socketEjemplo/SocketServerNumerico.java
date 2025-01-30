package socketEjemplo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerNumerico {
	private ServerSocket serverSocket;
	private Socket socket;
	private int port;
	private InputStream is;
	private OutputStream os;

	public SocketServerNumerico(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(port);
	}

	public void start() throws IOException {
		socket = serverSocket.accept();
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}

	public void stop() throws IOException {
		socket.close();
	}

	public static void main(String[] args) throws IOException {
		SocketServerNumerico server = new SocketServerNumerico(8081);		
		try {
			server.start();
			int datoLeido = server.is.read();
			System.out.printf("[Server] Dato recibido: %d %n", datoLeido);
			int datoADevolver = ++datoLeido;
			server.os.write(datoADevolver);
			System.out.printf("[Server] Dato enviado: %d %n", datoLeido);
			server.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
