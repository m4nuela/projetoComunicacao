package exceptions;

public class SalaJaExistenteException extends Exception {
	String nome;
	
	
	public SalaJaExistenteException(String nome){
		super("A sala : " + nome + " j� existe.");
		this.nome = nome;
		
	}
	
}
