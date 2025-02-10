package preRecuExamen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private PrintWriter pw;
	private BufferedReader br;
	private Socket socket;
	private int port;
	private ServerSocket serverSocket;
	
	public Server(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Server myServer;

		try {
			// start server
			myServer = new Server(4500);
			System.out.println("Esperando peticion . . .");
			myServer.socket = myServer.serverSocket.accept();
			System.out.println("Petición aceptada.");
			// Open channels
			myServer.br = new BufferedReader(new InputStreamReader(myServer.socket.getInputStream()));
			myServer.pw = new PrintWriter(myServer.socket.getOutputStream(), true);
			// message exchange
			String dataRead = myServer.br.readLine();
			System.out.println("Dato leido: [[" + dataRead + "]]");
			System.out.println("Pausa de 10seg . . .");
			Thread.sleep(10000); // por qué lo ha podido hacer javi sin throws
			String dataSent = "* " + dataRead;
			myServer.pw.println(dataSent);
			System.out.println("Dato enviado: [[" + dataSent + "]]");
			// close channels
			myServer.socket.close();
			System.out.println("Conexión finalizada.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
