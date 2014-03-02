package server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.*;

import protocolo.TCP.CamadaTransporte;
import protocolo.TCP.TCPServidor;

public class ClientThread implements Runnable {

	WriteServidor write;
	ReadServidor read;
	int indice;
	Server servidor;
	Lock lock;
	Condition cv;
	NegociosServidor negocios;
	CamadaTransporte comunicacao;

	public ClientThread (Server servidor, int indice, Lock lock, Condition cv, Socket socket) throws IOException {
		this.lock = lock;
		this.cv = cv;
		this.servidor = servidor;
		this.indice = indice;
		this.comunicacao = new TCPServidor(socket);
		try {
			//enviando a lista inicial de salas
			this.negocios.enviarListaSalas(servidor.salas);
		} catch (IOException e){
			e.printStackTrace();
		}

		write = new WriteServidor(servidor, indice, lock, cv, indice, negocios);
		read = new ReadServidor(servidor, indice, negocios, comunicacao);
	}
	
	
	public ClientThread(Server servidor2, int i, Lock lock2, Condition cv2,	CamadaTransporte comunicacao) throws IOException, SocketException {
		//criando a thread do cliente
		this.lock = lock2;
		this.cv = cv2;
		this.servidor = servidor2;
		this.indice = i;
		this.comunicacao = comunicacao;
		this.negocios = new NegociosServidor(this.comunicacao);
		try {
			//enviando a lista inical de salas
			this.negocios.enviarListaSalas(servidor.salas);
		} catch (IOException e) {
			e.printStackTrace();
		}
		write = new WriteServidor(servidor, indice, lock, cv, indice, negocios);
		read = new ReadServidor(servidor, indice, negocios, this.comunicacao);
	}
	
	public void run() {
		read.start();
		write.start();
		//Startando as threads de leitura e escrita
	}

}
