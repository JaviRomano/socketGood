package socketsUltimaDefensa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

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
				System.out.println("Pausa 1 seg . . .");
				Thread.sleep(1000);
				String dataSent = receivingData(dataRead);
				pw.println(dataSent);
				if (dataSent.equals("#Desconectado#")) {
					socket.close();
					System.out.println("Conexión finalizada.");
					break;
				}
			}		
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String receivingData(String data) {
		String[] slices = data.split("#");
			
			if (data.startsWith("#Máximo")) {
				int first = Integer.parseInt(slices[2]);
				int last = Integer.parseInt(slices[3]);
				if (first == last) {
					return "#iguales#";
				}
				return "#Máximo=#" + Math.max(first, last) + "#";
			} else if (data.startsWith("#Mínimo")) {
				int first = Integer.parseInt(slices[2]);
				int last = Integer.parseInt(slices[3]);
				if (first == last) {
					return "#iguales#";
				}
				return "#Mínimo=#" + Math.min(first, last) + "#";
			} else if (data.startsWith("#Palíndromo")) {
				String text = data.replace("#Palíndromo#", "").replace("#", "");
				String invertedText = new StringBuilder(text).reverse().toString();
				return invertedText.equalsIgnoreCase(text)
						? "#Palíndromo#gracias por preguntar#" + text + "#si es palíndromo#"
						: "#Palíndromo#gracias por preguntar#" + text + "#no es palíndromo#";		
			} else if (data.startsWith("#Cuántos")) {
				String[] numbers = Arrays.copyOfRange(slices, 2, slices.length);
				String splitArray = String.join(",", numbers);				
				return "#En [" + splitArray + "] hay " + numbers.length+ " elementos#";				
			} else if (data.startsWith("#Cuánto")) {				
				String[] numbers = Arrays.copyOfRange(slices, 2, slices.length);
				String splitArray = String.join(",", numbers);
				int amount = 0;				
				try {
				for (int i = 2; i < slices.length; i++) {
					amount += Integer.parseInt(slices[i]);
				}
				return  "#Suma de [" + splitArray + "] es " + amount + "#";
				} catch (NumberFormatException e) {
			        return "#Error#Formato incorrecto en los datos#";
			    }
			} else if (data.startsWith("#Desconectar")) {
				return "#Desconectado#";
			}
		return "#Instrucción no válida#";
	}
}