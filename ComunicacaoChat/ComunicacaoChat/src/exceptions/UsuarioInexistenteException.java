package exceptions;

public class UsuarioInexistenteException extends Exception{

	String login;
	public UsuarioInexistenteException(String login){
		super("O usu�rio " + login + " n�o est� cadastrado no sistema.");
	}
	
	public void getLogin(){
	this.login = login;
	}
}
