package exceptions;

public class SalaJaExistenteException extends Exception {
	String nome;
	
	
	public SalaJaExistenteException(String nome){
		super("A sala : " + nome + " já existe.");
		this.nome = nome;
		
	}
	
}
