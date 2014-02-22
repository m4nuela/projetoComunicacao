package protocolo.TCP;

import java.io.IOException;

public interface CamadaTransporte {
	
	public void conectar(String address) throws IOException;
	public void desconectar() throws IOException;
	public void enviar(Object in) throws IOException;
	public Object receber() throws IOException, ClassNotFoundException;
}

