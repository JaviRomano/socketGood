package preRecuExamen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private PrintWriter pw;
	private BufferedReader br;
	private Socket socket;
	private String address;
	private int port;
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public static void main(String[] args) {
		
		Client myClient = new Client("localhost", 4500);		
		try {
			System.out.println("Lanzando petición socket . . .");
			myClient.socket = new Socket("localhost", 4500);
			System.out.println("Petición aceptada");
			// open channels
			myClient.br = new BufferedReader(new InputStreamReader(myClient.socket.getInputStream()));
			myClient.pw = new PrintWriter(myClient.socket.getOutputStream(), true);
			// message exchange
			String dataSent = "Hola";
			myClient.pw.println(dataSent);
			System.out.println("Dato enviado: [[" + dataSent + "]]");
			String dataRead = myClient.br.readLine();
			System.out.println("Dato leido: [[" + dataRead + "]]");
			// close channels
			myClient.socket.close();	
			System.out.println("Conexión cerrada");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}