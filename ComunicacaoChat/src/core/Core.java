package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import protocolo.TCP.CamadaTransporte;
import protocolo.TCP.TCPCliente;
import protocolo.TCP.TCPServidor;
import protocolo.UDP.UDPCliente;
import entidades.Sala;
import entidades.Usuario;
import exceptions.CampoObrigatorioException;
import exceptions.LoginJaExistenteException;
import exceptions.SalaJaExistenteException;
import exceptions.SenhaIncorretaException;
import exceptions.UsuarioInexistenteException;


public class Core {
	ArrayList<Sala> listaSalas;
	ArrayList<Usuario> listaUsuarios;
	ArrayList<Usuario> listaUsuariosOnline;
	CamadaTransporte comunicacao;
	Usuario user = new Usuario(null, null, null, null, null);
	Sala sala = new Sala(null, false, null, user);
	NegociosCliente negocios;
	static String checar;
	InetAddress ip = InetAddress.getByName("localhost");

	String ip2;
	Socket socket;





	public Core() throws IOException, ClassNotFoundException {
		this.ip2 = ip.getHostAddress();
		socket = new Socket(ip2, 8020);
		this.listaSalas = new ArrayList<Sala>();
		this.listaUsuarios = new ArrayList<Usuario>();
		this.comunicacao = new TCPServidor(socket);
		negocios = new NegociosCliente(this.comunicacao);
		negocios.conectarUsuario("localhost");

	}


	///////////////////////////////////CADASTRAR_USUARIO//////////////////////////////////////////////////////////////////////////////////////////////////////
	public Usuario cadastrarUsuario(String nome, String login, String senha,String email, JLabel avatar) throws LoginJaExistenteException, CampoObrigatorioException, IOException, FileNotFoundException, ClassNotFoundException{

		negocios.cadastrarUsuario(nome, login, senha, email, avatar);
		listaUsuarios = negocios.receberListaUsuarios();
		Usuario user= negocios.receberUsuario();
		return user;	
	}

	/////////////////////////////////////////ATUALIZAR_USUARIO////////////////////////////////////////////////////////////////////////////////////////
	public void atualizarUsuario(String nome,String login, String senha,String email, JLabel avatar) throws IOException, ClassNotFoundException {
		negocios.atualizarUsuario(nome, login, senha, email, avatar, listaUsuarios);
		listaUsuarios = negocios.receberListaUsuarios();
	}


	/////////////////////////////////////////LOGIN/////////////////////////////////////////////////////////////////////////////////////////////////
	public void fazerLoginUsuario(String login, String senha) throws UsuarioInexistenteException, SenhaIncorretaException,CampoObrigatorioException, IOException {
		negocios.fazerloginUsuario(login, senha, listaUsuarios);
		if(checar.equals("logado")){
			for (int i = 0; i < listaUsuarios.size(); i++) {
				Usuario user = listaUsuarios.get(i);
				if(user.getLogin().equals(login)){
					listaUsuariosOnline.add(user);
				}
			}
		}
	}

	public void checkar(String logado){ //Checar se o login deu certo
		checar = logado;		
	}

	//////////////////////////////////////////MOSTRAR_LISTA_USUARIOS_ONLINE///////////////////////////////////////////////////////////////////////////
	public ArrayList<Usuario> mostrarListaUsuariosOnline() throws IOException, ClassNotFoundException {
		ArrayList<Usuario> retorno = negocios.receberListaUsuariosOnline();
		return retorno;
	}


	///////////////////////////////////////////////////////CRIAR_SALA///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Sala criarSala(String nome, boolean protegida, String senha, Usuario dono) throws SalaJaExistenteException, CampoObrigatorioException, IOException, ClassNotFoundException {
		negocios.criarSala(nome, protegida, senha, dono);
		listaSalas = negocios.receberListaSalas();
		Sala aux =  negocios.receberSala();
		return aux;
	}

	////////////////////////////////////////////////////////ATUALIZAR_SALA///////////////////////////////////////////////////////////////////
	public void atualizarSala(String nome, boolean protegida,String senha) throws IOException, ClassNotFoundException{
		negocios.atualizarSala(nome, protegida, senha);
		listaSalas = negocios.receberListaSalas();
	}


	//////////////////////////////////////////////////////ENVIAR_MSG////////////////////////////////////////////////////////////////////////////////
	public void enviarMensagem(String messagem, Sala sala) throws IOException{
		negocios.enviarMensagem(messagem, sala);	
	}

	/////////////////////////////////////////////////////RECEBER_MENSAGEM///////////////////////////////////////////////////////////////////////////
	public String receberMensagem() throws ClassNotFoundException, IOException{
		return negocios.receberMensagem();
	}


	///////////////////////////////////////////////////////ENTRAR_SALA//////////////////////////////////////////////////////////////////////////////////	
	void entrarSala(Usuario usuario, Sala sala) throws IOException, ClassNotFoundException{
		negocios.entrarSala(listaSalas, listaUsuarios, usuario, sala);
		listaSalas = negocios.receberListaSalas();
		listaUsuarios = negocios.receberListaUsuarios();

	}

	////////////////////////////////////////////////////////SAIR_SALA///////////////////////////////////////////////////////////////////////////////////
	public void sairSala(Usuario usuario, Sala sala) throws ClassNotFoundException, IOException{
		negocios.sairSala(listaUsuarios, listaSalas, usuario, sala);
		listaSalas = negocios.receberListaSalas();
		listaUsuarios = negocios.receberListaUsuarios();

	}


	///////////////////////////////////////////////////////////////MOSTRAR_LISTA_SALAS/////////////////////////////////////////////////////////////////////////
	public ArrayList<Sala> mostrarListaSalas() throws ClassNotFoundException, IOException{
		listaSalas = negocios.receberListaSalas();
		return listaSalas;

	}

	///////////////////////////////////////////////////////MOSTRAR_LISTA_USUARIOS////////////////////////////////////////////////////////////////////////
	public ArrayList<Usuario> mostrarListaUsuarios() throws ClassNotFoundException, IOException{
		listaUsuarios = negocios.receberListaUsuarios();
		return listaUsuarios;

	}


	//////////////////////////////////////////////////////////CONECTAR////////////////////////////////////////////////////////////////////////////////////
	public void conectar() throws IOException{
		negocios.conectarUsuario(ip2);
	}

	//////////////////////////////////////////////////////////DESCONECTAR////////////////////////////////////////////////////////////////////////////////////////	
	public void desconectar() throws IOException{
		negocios.desconectarUsuario(ip2, listaUsuarios);

		int i =0;
		boolean achou = false;
		while(!achou && i<listaUsuarios.size()){
			user = listaUsuarios.get(i); 
			if(user.getIP().equals(ip2)){
				listaUsuariosOnline.remove(user);
			}else{
				i++;
			}
		}


	}

	//////////////////////////////////////////////////////////ENVIAR_OBJETO/////////////////////////////////////////////////////////////////////////////////////
	public void enviarObjeto(Object objeto) throws IOException{
		negocios.enviarObjeto(objeto);
	}

	//////////////////////////////////////////////////////////RECEBER_OBJETO////////////////////////////////////////////////////////////////////////////////////
	public Object receberObjeto() throws ClassNotFoundException, IOException{
		Object objeto = negocios.receberObjeto();
		return objeto;
	}


}
