package exceptions;

public class SalaJaExistenteException extends Exception {
	String nome;
	int id;
	
	public SalaJaExistenteException(String nome, int id){
		super("A sala escolhida j� existe.");
		this.nome = nome;
		this.id = id;
	}
	
}
