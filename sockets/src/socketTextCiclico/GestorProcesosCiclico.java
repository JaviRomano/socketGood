package socketTextCiclico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class GestorProcesosCiclico extends Thread {
	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;

	public GestorProcesosCiclico(Socket socket) {
		this.socket = socket;
	}

	private void launchProccess() throws IOException {
		pw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	private void closeConnections() throws IOException {
		pw.close();
		br.close();
		socket.close();
		System.out.println("[Server] Conexión cerrada con el cliente");
	}

	private String processingText(String text) {
		if (text == null || text.isEmpty() || !text.contains("#")) {
			return "[ERROR]";
		}		
		
		String[] slices = text.split("#");
		if (slices.length < 2) {
	        return "[ERROR] Comando no válido.";
	    }

		switch (slices[1].toLowerCase()) {
		case "listado números", "listado numeros":
			if (slices.length == 4) {
				try {
					int start = Integer.parseInt(slices[2]);
					int end = Integer.parseInt(slices[3]);
					StringBuilder sb = new StringBuilder();
					for (int i = start; i <= end; i++) {
						sb.append(i).append("|");
					}
					return sb.substring(0, sb.length() - 1);
				} catch (NumberFormatException e) {
					return "[ERROR] Formato incorrecto.";
				}
			}
			break;

		case "número aleatorio", "numero aleatorio":
			if (slices.length == 4) {
				try {
					int min = Integer.parseInt(slices[2]);
					int max = Integer.parseInt(slices[3]);
					Random random = new Random();
					int randomNumber = random.nextInt(max - min + 1) + min;
					return String.valueOf(randomNumber);
				} catch (NumberFormatException e) {
					return "[ERROR] Formato incorrecto.";
				}
			}
			break;

		case "fin":
			return "#Finalizado#";
		default:
			return "[ERROR] Comando no reconocido.";
		}
		return "[ERROR] formato incorrecto";
	}

	@Override
	public void run() {

		try {
			launchProccess();
			String text;
			while ((text = br.readLine()) != null) {
				System.out.println("[Server] Received text: " + text);
				String reply = processingText(text);
				pw.println(reply);
				System.out.println("(Servidor) Respuesta enviada: " + reply);

				if ("#Finalizado#".equals(reply)) {
					break;
				}
			}
			closeConnections();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
