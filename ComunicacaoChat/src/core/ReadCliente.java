package core;

import java.io.IOException;
import java.util.List;

import entidades.Sala;

public class ReadCliente extends Thread {

	Core core;

	public ReadCliente(Core core) {
		this.core = core;
	}

	public void run() {
		while (true) {
			try {
				String recebida = (String) core.comunicacao.receber();
				if (recebida.equals("enviarListaSalas")) {
					System.out.println("Vou atualizar a lista de salas");
					List<Sala> l = core.negocios.receberListaSala();
					for(int i = 0; i < l.size(); i++){
						Sala sala = l.get(i);
						//System.out.println("Sala tem " + sala.getQtdJogadores() + " jogadores no core");
					}
					core.setListaSalas(l);
					//System.out.println("Lista de salas atualizada");
					core.atualizarSala();
				} else if(recebida.equals("logado")){
					core.checkar("logado");
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
