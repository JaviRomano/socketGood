package preRecuExamen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
				Client myClient;		
		try (Socket socket = new Socket("localhost", 4500);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {			
			System.out.println("Lanzando petición socket . . .");
			String input;			
			while (( input = userInput.readLine()) != null) {
				pw.println(input);
				String response = br.readLine();
				System.out.println("Mensaje recibido: " + response);
				if (response.equals("#Finalizado#")) {
					System.out.println("Fin de la conexión.");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
