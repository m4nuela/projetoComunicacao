package protocolo.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class Receptor {

	int portaRemetente;
	int portaDestinatario;
	InetAddress ipRemetente;
	DatagramSocket socket;
	int expectedSeqNum;
	int limiarDescartar;
	ModuloEspecial moduloUDP;
	public Vector<Object> objetos;
	private Worker worker;
	AtomicBoolean recebeu;
	int lastAck;

	public Receptor(int portaRemetente, InetAddress ipRemetente,
			int portaDestinatario, DatagramSocket socket, int limiarDescartar)
	throws IOException {
		// TODO Auto-generated constructor stub
		this.recebeu = new AtomicBoolean();
		this.expectedSeqNum = 1;
		this.portaRemetente = portaRemetente;
		this.ipRemetente = ipRemetente;
		this.portaDestinatario = portaDestinatario;
		this.socket = socket;
		this.limiarDescartar = limiarDescartar;
		moduloUDP = new ModuloEspecial(limiarDescartar);
		this.worker = new Worker();
		this.worker.start();
		lastAck = 0;
		this.objetos = new Vector<Object>();
	}

	public void enviarSynAck() throws IOException {
		// TODO Auto-generated method stub
		Pacote synAck = new Pacote(portaDestinatario, portaRemetente, 0, 0,
				true, true, false, new byte[1]);
		byte[] sendData = synAck.toByteArray();
		DatagramPacket pacoteUDP = new DatagramPacket(sendData,
				sendData.length, ipRemetente, portaRemetente);
		socket.send(pacoteUDP);
		System.out.println("Enviando SYNACK para o cliente " + portaRemetente);
	}

	public void close() {
		// TODO Auto-generated method stub
		System.out.println("Fechando modulo receptor");
		socket.close();
	}

	private synchronized void enviarAck(int numeroSequencia) throws IOException {
		// TODO Auto-generated method stub
		Pacote ack = new Pacote(portaDestinatario, portaRemetente, 0,
				numeroSequencia, false, true, false, new byte[1]);
		byte[] sendData = ack.toByteArray();
		DatagramPacket pacoteUDP = new DatagramPacket(sendData,
				sendData.length, ipRemetente, portaRemetente);
		socket.send(pacoteUDP);
		System.out.println("Ack enviado para o pacote " + numeroSequencia);
	}


	private class Worker extends Thread{


		public void run() {
			while (true) {
				byte[] receivedData = new byte[Pacote.TAM_MAX_PACOTE];
				DatagramPacket pacote = new DatagramPacket(receivedData, receivedData.length);
				try {
					socket.receive(pacote);
					Pacote recebido = new Pacote(pacote.getData(), pacote.getLength());
					if(!moduloUDP.descartarPacote()){
						System.out.println(">>>>> "+recebido.getNumeroSequencia());
						if(recebido.getNumeroSequencia() == expectedSeqNum){
							Object objetoRecebido = Serializador.deserialize(recebido.getDados());
							objetos.add(objetoRecebido);
							//while(!recebeu.get());
							System.out.println("Enviando ack novo");
							lastAck = recebido.numeroSequencia;
							enviarAck(lastAck);
							expectedSeqNum++;
							recebeu.set(true);
							System.out.println("****** " + recebeu.get());
						}
						else {
							System.out.println("Enviando ack antigo");
							enviarAck(lastAck);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
