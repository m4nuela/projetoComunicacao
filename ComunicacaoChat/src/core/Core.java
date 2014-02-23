package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import protocolo.TCP.CamadaTransporte;
import protocolo.TCP.TCPCliente;
import entidades.Sala;
import entidades.Usuario;
import exceptions.CampoObrigatorioException;
import exceptions.LoginJaExistenteException;
import exceptions.SalaJaExistenteException;
import exceptions.SenhaIncorretaException;
import exceptions.UsuarioInexistenteException;


public class Core {
	ArrayList<Sala> listaSalas;
	ArrayList<Usuario> listaUsuarios;
	CamadaTransporte protocolo;
	Usuario user;
	Sala sala;
	boolean existe;
	boolean logado;
	int i=0;
	FileOutputStream fosU;
	ObjectOutputStream oosU;
	FileOutputStream fosS;
	ObjectOutputStream oosS;
	FileInputStream fisU;
	ObjectInputStream oisU;
	FileInputStream fisS;
	ObjectInputStream oisS;



	public Core(){
		this.listaSalas = new ArrayList<Sala>();

		this.listaUsuarios = new ArrayList<Usuario>();
		this.protocolo = new TCPCliente(4035);
	}


	///////////////////////////////////INICIALIZAR_LISTAS///////////////////////////////////////////////////
	public void inicializarListas() throws IOException, FileNotFoundException, ClassNotFoundException{


		///Usuarios
		fisU = new FileInputStream("arquivosUsuarios.txt");
		oisU = new ObjectInputStream(fisU);


		while( ( user = (Usuario) oisU.readObject() ) != null){

			listaUsuarios.add(user);  
		}

		oisU.close();





		///Salas
		fisS = new FileInputStream("arquivosSalas.txt");
		oisS = new ObjectInputStream(fisS);

		while( ( sala = (Sala) oisS.readObject() ) != null){

			listaSalas.add(sala);  
		}

		oisS.close();



	}




	///////////////////////////////////CADASTRAR_USUARIO////////////////////////////////////////////////////
	public void cadastrarUsuario(String nome,String login, String senha,String email, JLabel avatar) throws LoginJaExistenteException, CampoObrigatorioException, IOException, FileNotFoundException, ClassNotFoundException{
		existe = false;

		while(existe == false && i<listaUsuarios.size()){ // Vejo se já existe alguem com o mesmo login ja cadastrado
			if (listaUsuarios.get(i).getLogin() == login){
				existe=true;
				throw new LoginJaExistenteException(login); // na GUI é que eu a trato
			}else{
				i++;
			}

		}

		user.setNome(nome);
		user.setLogin(login);
		user.setSenha(senha);
		user.setEmail(email);
		user.setAvatar(avatar);

		if((user.getNome()).equals(null) || (user.getLogin()).equals(null) || (user.getSenha()).equals(null) || (user.getEmail()).equals(null) || (user.getAvatar()).equals(null)){
			throw new CampoObrigatorioException();
		}else{

			user = new Usuario(nome,login,senha,email,avatar);

			listaUsuarios.add(user); // adiciono à lista 

			fosU = new FileOutputStream("arquivoUsuarios.txt",true);  // gravar no arquivo e nao sobrescrever
			oosU = new ObjectOutputStream(fosU);

			// salva o objeto
			oosU.writeObject(user);

			oosU.close();
		}
	}





	/////////////////////////////////////////ATUALIZAR_USUARIO//////////////////////////////////////////
	public void atualizarUsuario(String nome,String login, String senha,String email, JLabel avatar) throws IOException{
		existe = false;
		logado = false;

		while(existe == false && i<listaUsuarios.size()){ // Vejo se existe alguem com o mesmo login ja cadastrado
			if (listaUsuarios.get(i).getLogin() == login){
				existe=true;
				user = listaUsuarios.get(i);

			}else{
				i++;
			}

		}
		if(existe){
			user.setNome(nome);
			user.setSenha(senha);
			user.setEmail(email);
			user.setAvatar(avatar);

			listaUsuarios.set(i, user); // depois de atualizar o dado na lista e tenho  q atualizar no arquivo!

			fosU = new FileOutputStream("arquivoUsuario.txt",false); // sobreescrever o arquivo com o obj.
			oosU = new ObjectOutputStream(fosU);
			oosU.writeObject(listaUsuarios.get(0)); // pega o primeiro obj. da lista
			oosU.close();
			// salva o objeto



			fosU = new FileOutputStream("arquivoUsuario.txt",true); // escrever depois do arq. ja existente
			oosU = new ObjectOutputStream(fosU);
			for(i=1;i<listaUsuarios.size();i++){
				oosU.writeObject(listaUsuarios.get(i));
			}	

			oosU.close();

		}

	}




	/////////////////////////////////////////LOGIN///////////////////////////////////////////////////////////////////////////
	public void fazerLoginUsuario(String login, String senha) throws UsuarioInexistenteException, SenhaIncorretaException{
		existe = false;
		while(existe == false && i<listaUsuarios.size()){ // Vejo se já existe alguem com o mesmo login ja cadastrado
			if (listaUsuarios.get(i).getLogin() == login){
				existe=true;
				user = listaUsuarios.get(i);

			}else{
				i++;
			}


		}
		if(existe == true){
			if(senha.equals(user.getSenha())){
				logado = true;
			}else{
				throw new SenhaIncorretaException();
			}
		}else{
			throw new UsuarioInexistenteException(login);
		}

	}








	public ArrayList<Usuario> mostrarListaUsuariosOnline() throws IOException, ClassNotFoundException {

		ArrayList<Usuario> retorno = new ArrayList<Usuario>();
		fisU = new FileInputStream("arquivosUsuarios");
		oisU = new ObjectInputStream(fisU);

		// recupera o objeto
		user = (Usuario) oisU.readObject();
		oisU.close();
		retorno.add(user);
		return retorno;
	}



	public void criarSala(String nome, String id, boolean protegida, String senha, ArrayList<Usuario> lista, Usuario dono, File file) throws SalaJaExistenteException, CampoObrigatorioException {
		/* toda sala tem que ter a lista de usuarios, e o dono(a pessoa que criou) da sala*/
		boolean existe = false;

		while(existe == false && i<listaSalas.size()){ // Vejo se já existe alguma sala com o mesmo id ou mesmo nome ja cadastrada
			if (listaSalas.get(i).getNome().equalsIgnoreCase(nome)){
				existe=true;
				throw new SalaJaExistenteException(nome); // na GUI é que eu a trato
			}else{
				i++;
			}

		}

		sala.setNome(nome);
		sala.setProtegida(protegida);
		sala.setSenha(senha);

		if((sala.getNome()).equals(null) || (sala.getSenha()).equals(null)){
			throw new CampoObrigatorioException();
		}else{
			sala = new Sala(lista,  nome, protegida, senha, dono, file);
			listaSalas.add(sala);
		}		
	}



	public void atualizarSala(){

	}


	public ArrayList<Sala> mostrarListaSalas(){
		return listaSalas;


	}


	public void enviarMensagem(){

	}


}
