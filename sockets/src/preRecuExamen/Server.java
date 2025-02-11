package preRecuExamen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
//	private PrintWriter pw;
//	private BufferedReader br;
//	private Socket socket;
	private int port;
	private ServerSocket serverSocket;
	
	public Server(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}
	
	public static void main(String[] args) {
		Server myServer;

		try {			
			myServer = new Server(4500);
			Socket mySocket = null;
			// start server
			while(true) {
				System.out.println("Esperando peticion . . .");
			mySocket = myServer.serverSocket.accept();
			System.out.println("Petición aceptada.");
			new Manager(mySocket).start();	
			}					
			// Open channels
//			myServer.br = new BufferedReader(new InputStreamReader(myServer.socket.getInputStream()));
//			myServer.pw = new PrintWriter(myServer.socket.getOutputStream(), true);
			// message exchange
//			String dataRead = myServer.br.readLine();
//			System.out.println("Dato leido: [[" + dataRead + "]]");
//			System.out.println("Pausa de 10seg . . .");
//			Thread.sleep(10000);
//			String dataSent = "*]] [[" + dataRead;
//			myServer.pw.println(dataSent);
//			System.out.println("Dato enviado: [[" + dataSent + "]]");
			// close channels
//			myServer.socket.close();
//			System.out.println("Conexión finalizada.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}