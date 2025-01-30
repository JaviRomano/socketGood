package socketTextCiclico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSocketTextCiclico {
	private Socket socket;
	private String address;
	private int port;
	private PrintWriter pw;
	private BufferedReader br;
	private Scanner scanner;

	public ClientSocketTextCiclico(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void start() throws UnknownHostException, IOException {
		socket = new Socket(address, port);
		System.out.printf("[Client-%s:%d] Conexión socket establecida ...%n", address, port);
		openingChannels();
		scanner = new Scanner(System.in);
	}

	public void stop() throws IOException {
		closingChannels();
		socket.close();
		System.out.printf("[Cliente-%s:%d] Conexión socket cerrada ...%n", address, port);
		scanner.close();
	}

	private void openingChannels() throws IOException {
		pw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	private void closingChannels() throws IOException {
		pw.close();
		br.close();
	}

	public void interact() throws IOException {
		Scanner scanner = new Scanner(System.in);
		String message;

		while (true) {
			System.out.print("Mensaje a enviar: ");
			message = scanner.nextLine();
			pw.println(message);
			String reply;
			while ((reply = br.readLine()) != null) {
				System.out.println("Respuesta recibida: " + reply);
				if ("#Finalizado#".equals(reply)) {
					System.out.println("Fin de la conexion.");
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		ClientSocketTextCiclico cliente = new ClientSocketTextCiclico("localhost", 8081);

		try {
			cliente.start();
			cliente.interact();
			cliente.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
