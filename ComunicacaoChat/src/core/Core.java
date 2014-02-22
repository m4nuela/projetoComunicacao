package core;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import negocios.exceptions.RepositorioInvalidoException;
import protocolo.TCP.CamadaTransporte;
import protocolo.TCP.TCPCliente;
import entidades.Sala;
import entidades.Usuario;
import exceptions.CampoObrigatorioException;
import exceptions.LoginJaExistenteException;
import exceptions.SenhaIncorretaException;
import exceptions.UsuarioInexistenteException;


public class Core {
	ArrayList<Sala> listaSalas;
	ArrayList<Usuario> listaUsuarios;
	CamadaTransporte protocolo;
	Usuario user;
	boolean existe;
	boolean logado;
	int i=0;
	
	
	public Core(){
	this.listaSalas = new ArrayList<Sala>();
	
	this.listaUsuarios = new ArrayList<Usuario>();
	this.protocolo = new TCPCliente(4035);
	}
	
	
	
	
	
	
	
	///////////////////////////////////CADASTRAR_USUARIO////////////////////////////////////////////////////
	public void cadastrarUsuario(String nome,String login, String senha,String email, JLabel avatar) throws LoginJaExistenteException, CampoObrigatorioException{
		existe = false;
		
		while(existe == false && i<listaUsuarios.size()){ // Vejo se já existe alguem com o mesmo login ja cadastrado
		if (listaUsuarios.get(i).getLogin() == login){
			existe=true;
			throw new LoginJaExistenteException(login); // na GUI é que eu a trato
		}else{
			i++;
		}
		
		}
		
		user.setNome(nome);
		user.setLogin(login);
		user.setSenha(senha);
		user.setEmail(email);
		user.setAvatar(avatar);
		
		if((user.getNome()).equals(null) || (user.getLogin()).equals(null) || (user.getSenha()).equals(null) || (user.getEmail()).equals(null) || (user.getAvatar()).equals(null)){
			throw new CampoObrigatorioException();
		}else{
	   
		user = new Usuario(nome,login,senha,email,avatar);
		
		listaUsuarios.add(user);
		}
	}
	
	
	
	
	
	/////////////////////////////////////////ATUALIZAR_USUARIO//////////////////////////////////////////
	public void atualizarUsuario(String nome,String login, String senha,String email, JLabel avatar){
		existe = false;
		logado = false;
		
		while(existe == false && i<listaUsuarios.size()){ // Vejo se já existe alguem com o mesmo login ja cadastrado
			if (listaUsuarios.get(i).getLogin() == login){
				existe=true;
				user = listaUsuarios.get(i);
				
			}else{
				i++;
			}
			
			}
		if(existe){
			user.setNome(nome);
			user.setSenha(senha);
			user.setEmail(email);
			user.setAvatar(avatar);
			
			listaUsuarios.set(i, user);
		}
		
	}
	
	
	
	
	/////////////////////////////////////////LOGIN///////////////////////////////////////////////////////////////////////////
	public void fazerLoginUsuario(String login, String senha) throws UsuarioInexistenteException, SenhaIncorretaException{
		existe = false;
		while(existe == false && i<listaUsuarios.size()){ // Vejo se já existe alguem com o mesmo login ja cadastrado
			if (listaUsuarios.get(i).getLogin() == login){
				existe=true;
				user = listaUsuarios.get(i);
				
			}else{
				i++;
			}
			
			
		}
		if(existe == true){
			if(senha.equals(user.getSenha())){
				logado = true;
			}else{
				throw new SenhaIncorretaException();
			}
		}else{
			throw new UsuarioInexistenteException(login);
		}
		
	}

	
	
	
	
	
	
	
	public ArrayList<Usuario> mostrarListaUsuariosOnline(){
	
	}

	
	public void criarSala(){
		
	}
	
	public void atualizarSala(){
		
	}
	
	
	public ArrayList<Sala> mostrarListaSalas(){
		
		
	}
	
	
	public void enviarMensagem(){
		
	}
	
	
}
