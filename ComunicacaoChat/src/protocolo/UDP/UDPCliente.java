package protocolo.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

import protocolo.TCP.CamadaTransporte;




public class UDPCliente implements CamadaTransporte {
	
	DatagramSocket socketReceptor;
	DatagramSocket socketTransmissor;
	InetAddress ipServer;
	int clientPortEnviar;
	int clientPortReceber;
	int serverPortReceptor;
	int serverListenerPort;
	int serverPortTransmissor;
	Receptor receptor;
	Transmissor transmissor;
	ModuloEspecial moduloUDP;
	int limiar;
	
	public UDPCliente(int serverListenerPort, int limiarDescartar) throws SocketException{
		this.serverListenerPort = serverListenerPort;
		this.limiar = limiarDescartar;
		Random randomGenerator = new Random();
		int portNumber = randomGenerator.nextInt(64000);
		portNumber+=1024;
		this.clientPortEnviar = portNumber;
		System.out.println("Porta do cliente " + this.clientPortEnviar);
		while(true){
			try {
				this.socketTransmissor = new DatagramSocket(clientPortEnviar);
				break;
			} catch (SocketException e) {
				clientPortEnviar++;
			}
		}
		this.clientPortReceber = ((2*this.clientPortEnviar)%64000) + 1024;
		this.socketReceptor = new DatagramSocket(clientPortReceber);
		moduloUDP = new ModuloEspecial(limiarDescartar);
		this.serverPortReceptor = 0;
		this.serverPortTransmissor = 0;
		
	}

	@Override
	public void conectar(String address) throws IOException {
		this.ipServer = InetAddress.getByName(address);
		enviarSyn(serverListenerPort);
		DatagramPacket udpPacket = new DatagramPacket(new byte[Pacote.TAM_MAX_PACOTE], Pacote.TAM_MAX_PACOTE);
		socketTransmissor.receive(udpPacket);
		Pacote packet = new Pacote(udpPacket.getData(), udpPacket.getLength());
		System.out.println("TO VIVOOOO");
		System.out.println(packet.isAck()+" "+packet.isSyn());
		if(packet.isAck() && packet.isSyn()){
			System.out.println("Recebeu SYNACK do servidor"); 
			this.serverPortReceptor = udpPacket.getPort();
			this.serverPortTransmissor = ((2*this.serverPortReceptor)%64000) + 1024;
			transmissor = new Transmissor(clientPortEnviar, ipServer, serverPortReceptor, socketTransmissor, limiar);
			receptor = new Receptor(serverPortTransmissor, ipServer, clientPortReceber, socketReceptor, limiar);
			System.out.println("Conectado");
		}
	}

	@Override
	public void desconectar() throws IOException {
		// TODO Auto-generated method stub
		receptor.close();
		transmissor.close();
		socketTransmissor.close();
		socketReceptor.close();
	}

	@Override
	public void enviar(Object in) throws IOException {
		while(!transmissor.enviou.get());
		transmissor.sendData(in);
		transmissor.enviou.set(false);
	}

	@Override
	public Object receber() throws IOException, ClassNotFoundException {
		Object objetoRecebido = null;
		System.out.println("ALOOOOOO TEM ALGUEM AIIIII");
		while(receptor.objetos.isEmpty());
		receptor.recebeu.set(false);
		System.out.println("Vai recuperar o objeto");
		objetoRecebido = receptor.objetos.remove(0);
		return objetoRecebido;
	}
	
	public void enviarSyn(int port) throws IOException {
		Pacote syn = new Pacote(clientPortEnviar, port, 0, 0, true,
				false, false, new byte[1]);
		byte[] sendData = syn.toByteArray();
		DatagramPacket pacoteUDP = new DatagramPacket(sendData,
				sendData.length, ipServer, port);
		socketTransmissor.send(pacoteUDP);
		System.out.println("Enviando SYN para o servidor na porta " + port);
	}
}
