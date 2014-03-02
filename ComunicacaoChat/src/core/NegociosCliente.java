package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;

import entidades.Sala;
import entidades.Usuario;
import exceptions.CampoObrigatorioException;
import exceptions.LoginJaExistenteException;
import protocolo.TCP.CamadaTransporte;

public class NegociosCliente {
	CamadaTransporte comunicacao;

	public NegociosCliente(CamadaTransporte comunicacao){
		this.comunicacao = comunicacao;
	}


	void criarSala(String nome, boolean protegida, String senha, Usuario dono) throws IOException{
		comunicacao.enviar("criarSala");
		comunicacao.enviar(nome);
		comunicacao.enviar(protegida);
		if(protegida){
			comunicacao.enviar(senha);
		}else{
			comunicacao.enviar("0_0");
		}
		comunicacao.enviar(dono);	

	}

	void cadastrarUsuario(String nome,String login, String senha,String email, JLabel avatar) throws LoginJaExistenteException, CampoObrigatorioException, IOException, FileNotFoundException, ClassNotFoundException{
		comunicacao.enviar("cadastrarUsuario");
		comunicacao.enviar(nome);
		comunicacao.enviar(login);
		comunicacao.enviar(senha);
		comunicacao.enviar(email);
		comunicacao.enviar(avatar);
	}

	void entrarSala(ArrayList<Sala> listaSalas, ArrayList<Usuario> listaUsuarios, Usuario usuario, Sala sala) throws IOException{
		comunicacao.enviar("entrarSala");
		comunicacao.enviar(listaSalas);
		comunicacao.enviar(listaUsuarios);
		comunicacao.enviar(usuario);
		comunicacao.enviar(sala);
	}

	void sairSala(ArrayList<Usuario> listaUsuarios,ArrayList<Sala> listaSalas, Usuario usuario, Sala sala) throws IOException{
		comunicacao.enviar("sairSala");
		comunicacao.enviar(listaUsuarios);
		comunicacao.enviar(listaSalas);
		comunicacao.enviar(usuario);
		comunicacao.enviar(sala);

	}

	void enviarMensagem(String msg) throws IOException{
		comunicacao.enviar("enviarMensagem");
		comunicacao.enviar(msg);

	}


	String receberMensagem() throws ClassNotFoundException, IOException{
		String recebida = (String) comunicacao.receber();
		return recebida;
	}

	ArrayList<Sala> receberListaSalas() throws ClassNotFoundException, IOException{
		ArrayList<Sala> listaSala = (ArrayList<Sala>) comunicacao.receber();
		return listaSala;
	}
	
	
	ArrayList<Usuario> receberListaUsuarios() throws ClassNotFoundException, IOException{
		ArrayList<Usuario> listaUsuario = (ArrayList<Usuario>) comunicacao.receber();
	return listaUsuario;
	}

	/*
	ArrayList<Usuario> receberListaUsuariosOnline() throws ClassNotFoundException, IOException{
		ArrayList<Usuario> listaUsuariosOnline = (ArrayList<Usuario>)comunicacao.receber();
		return listaUsuariosOnline;
	}
	*/

	Sala receberSala() throws ClassNotFoundException, IOException{
		return (Sala) comunicacao.receber();
	}

	void desconectarUsuario() throws IOException {
		comunicacao.desconectar();
	}

	void conectarUsuario(String ip) throws IOException{
		comunicacao.conectar(ip);
	}
	
	void enviarObjeto(Object obj ) throws IOException{
		comunicacao.enviar("enviarObjeto");
		comunicacao.enviar(obj);
	}
	
	Object receberObjeto() throws ClassNotFoundException, IOException{
		Object retorno = comunicacao.receber();
		return retorno;
		
	}
	
	void atualizarUsuario(String nome,String login, String senha,String email, JLabel avatar) throws IOException{
		comunicacao.enviar("atualizarUsuario");
		comunicacao.enviar(nome);
		comunicacao.enviar(login);
		comunicacao.enviar(senha);
		comunicacao.enviar(email);
		comunicacao.enviar(avatar);
	}
	
	 void atualizarSala(String nome, boolean protegida,String senha, String dono) throws IOException{
		 comunicacao.enviar("atualizarSala");   
		 comunicacao.enviar(nome);
		 comunicacao.enviar(protegida);
		 comunicacao.enviar(senha);
		 comunicacao.enviar(dono);
	}
	 
	 void fazerloginUsuario(String login, String senha) throws IOException{
		 comunicacao.enviar("fazerLoginUsuario");
		 comunicacao.enviar(login);
		 comunicacao.enviar(senha);
	 }
	
}
