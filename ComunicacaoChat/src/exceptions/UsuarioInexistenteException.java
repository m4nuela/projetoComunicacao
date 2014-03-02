package exceptions;

public class UsuarioInexistenteException extends Exception{

	String login;
	public UsuarioInexistenteException(String login){
		super("O usuário " + login + " não está cadastrado no sistema.");
	}
	
	public void getLogin(){
	this.login = login;
	}
}
