package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;
import javax.swing.JLabel;

import entidades.*;
import exceptions.CampoObrigatorioException;
import exceptions.LoginJaExistenteException;
import exceptions.SenhaIncorretaException;
import exceptions.UsuarioInexistenteException;
import protocolo.TCP.*;

public class NegociosServidor {
	CamadaTransporte comunicacao;

	public NegociosServidor(CamadaTransporte comunicacao){
		this.comunicacao = comunicacao;
	}

	void criarSala() throws ClassNotFoundException, IOException {
		Sala sala = null;
		String nomeSala = (String) comunicacao.receber();
		boolean protegidaSala = Boolean.parseBoolean(comunicacao.receber().toString());
		String senhaSala = (String) comunicacao.receber();
		Usuario donoSala = (Usuario) comunicacao.receber();
		sala = new Sala(nomeSala, protegidaSala, senhaSala, donoSala);
		this.gravarArquivoSala(sala);
	    comunicacao.enviar("receberSala");
	    comunicacao.enviar(sala);
	}

	void cadastrarUsuario() throws ClassNotFoundException, IOException, LoginJaExistenteException, CampoObrigatorioException, FileNotFoundException {
		Usuario user = null;
		String nomeUser = (String) comunicacao.receber();
		String loginUser = (String) comunicacao.receber();
		String senhaUser = (String) comunicacao.receber();
		String emailUser = (String) comunicacao.receber();
		JLabel avatarUser = (JLabel) comunicacao.receber();
		user = new Usuario (nomeUser, loginUser, senhaUser, emailUser, avatarUser);
		user.setIP(((TCPServidor) comunicacao).getSocket().getInetAddress());
		this.gravarArquivoUsuario(user);
		comunicacao.enviar("receberUsuario");
		comunicacao.enviar(user);
	}

	InfoSala entrarSala() throws ClassNotFoundException, IOException{
		InfoSala retorno = null;
		ArrayList<Sala> listaSalas = (ArrayList<Sala>) comunicacao.receber();
		ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) comunicacao.receber();
		Usuario usuario  =  (Usuario) comunicacao.receber();
		Sala sala = (Sala) comunicacao.receber();

		File file = new File("arquivosSalas.txt");
		FileOutputStream fosS = new FileOutputStream(file); 
		ObjectOutputStream oosS = new ObjectOutputStream(fosS);


		File fileU = new File("arquivosUsuarios.txt");
		FileOutputStream fosU = new FileOutputStream(fileU); 
		ObjectOutputStream oosU = new ObjectOutputStream(fosU);


		for (int i = 0; i < listaSalas.size(); i++) {
			Sala salaAux = listaSalas.get(i);
			if(salaAux.getId()==sala.getId()){
				retorno = new InfoSala(i, usuario, sala.getNome());
				listaSalas.get(i).getListaUsuarios().add(usuario);
				oosS.writeObject(sala);
			}else{
				oosS.writeObject(salaAux);
			}
		}
		oosS.close();


		for (int i = 0; i < listaUsuarios.size(); i++) {
			Usuario usuarioAux = listaUsuarios.get(i);
			if(usuarioAux==usuario){
				usuario.getSalasParticipa().add(sala);
				oosU.writeObject(usuario);
			}else{
				oosU.writeObject(usuarioAux);
			}
		}

		oosU.close();
		return retorno;

	}

	void sairSala() throws ClassNotFoundException, IOException{
		ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) comunicacao.receber();
		ArrayList<Sala> listaSalas = (ArrayList<Sala>) comunicacao.receber();
		Usuario usuario  =  (Usuario) comunicacao.receber();
		Sala sala = (Sala) comunicacao.receber();

		File file = new File("arquivosSalas.txt");
		FileOutputStream fosS = new FileOutputStream(file); 
		ObjectOutputStream oosS = new ObjectOutputStream(fosS);

		File fileU = new File("arquivosUsuarios.txt");
		FileOutputStream fosU = new FileOutputStream(fileU); 
		ObjectOutputStream oosU = new ObjectOutputStream(fosU);


		for (int i = 0; i < listaSalas.size(); i++) {
			if(listaSalas.get(i).getId()==sala.getId()){
				listaSalas.get(i).getListaUsuarios().remove(usuario);
				if(usuario == sala.getDono()){
					listaSalas.remove(sala);
				}else{
					oosS.writeObject(sala);
				}
			}else{
				oosS.writeObject(sala);
			}
		}

		oosS.close();


		for (int i = 0; i < listaUsuarios.size(); i++) {
			Usuario usuarioAux = listaUsuarios.get(i);
			if(usuarioAux==usuario){
				listaUsuarios.get(i).getSalasParticipa().remove(sala);
			}else{
				oosU.writeObject(usuarioAux);
			}
		}

		oosU.close();


	}

	void enviarMensagem(String mensagem) throws IOException {
		comunicacao.enviar("receberMensagem");
		comunicacao.enviar(mensagem);
	}

	void receberMensagem(String mensagem) throws IOException, ClassNotFoundException {
		String recebida = (String) comunicacao.receber();
		Sala lista = (Sala) comunicacao.receber();  

		File file = lista.getConversa();

		FileInputStream fisM = new FileInputStream(file);
		ObjectInputStream oisM = new ObjectInputStream(fisM);
		FileOutputStream fosM = new FileOutputStream(file); 
		ObjectOutputStream oosM = new ObjectOutputStream(fosM);

		String aux;
		while((aux = (String) oisM.readLine()) != null) {
			oosM.writeObject(aux);
		}

		oosM.writeObject(recebida);
		oisM.close();
		oosM.close();

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

	/**
	 *	void enviarListaUsuario(ArrayList<Usuario> listaUsuario) throws IOException {
	 *		comunicacao.enviar("enviarListaUsuarios");
	 *		for (int i = 0; i < listaUsuario.size(); i++) {
	 *			Usuario user = listaUsuario.get(i);
	 *			comunicacao.enviar(user);
	 *		}
	 *	}
	 **/


	void enviarObjeto(Object objeto) throws IOException {
		comunicacao.enviar("receberObjeto");
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
			}else{
				entrar = false;
			}
		}

		comunicacao.enviar("receberListaSalas");
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
			}else{
				entrar = false;
			}
		}
        comunicacao.enviar("receberListaUsuarios");
		comunicacao.enviar(listaUsuarios);
		oisU.close();
		fisU.close();
		return listaUsuarios;
	}

	void gravarArquivoSala(Sala sala2) throws IOException, ClassNotFoundException{
		File file = new File("arquivosSalas.txt");
		FileOutputStream fosS = new FileOutputStream(file); 
		ObjectOutputStream oosS = new ObjectOutputStream(fosS);
		ArrayList<Sala> listaSalas = this.lerArquivoSala();

		for (int i = 0; i < listaSalas.size(); i++) {
			Sala sala = listaSalas.get(i);
			oosS.writeObject(sala);
		}

		oosS.writeObject(sala2);
		oosS.close();
	}

	void gravarArquivoUsuario(Usuario user) throws IOException, ClassNotFoundException{
		ArrayList<Usuario> listaUsuario = null;
		File file = new File("arquivosUsuario.txt");
		FileOutputStream fosU = new FileOutputStream(file); 
		ObjectOutputStream oosU = new ObjectOutputStream(fosU);
		try {
			listaUsuario = this.lerArquivoUsuario();

		} catch (IOException e) {			

			oosU.writeObject(user);
			oosU.close();
		} 

		for (int i = 0; i < listaUsuario.size(); i++) {
			Usuario usuario = listaUsuario.get(i);
			oosU.writeObject(usuario);
		}


		oosU.writeObject(user);
		oosU.close();
	}

	void atualizarArquivoSala(ArrayList<Sala> listaSalas) throws IOException, ClassNotFoundException{
		File file = new File("arquivosSalas.txt");
		FileOutputStream fosS = new FileOutputStream(file); 
		ObjectOutputStream oosS = new ObjectOutputStream(fosS);


		String nomeSala = (String) comunicacao.receber();
		boolean protegidaSala = Boolean.parseBoolean(comunicacao.receber().toString());
		String senhaSala = (String) comunicacao.receber();

		for (int i = 0; i < listaSalas.size(); i++) {
			Sala sala = listaSalas.get(i);
			if(nomeSala.equalsIgnoreCase(listaSalas.get(i).getNome())){
				sala.setNome(nomeSala);
				sala.setSenha(senhaSala);
				sala.setProtegida(protegidaSala);
				oosS.writeObject(sala);
			} else {
				oosS.writeObject(sala);
			}
		}
		oosS.close();
	}

	@SuppressWarnings("unchecked")
	void atualizarArquivoUsuario() throws IOException, ClassNotFoundException{
		File file = new File("arquivosUsuario.txt");
		FileOutputStream fosU = new FileOutputStream(file); 
		ObjectOutputStream oosU = new ObjectOutputStream(fosU);


		String nomeUser = (String) comunicacao.receber();
		String loginUser = (String) comunicacao.receber();
		String senhaUser = (String) comunicacao.receber();
		String emailUser = (String) comunicacao.receber();
		JLabel avatarUser = (JLabel) comunicacao.receber();
		ArrayList<Usuario> listaUsuario = (ArrayList<Usuario>) comunicacao.receber();

		for (int i = 0; i < listaUsuario.size(); i++) {
			Usuario usuario = listaUsuario.get(i);
			if(usuario.getLogin().equals(loginUser)){
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

	@SuppressWarnings("unchecked")
	public void fazerLoginUsuario() throws SenhaIncorretaException, UsuarioInexistenteException, IOException, ClassNotFoundException{
		String login = (String) comunicacao.receber();
		String senha = (String) comunicacao.receber();
		ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) comunicacao.receber();
		Usuario user = null;

		int i = 0;
		boolean existe = false;
		boolean logado = false;


		while(!existe && i<listaUsuarios.size()){ // Vejo se j� existe alguem com o mesmo login ja cadastrado
			if (listaUsuarios.get(i).getLogin().equals(login)){
				existe = true;
				user = listaUsuarios.get(i);
			} else {
				i++;
			}

		}
		if(existe == true){
			if(senha.equals(user.getSenha())){
				logado = true;
				String out = null;
				if(logado){
					out = "logado";
					user.setStatus(true);
					gravarArquivoUsuario(user);
					comunicacao.enviar(out);
				}
			}else{
				throw new SenhaIncorretaException();
			}
		}else{
			throw new UsuarioInexistenteException(login);
		}

	}

	/**
	void mostrarListaSalasUsuario() throws ClassNotFoundException, IOException{
		Usuario user = (Usuario) comunicacao.receber();
		ArrayList<Sala> lista = user.getSalasParticipa();
		comunicacao.enviar(lista);
	}



	void mostrarListaUsuariosOnline(ArrayList <Usuario> lista ) throws IOException{
		ArrayList <Usuario> listaOnline = new ArrayList<Usuario>();

		for(int i=0;i<lista.size(); i++){
			Usuario userAux = lista.get(i);
			if(userAux.getStatus()==true){
				listaOnline.add(userAux);
			}
		}

		comunicacao.enviar(listaOnline);
	}
	 **/

}

class InfoSala {
	int indiceSala;
	Usuario usuario;
	String nomeSala;

	public InfoSala(int indiceSala, Usuario usuario, String nomeSala){
		this.indiceSala = indiceSala;
		this.usuario = usuario;
		this.nomeSala = nomeSala;
	}

	public int getIndiceSala() {
		return indiceSala;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getNomeSala() {
		return nomeSala;
	}

}
