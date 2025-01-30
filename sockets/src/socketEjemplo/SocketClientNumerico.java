package socketEjemplo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class SocketClientNumerico {
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private String address;
	private int port;
	
	public SocketClientNumerico(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void start() throws UnknownHostException, IOException {
		System.out.println("[Client] Lanzando petición al server ...%n");
		socket = new Socket(address, port);
		System.out.printf("[Client] Establecida conexión con %s:%d.%n", address, port);
		is = socket.getInputStream();
		os = socket.getOutputStream();	
	}
	
	public void stop() throws IOException {
		System.out.printf("[Client] Cerrando conexión con %s:%d ...%n", address, port);
		socket.close();
		System.out.printf("[Client] Conexión cerrada con %s:%d ...%n", address, port);
	}
	
	
	public static void main(String[] args) {
		SocketClientNumerico client = new SocketClientNumerico("localhost", 8081);
		
		try {
			client.start();
			int datoAEnviar = new Random().nextInt(255);
			System.out.printf("[Client] Enviando %d a %s: %d.%n", datoAEnviar, client.address, client.port);
			client.os.write(datoAEnviar);
			int datoRecibido = client.is.read();
			System.out.printf("[Client] Recibido %d de %s:%d.%n", datoRecibido, client.address, client.port);
			client.stop();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
