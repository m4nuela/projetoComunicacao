package exceptions;

public class CampoObrigatorioException extends Exception{
	
	public CampoObrigatorioException(){
		super("Todos os campos são obrigatórios!");
		
	}

}
