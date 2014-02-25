package exceptions;

public class SenhaIncorretaException extends Exception{
	public SenhaIncorretaException(){
		super ("Senha incorreta, tente novamente.");
	}

}
