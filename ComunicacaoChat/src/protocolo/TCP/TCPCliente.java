package protocolo.TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class TCPCliente implements CamadaTransporte {
	Socket socket;
	ObjectOutputStream socketOut;
	ObjectInputStream socketIn;
	int port;
	
	public TCPCliente(int port){
		this.port = port;
	}

	public void conectar(String address) throws IOException {
		this.socket = new Socket(address, 5435);
		socketOut = new ObjectOutputStream(this.socket.getOutputStream());
		socketIn = new ObjectInputStream(this.socket.getInputStream());
	}

	public void desconectar() throws IOException {
		socket.close();
	}

	
	public void enviar(Object in) throws IOException {
       socketOut.reset();
		socketOut.writeObject(in);
	}

	public Object receber() throws IOException, ClassNotFoundException {
		Object objetoRecebido = socketIn.readObject();
		return objetoRecebido;
	}
	

}
