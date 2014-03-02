package core;

public class ThreadAtualiza extends Thread {
	
	Core core;
	
	public ThreadAtualiza(Core core){
		this.core = core;
	}

	public void run(){
		while(true){
			core.atualizarSala();
			try {
				sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
