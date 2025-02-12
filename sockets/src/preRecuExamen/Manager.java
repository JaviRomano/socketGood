package preRecuExamen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Manager extends Thread {
	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;

	public Manager(Socket socket) throws IOException {
		this.socket = socket;
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pw = new PrintWriter(socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		
		try {
			String dataRead;
			while ((dataRead = br.readLine()) != null) {

				System.out.println("Dato leido: " + dataRead);
				System.out.println("Pausa 2 seg . . .");
				Thread.sleep(2000);
				String dataSent = "<> " + receivingData(dataRead) + " <>";
				pw.println("Dato enviado: " + dataSent);
				if (dataSent.equals("#Finalizado#"))
					break;
			}
			socket.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String receivingData(String data) {
		if ((data.startsWith("#Listado números")) || (data.startsWith("#Listado numeros"))) {
			String[] slices = data.split("#");
			if (slices.length == 4) {
				int start = Integer.parseInt(slices[2]);
				int end = Integer.parseInt(slices[3]);
				if (start < end) {
					StringBuilder sb = new StringBuilder();
					for (int i = start; i <= end; i++)
						sb.append(i).append("|");
					return sb.substring(0, sb.length() - 1);
				}
			}

		} else if ((data.startsWith("#Número aleatorio") || (data.startsWith("#Numero aleatorio")))) {
			String[] slices = data.split("#");
			if (slices.length == 4) {
				int min = Integer.parseInt(slices[2]);
				int max = Integer.parseInt(slices[3]);
				if (min < max) {
					return String.valueOf(new Random().nextInt(max - min + 1) + min);
				}
			}
		} else if ((data.startsWith("#Fin")) || (data.startsWith("#fin"))) {
			return "#Finalizado#";
		}
		return "#Error";
	}
}