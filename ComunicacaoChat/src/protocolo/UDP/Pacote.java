package protocolo.UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Pacote implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5075376949829086082L;
	public static final int TAM_MAX_PACOTE = 1492;
	public static final int TAM_MAX_DADOS = 1476;
	
	int portaOrigem;
	int portaDestino;
	int numeroSequencia;
	int numeroAck;
	
	boolean syn;
	boolean ack;
	boolean fin;
	byte[] dados;
	
	public Pacote(int portaOrigem, int portaDestino, int numeroSequencia,
			int numeroAck, boolean syn, boolean ack, boolean fin,
			byte[] dados) {
		this.portaOrigem = portaOrigem;
		this.portaDestino = portaDestino;
		this.numeroSequencia = numeroSequencia;
		this.numeroAck = numeroAck;
		this.syn = syn;
		this.ack = ack;
		this.fin = fin;
		this.dados = dados;
	}

	//Construtor soh com dados (para pacotes normais, que possuem todas as flags false
	public Pacote(int portaOrigem, int portaDestino, int numeroSequencia, int numeroAck, byte[] dados){
		this.portaOrigem = portaOrigem;
		this.portaDestino = portaDestino;
		this.numeroSequencia = numeroSequencia;
		this.numeroAck = numeroAck;
		this.syn = false;
		this.ack = false;
		this.fin = false;
		this.dados = dados;
	}
	
	public Pacote(byte[] pacoteByte, int tamanhoPacote) throws IOException{
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(pacoteByte));
		
		this.portaOrigem = dis.readShort();
		this.portaDestino = dis.readShort();
		this.numeroSequencia = dis.readInt();
		this.numeroAck = dis.readInt();
		int unusedAndFlags = dis.readInt();
		if((unusedAndFlags & 0x0400) != 0x0400){
			this.syn = false;
		}
		else {
			this.syn = true;
		}
		if((unusedAndFlags & 0x0200) != 0x0200){
			this.ack = false;
		}
		else {
			this.ack = true;
		}
		if ((unusedAndFlags & 0x0100) != 0x0100){
			this.fin = false;
		}
		else {
			this.fin = true;
		}
		
		this.dados = new byte[tamanhoPacote - 16];
		dis.read(this.dados, 0, tamanhoPacote - 16);
	}
	
	public byte[] toByteArray() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(16 + dados.length);
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeShort(portaOrigem);
		dos.writeShort(portaDestino);
		dos.writeInt(numeroSequencia);
		dos.writeInt(numeroAck);
		int unusedAndFlags = 0;
		if(syn) unusedAndFlags = (unusedAndFlags | 0x0400);	
		if(ack) unusedAndFlags = (unusedAndFlags | 0x0200);
		if(fin) unusedAndFlags = (unusedAndFlags | 0x0100);
		dos.writeInt(unusedAndFlags);
		dos.write(dados);
		byte[] pacoteByte = baos.toByteArray();
		return pacoteByte;
	}
	
	

	public int getPortaOrigem() {
		return portaOrigem;
	}


	public void setPortaOrigem(int portaOrigem) {
		this.portaOrigem = portaOrigem;
	}


	public int getPortaDestino() {
		return portaDestino;
	}


	public void setPortaDestino(int portaDestino) {
		this.portaDestino = portaDestino;
	}


	public int getNumeroSequencia() {
		return numeroSequencia;
	}


	public void setNumeroSequencia(int numeroSequencia) {
		this.numeroSequencia = numeroSequencia;
	}


	public int getNumeroAck() {
		return numeroAck;
	}


	public void setNumeroAck(int numeroAck) {
		this.numeroAck = numeroAck;
	}


	public boolean isSyn() {
		return syn;
	}


	public void setSyn(boolean syn) {
		this.syn = syn;
	}


	public boolean isAck() {
		return ack;
	}


	public void setAck(boolean ack) {
		this.ack = ack;
	}


	public boolean isFin() {
		return fin;
	}


	public void setFin(boolean fin) {
		this.fin = fin;
	}


	public byte[] getDados() {
		return dados;
	}


	public void setDados(byte[] dados) {
		this.dados = dados;
	}

}
