package Server;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WriteServidor extends Thread {
	
	int indice;
	NegociosServidor negocios;
	Server servidor;
	Lock lock;
	Condition cv;
	
	
	public WriteServidor(Server servidor, int indice, Lock lock, Condition cv, int indice2, NegociosServidor negocios){
		this.negocios = negocios;
		this.servidor = servidor;
		this.indice = indice;
		this.lock = lock;
		this.cv = cv;	
	}
	
	public void run(){
		while(true){
			this.lock.lock();
			
			if(!servidor.mudanca[indice]){
				try{
					this.cv.awaitNanos(1000);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
			
			if(servidor.mudanca[indice]){
				try{
					/** enviando a lista de salas para o cliente  **/
					negocios.enviarListaSalas(servidor.salas);
					servidor.mudanca[indice] = false;
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			
			this.cv.signalAll();
			this.lock.unlock();
		}
	}
	
	

}
