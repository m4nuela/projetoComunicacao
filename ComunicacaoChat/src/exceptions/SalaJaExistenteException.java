package exceptions;

public class SalaJaExistenteException extends Exception {
	String nome;
	String id;
	
	public SalaJaExistenteException(String nome, String id){
		super("A sala escolhida j� existe.");
		this.nome = nome;
		this.id = id;
	}
	
}
