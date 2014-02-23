package protocolo.TCP;

import entidades.Sala;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class TCPServidor implements CamadaTransporte {
	Socket socket;
	int port;
	ObjectOutputStream socketOut;
	ObjectInputStream socketIn;

	public TCPServidor(Socket socket) throws IOException{
		this.socket = socket;
		socketOut = new ObjectOutputStream(this.socket.getOutputStream());
		socketIn = new ObjectInputStream(this.socket.getInputStream());
	}

	public void conectar(String address) throws IOException {
		System.out.println("isso eh usado?");
	}

	public void desconectar() throws IOException {
		socketOut.close();
		socketIn.close();
		socket.close();
	}


	public void enviar(Object in) throws IOException {
		socketOut.reset();
		socketOut.writeObject(in);
		if (in instanceof Sala){
			System.out.printf("Enviando uma sala com %d Usuários\n", ((Sala)in).getLista());
		}
	}

	public Object receber() throws IOException, ClassNotFoundException {
		Object objetoRecebido = socketIn.readObject();
		return objetoRecebido;
	}

	public Socket getSocket(){
		return socket;
	}
}
