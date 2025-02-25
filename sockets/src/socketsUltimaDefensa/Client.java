package socketsUltimaDefensa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		String server = "localhost";
        int port = 6000;
		try (Socket socket = new Socket(server, port);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.println("Conexión iniciada . . .");
			System.out.print("Escriba un mensaje: ");
			String input;
			while ((input = userInput.readLine()) != null) {				 
				pw.println(input);
				String response = br.readLine();
				System.out.println("Mensaje recibido: " + response);
				System.out.print("Escriba un mensaje: ");
				if (response.equals("#Desconectado#")) {
					System.out.println("Fin de la conexión.");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
