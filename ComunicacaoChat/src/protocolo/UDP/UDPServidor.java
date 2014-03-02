package protocolo.UDP;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import protocolo.TCP.CamadaTransporte;



public class UDPServidor implements CamadaTransporte {

	DatagramSocket socketReceptor;
	DatagramSocket socketTransmissor;
	public InetAddress ipCliente;
	int clientPortEnviar;
	int clientPortReceber;
	int serverPortEnviar;
	int serverPortReceber;
	Receptor receptor;
	Transmissor transmissor;
	private int limiarDescartar;

	public UDPServidor(int clientPortEnviar, InetAddress ipCliente,
			int limiarDescartar) throws IOException {
		// TODO Auto-generated constructor stub
		this.clientPortEnviar = clientPortEnviar;
		this.ipCliente = ipCliente;
		this.limiarDescartar = limiarDescartar;
		this.serverPortReceber = clientPortEnviar;
		this.clientPortReceber = ((2*this.clientPortEnviar)%64000) + 1024;
	}

	@Override
	public void conectar(String address) throws IOException {
		// TODO Auto-generated method stub
		while (true) {
			try {
				socketReceptor = new DatagramSocket(serverPortReceber);
				System.out.println("Porta dedicada para o cliente: "
						+ this.serverPortReceber);
				break;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				this.serverPortReceber++;
			}
		}
		this.serverPortEnviar = ((2*this.serverPortReceber)%64000) + 1024;
		socketTransmissor = new DatagramSocket(serverPortEnviar);
		this.receptor = new Receptor(clientPortEnviar, ipCliente, serverPortReceber,
				socketReceptor, limiarDescartar);
		this.transmissor = new Transmissor(serverPortEnviar, this.ipCliente,
				this.clientPortReceber, socketTransmissor, limiarDescartar);
		receptor.enviarSynAck();
		transmissor.running = true;
	}

	@Override
	public void desconectar() throws IOException {
		// TODO Auto-generated method stub
		receptor.close();
		transmissor.close();
		socketReceptor.close();
		socketTransmissor.close();
	}

	@Override
	public void enviar(Object in) throws IOException {
		// TODO Auto-generated method stub
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

}
