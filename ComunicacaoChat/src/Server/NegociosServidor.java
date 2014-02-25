package Server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JLabel;

import entidades.*;
import protocolo.TCP.*;

public class NegociosServidor {

	CamadaTransporte comunicacao;


	public NegociosServidor(CamadaTransporte comunicacao){
		this.comunicacao = comunicacao;
	}

	Sala criarSala() throws ClassNotFoundException, IOException {
		Sala sala = null;
		String nomeSala = (String) comunicacao.receber();
		boolean protegidaSala = Boolean.parseBoolean(comunicacao.receber().toString());
		String senhaSala = (String) comunicacao.receber();
		Usuario donoSala = (Usuario) comunicacao.receber();
		sala = new Sala(nomeSala, protegidaSala, senhaSala, donoSala);
		return sala;
	}

	Usuario cadastrarUsuario() throws ClassNotFoundException, IOException {
		Usuario user = null;
		String nomeUser = (String) comunicacao.receber();
		String loginUser = (String) comunicacao.receber();
		String senhaUser = (String) comunicacao.receber();
		String emailUser = (String) comunicacao.receber();
		JLabel avatarUser = (JLabel) comunicacao.receber();
		user = new Usuario (nomeUser, loginUser, senhaUser, emailUser, avatarUser);
		user.setIP(((TCPServidor) comunicacao).getSocket().getInetAddress());
		return user;
	}

	void enviarSala(Sala sala) throws IOException {
		comunicacao.enviar("enviarSala");
		comunicacao.enviar(sala);
	}

	void enviarListaSalas(ArrayList<Sala> listaSalas) throws IOException {
		comunicacao.enviar("enviarListaSalas");
		for (int i = 0; i < listaSalas.size(); i++) {
			Sala sala = listaSalas.get(i);
			comunicacao.enviar(sala);
		}
	}

	void enviarUsuario(Usuario user) throws IOException {
		comunicacao.enviar("enviarUsuario");
		comunicacao.enviar(user);
	}

	void enviarListaUsuario(ArrayList<Usuario> listaUsuario) throws IOException {
		comunicacao.enviar("enviarListaUsuarios");
		for (int i = 0; i < listaUsuario.size(); i++) {
			Usuario user = listaUsuario.get(i);
			comunicacao.enviar(user);
		}
	}

	void desconectarUsuario() throws IOException {
		comunicacao.desconectar();
	}

	void conectarUsuario(String ip) throws IOException{
		comunicacao.conectar(ip);
	}

	void enviarMensagem(String mensagem) throws IOException {
		comunicacao.enviar("enviarMensagem");
		comunicacao.enviar(mensagem);
	}

	String receberMensagem(String mensagem) throws IOException, ClassNotFoundException {
		String recebida = (String) comunicacao.receber();
		return recebida;
	}

	void enviarObjeto(Object objeto) throws IOException {
		comunicacao.enviar("enviarObjeto");
		comunicacao.enviar(objeto);
	}

	Object receberObjeto(Object objeto) throws ClassNotFoundException, IOException{
		Object retorno = comunicacao.receber();
		return retorno;
	}

	ArrayList<Sala> lerArquivoSala() throws IOException, ClassNotFoundException{

		ArrayList<Sala> listaSalas = new ArrayList<Sala>();
		File arqS = new File("arquivosSalas.txt");
		FileInputStream fisS = new FileInputStream(arqS);
		ObjectInputStream oisS = new ObjectInputStream(fisS);
		Sala sala = null;

		boolean entrar;
		entrar = true;
		while(entrar){
			if((sala = (Sala) oisS.readObject() ) != null){
				listaSalas.add(sala); 
				if(oisS.available()==0){
					entrar = false;
				}
			}
		}
		comunicacao.enviar(listaSalas);
		oisS.close();
		fisS.close();
		return listaSalas;
	}

	ArrayList<Usuario> lerArquivoUsuario() throws IOException, ClassNotFoundException {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		File arqU = new File("arquivosUsuarios.txt");
		FileInputStream fisU = new FileInputStream(arqU);
		ObjectInputStream oisU = new ObjectInputStream(fisU);
		Usuario user = null;

		boolean entrar = true;
		while(entrar){
			if((user = (Usuario) oisU.readObject()) != null){
				listaUsuarios.add(user);  
				if(fisU.available()<100){
					entrar = false;
				}
			}
		}
		comunicacao.enviar(listaUsuarios);
		oisU.close();
		fisU.close();
		return listaUsuarios;
	}

	void gravarArquivoSala() throws IOException, ClassNotFoundException{
		File file = new File("arquivosSalas.txt");
		FileOutputStream fosS = new FileOutputStream(file); 
		ObjectOutputStream oosS = new ObjectOutputStream(fosS);
		ArrayList<Sala> listaSalas = this.lerArquivoSala();

		for (int i = 0; i < listaSalas.size(); i++) {
			Sala sala = listaSalas.get(i);
			oosS.writeObject(sala);
		}


		Sala sala = (Sala) comunicacao.receber();
		oosS.writeObject(sala);
		oosS.close();
	}

	void gravarArquivoUsuario() throws IOException, ClassNotFoundException{
		File file = new File("arquivosUsuario.txt");
		FileOutputStream fosU = new FileOutputStream(file); 
		ObjectOutputStream oosU = new ObjectOutputStream(fosU);
		ArrayList<Usuario> listaUsuario = this.lerArquivoUsuario();

		for (int i = 0; i < listaUsuario.size(); i++) {
			Usuario usuario = listaUsuario.get(i);
			oosU.writeObject(usuario);
		}


		Usuario usuario = (Usuario) comunicacao.receber();
		oosU.writeObject(usuario);
		oosU.close();
	}


	void atualizarArquivoSala() throws IOException, ClassNotFoundException{
		File file = new File("arquivosSalas.txt");
		FileOutputStream fosS = new FileOutputStream(file); 
		ObjectOutputStream oosS = new ObjectOutputStream(fosS);
		ArrayList<Sala> listaSalas = this.lerArquivoSala();

		String nomeSala = (String) comunicacao.receber();
		boolean protegidaSala = Boolean.parseBoolean(comunicacao.receber().toString());
		String senhaSala = (String) comunicacao.receber();
		Usuario donoSala = (Usuario) comunicacao.receber();

		for (int i = 0; i < listaSalas.size(); i++) {
			Sala sala = listaSalas.get(i);
			if(nomeSala.equalsIgnoreCase(listaSalas.get(i).getNome())){
				sala.setNome(nomeSala);
				sala.setDono(donoSala);
				sala.setSenha(senhaSala);
				sala.setProtegida(protegidaSala);
				oosS.writeObject(sala);
			} else {
				oosS.writeObject(sala);
			}
		}
		oosS.close();
	}

	void atualizarArquivoUsuario() throws IOException, ClassNotFoundException{
		File file = new File("arquivosUsuario.txt");
		FileOutputStream fosU = new FileOutputStream(file); 
		ObjectOutputStream oosU = new ObjectOutputStream(fosU);
		ArrayList<Usuario> listaUsuario = this.lerArquivoUsuario();

		String nomeUser = (String) comunicacao.receber();
		String loginUser = (String) comunicacao.receber();
		String senhaUser = (String) comunicacao.receber();
		String emailUser = (String) comunicacao.receber();
		JLabel avatarUser = (JLabel) comunicacao.receber();

		for (int i = 0; i < listaUsuario.size(); i++) {
			Usuario usuario = listaUsuario.get(i);
			if(usuario.getLogin().equalsIgnoreCase(listaUsuario.get(i).getLogin())){
				usuario.setNome(nomeUser);
				usuario.setEmail(emailUser);
				usuario.setSenha(senhaUser);
				usuario.setAvatar(avatarUser);
				oosU.writeObject(usuario);
			} else {
				oosU.writeObject(usuario);
			}
		}
		oosU.close();
	}


}
