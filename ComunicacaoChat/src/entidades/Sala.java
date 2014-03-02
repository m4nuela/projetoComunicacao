package entidades;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import entidades.Usuario;


public class Sala implements Serializable{
	String nome;
	int id;
	boolean protegida;
	String senha;
	ArrayList<Usuario> listaUsuarios;
	Usuario dono;
	File conversa;
	
	public Sala(String nome, boolean protegida, String senha, Usuario dono){
		this.nome = nome;
		this.id = (int) (Math.random()*100); /// math.random 
		this.listaUsuarios = new ArrayList<Usuario>();
		this.protegida = protegida;
		this.senha = senha;		
		this.dono = dono;
		this.conversa = new File(id + ".txt");
	}
		

	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}


	public File getConversa() {
		return conversa;
	}


	public void setConversa(File conversa) {
		this.conversa = conversa;
	}



	public void setListaUsuarios(ArrayList<Usuario> lista) {
		this.listaUsuarios = lista;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean getProtegida() {
		return protegida;
	}

	public void setProtegida(boolean protegida) {
		this.protegida = protegida;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Usuario getDono() {
		return dono;
	}


	public void setDono(Usuario dono) {
		this.dono = dono;
	}
	
	
}