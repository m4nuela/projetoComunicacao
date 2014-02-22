package exceptions;

public class LoginJaExistenteException extends Exception{
	String login;
	
	public LoginJaExistenteException(String login){
		super("O login escolhido já existe. Favor escolher outro.");
		this.login = login;
	}
	
	public String getLogin(){
		return this.login;
	}

	
}
