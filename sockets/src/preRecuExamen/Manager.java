package preRecuExamen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
			String dataRead = br.readLine();
			System.out.println("Dato leido: [[ " + dataRead + " ]]");
			System.out.println("Pausa de 10seg . . .");
			Thread.sleep(10000);
			String dataSent = "* ]] + [[ " + dataRead;
			pw.println(dataSent);
			System.out.println("Dato enviado: " + dataSent);
			socket.close();
			System.out.println("");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
