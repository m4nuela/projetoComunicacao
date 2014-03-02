package protocolo.UDP;

import java.util.Random;

public class ModuloEspecial {
	int limiar;
	int sorteio;
	Random random;
	
	public ModuloEspecial(int limiar){
		random = new Random();
		this.limiar = limiar;
	}
	
	public boolean descartarPacote(){
		boolean descartar = true;
		sorteio = random.nextInt(100);
		//System.out.println("Numero sorteado: " + sorteio);
		if(sorteio >= limiar) descartar = false;
		//System.out.println(descartar? "Pacote descartado" : "Pacote nao sera descartado");
		return descartar;
	}
}
