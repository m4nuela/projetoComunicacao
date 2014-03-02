package testes;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.JLabel;

import core.Core;
import entidades.Usuario;
import exceptions.CampoObrigatorioException;
import exceptions.SalaJaExistenteException;

public class ClienteTeste {

	public static void main(String[] args) throws SalaJaExistenteException, CampoObrigatorioException {

		Scanner in = new Scanner (System.in);
		String ip = "localhost";

		try {
			Core core = new Core (ip);
			System.out.println("fui pro core");
			String comando;

			while(true){

				comando = in.nextLine();
				JLabel a = new JLabel("/assets/AvatarDefault.png");
				Usuario user = new Usuario("Thiago", "tas4", "123", "thiago", a);
				System.out.println("Cheguei do core");
				if(comando.equals("criarSala")) {
					System.out.println("nome");
					String nome = in.nextLine();
					System.out.println("avatar (int)");
					int avatar = in.nextInt();
					System.out.println("sala");
					String sala = in.next();
					System.out.println("chat (bool)");
					boolean chat = in.nextBoolean();
					System.out.println("protegida (bool)");
					boolean protegida = in.nextBoolean();
					System.out.println("senha");
					String senha = in.next();
					System.out.println("criar uma sala");
					core.criarSala(nome, protegida, senha, user);
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		} catch(SalaJaExistenteException e){
			e.printStackTrace();
		}
	}
}