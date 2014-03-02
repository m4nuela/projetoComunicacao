package Server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.locks.*;
import protocolo.TCP.CamadaTransporte;
import protocolo.UDP.Pacote;
import protocolo.UDP.UDPServidor;
import entidades.*;

public class Server implements Runnable {

	CamadaTransporte comunicacao;
	ArrayList<Sala> salas;
	int port;
	int maxClients;
	ClientThread [] threads;
	boolean [] mudanca;
	Condition cv;
	Lock lock;
	//MetodosBP bp;
	DatagramSocket serverSocket;
	private InetAddress ipCliente;
	private int portaEnvio;
	int limiar;


	public Server (int port, int maxClients, int limiar) throws SocketException {
		this.lock = new ReentrantLock();
		this.cv = lock.newCondition();
		this.port = port;
		this.salas = new ArrayList<Sala>();
		this.threads = new ClientThread[maxClients];
		serverSocket = new DatagramSocket(port);
		//bp = new MetodosBP();
		mudanca = new boolean [maxClients];
		this.limiar = limiar;
	}

	@Override
	public void run() {
		boolean entrou = false;
		while(true){
			try{
				this.conectar();
				comunicacao = new UDPServidor(portaEnvio, ipCliente, limiar);
				comunicacao.conectar("localhost");
				//System.out.println("conectou");
				for (int i = 0; i < maxClients; i++) {
					if(threads[i] == null){
						threads[i] = new ClientThread(this, i, lock, cv, comunicacao);
						Thread t = new Thread(threads[i]);
						t.start();
						entrou = true;
						break;
					}
				}

				if(!entrou){
					//System.out.println("server cheio");
					comunicacao.enviar("Server cheio. Por favor tente mais tarde.");
					comunicacao.desconectar();
				}

			} catch (IOException e){
				e.printStackTrace();
			}

		}

	}
	
	private void conectar(){
		//Esperando clientes;
		byte [] receiveData = new byte [Pacote.TAM_MAX_PACOTE];
		DatagramPacket pacote = new DatagramPacket(receiveData, receiveData.length);
		
		try {
			serverSocket.receive(pacote);
			//Recebeu o handshake do cliente;
			Pacote pacoteSYN = new Pacote(receiveData,receiveData.length);
			if(pacoteSYN.isSyn()){
				//Conctado com alguém para se comunicar
				ipCliente = pacote.getAddress(); 
				//Recebeu o pedido de conexão do ip
				portaEnvio = pacote.getPort();	
				// Porta do cliente
			} else {
				this.conectar();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}

}
