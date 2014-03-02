package testes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JLabel;

import core.Core;
import entidades.Usuario;
import exceptions.CampoObrigatorioException;
import exceptions.LoginJaExistenteException;
import exceptions.SalaJaExistenteException;

public class ClienteTeste {

	public static void main(String[] args) throws SalaJaExistenteException, CampoObrigatorioException, IOException {

		Scanner in = new Scanner (System.in);
		String ip = "172.22.64.139";

		Core core = null;
		try {
			core = new Core (ip);
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}
		System.out.println("fui pro core");
		String comando;

		while(true){

			
			JLabel a = new JLabel("/assets/AvatarDefault.png");
			
			try {
				core.cadastrarUsuario("thiago", "tas4", "1234", "a", a);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (LoginJaExistenteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Cadastrei!");
			/*System.out.println("Cheguei do core");
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
			}*/
		}
	}
}