package server;

import java.io.IOException;
import java.util.ArrayList;

import protocolo.TCP.CamadaTransporte;
import entidades.*;

public class ReadServidor extends Thread {
	Sala salaUsuario;
	ArrayList<Sala> salas;
	Server servidor;
	NegociosServidor negocios;
	int indice;
	CamadaTransporte comunicacao;


	public ReadServidor(Server servidor, int indice, NegociosServidor negocios, CamadaTransporte comunicacao) {
		this.servidor = servidor;
		this.negocios = negocios;
		salas = new ArrayList<Sala>();
		this.indice = indice;
		this.comunicacao = comunicacao;
	}

	public void run(){
		//Começando a thread de leitura

		while(true){
			try {
				String recebida = (String) comunicacao.receber();
				if(recebida.equalsIgnoreCase("criarSala")){
					//Criar sala
					salaUsuario = negocios.criarSala();
					servidor.salas.add(salaUsuario);

					for (int i = 0; i < servidor.mudanca.length; i++) {
						if(servidor.threads[i] != null){
							//sinalizando a thread
							servidor.mudanca[i] = true;
						}
					}
				} else if(recebida.equalsIgnoreCase("entrarSala")){
					InfoSala info = negocios.entrarSala();
					modificarSalas(info);
					for (int i = 0; i < servidor.mudanca.length; i++) {
						if(servidor.threads[i] != null){
							//sinalizando thread
							servidor.mudanca[i] = true;
						}
					}
				} else if(recebida.equalsIgnoreCase("sairSala")){
					for (int i = 0; i < servidor.salas.size(); i++) {
						if (salaUsuario.getNome().equals(servidor.salas.get(i).getNome())) {
							//procurando a sala pelo nome, falta decidir se vai sair pelo nome ou pelo ID
							servidor.salas.remove(i);
							break;
						}
					}
					for (int i = 0; i < servidor.mudanca.length; i++) {
						servidor.mudanca[i] = true;
					}
				}
			} catch (ClassNotFoundException e){
				e.printStackTrace();

			} catch (IOException e){
				e.printStackTrace();
			}
		}

	}

	private synchronized void modificarSalas(InfoSala info){
		Sala sala = servidor.salas.get(info.indiceSala);
		sala.getListaUsuarios().add(info.usuario);
		salaUsuario = sala;
		servidor.salas.set(info.indiceSala, salaUsuario);
		//Atualizando a sala na lista, acrescentendo usuários
	}
}