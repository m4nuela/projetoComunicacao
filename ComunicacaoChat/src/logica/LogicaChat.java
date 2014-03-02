package logica;

import java.util.ArrayList;
import entidades.Sala;
import entidades.Usuario;

public class LogicaChat {
	ArrayList<Usuario> onlineU;
	ArrayList<Sala> salas;

	public LogicaChat(ArrayList<Usuario> onlineU, ArrayList<Sala> salas){
		this.onlineU = onlineU;
		this.salas = salas;	
	}

	public ArrayList<Usuario> getOnlineU() {
		return onlineU;
	}

	public void setOnlineU(ArrayList<Usuario> onlineU) {
		this.onlineU = onlineU;
	}

	public ArrayList<Sala> getSalas() {
		return salas;
	}

	public void setSalas(ArrayList<Sala> salas) {
		this.salas = salas;
	}
	
	
	
	
}
