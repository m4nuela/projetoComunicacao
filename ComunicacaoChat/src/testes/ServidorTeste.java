package testes;

import java.net.SocketException;

import server.Server;

public class ServidorTeste {

	private static int porta = 5435;
	private static int maxClients = 4000;
	
	public static void main(String[] args) {
		Server servidor;
		
		try {
			servidor = new Server (porta, maxClients, 20);
			Thread t = new Thread (servidor);
			t.start();
		} catch (SocketException e){
			e.printStackTrace();
		}
	}
}
