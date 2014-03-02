package protocolo.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Transmissor {
	int portaRemetente;
	int portaDestinatario;
	InetAddress ipDestinatario;
	DatagramSocket socket;
	int nextSeqNum;
	int timeOut;
	int base;
	int windowSize;
	int packetNum;
	Timer timer;
	int limiarDescartar;
	ModuloEspecial moduloUDP;
	ConcurrentHashMap<Integer, Pacote> buffer;
	boolean goingtoClose;
	boolean running;
	Object stateMutex;
	AckReceiver ackReceiver;
	AtomicBoolean enviou;

	public Transmissor(int portaRemetente, InetAddress ipDestinatario,
			int portaDestinatario, DatagramSocket socket, int limiarDescartar) {
		this.portaRemetente = portaRemetente;
		this.ipDestinatario = ipDestinatario;
		this.portaDestinatario = portaDestinatario;
		this.socket = socket;
		this.limiarDescartar = limiarDescartar;
		this.moduloUDP = new ModuloEspecial(limiarDescartar);
		this.buffer = new ConcurrentHashMap<Integer, Pacote>();
		this.timeOut = 150;
		this.windowSize = 1;
		this.base = 1;
		this.nextSeqNum = 1;
		this.packetNum = 0;
		this.timer = new Timer(true);
		this.goingtoClose = false;
		this.ackReceiver = new AckReceiver();
		enviou = new AtomicBoolean(true);
		ackReceiver.start();
	}

	public void enviarFin() throws IOException {
		Pacote fin = new Pacote(portaRemetente, portaDestinatario,
				nextSeqNum - 1, packetNum, false, false, true, new byte[1]);
		byte[] sendData = fin.toByteArray();
		DatagramPacket pacoteUDP = new DatagramPacket(sendData,
				sendData.length, ipDestinatario, portaDestinatario);
		socket.send(pacoteUDP);
		System.out.println("Enviando FIN");
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		goingtoClose = true;
		enviarFin();
		socket.close();
		timer.cancel();
		System.out.println("Fechando o modulo transmissor");
	}

	private synchronized void send(Pacote pacote) {
		// TODO Auto-generated method stub
		try {
			byte[] pacoteByte = pacote.toByteArray();
			DatagramPacket pacoteUDP = new DatagramPacket(pacoteByte,
					pacoteByte.length, ipDestinatario, portaDestinatario);
			System.out.println("porta do transmissor "+socket.getLocalPort());
			socket.send(pacoteUDP);
			timer.schedule(new PacketTimeout(pacote.numeroSequencia), timeOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void sendData(Object object) {
		this.enviou.set(false);
		if (nextSeqNum < (base + windowSize)) {
			byte[] objetoByte;
			try {
				objetoByte = Serializador.serialize(object);
				System.out.println("Porta remetente: " + portaRemetente);
				Pacote pacote = new Pacote(portaRemetente, portaDestinatario,
						nextSeqNum, 0, objetoByte);
				buffer.put(pacote.numeroSequencia, pacote);
				System.out.println("Porta remetente no pacote " + pacote.getPortaOrigem());
				System.out.println("+++++++ "+pacote.getNumeroSequencia() + " porta " + portaDestinatario);
				send(pacote);
				nextSeqNum++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private class AckReceiver extends Thread {

		public void run() {
			while (true) {
				byte[] receivedAck = new byte[Pacote.TAM_MAX_PACOTE];
				DatagramPacket pacoteAck = new DatagramPacket(receivedAck,
						receivedAck.length);
				try {
					socket.receive(pacoteAck);
					Pacote recebido = new Pacote(pacoteAck.getData(),
							pacoteAck.getLength());
					//if (recebido.isAck()) {
						if (!moduloUDP.descartarPacote()) {
							base = recebido.numeroAck + 1;
							if (base == nextSeqNum) {
								System.out.println("Recebeu ack novo");
								enviou.set(true);
								synchronized(buffer){
									buffer.remove(recebido.numeroAck);
								}
							} else {
								//timer.schedule(new PacketTimeout(recebido.numeroSequencia), timeOut);
							}
						}
					//}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class PacketTimeout extends TimerTask {

		int numeroSequencia;

		public PacketTimeout(int numeroSequencia) {
			this.numeroSequencia = numeroSequencia;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int seqNum = base;
			while (seqNum < nextSeqNum) {
				Pacote pacote = buffer.get(numeroSequencia);
				if(pacote != null){
					send(pacote);
					//timer.schedule(new PacketTimeout(numeroSequencia), timeOut);
				}
				seqNum++;
			}
		}

	}
}
