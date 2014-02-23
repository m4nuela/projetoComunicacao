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
	ArrayList<Usuario> lista;
	Usuario dono;
	File conversa;
	
	public Sala(ArrayList<Usuario> lista, String nome, boolean protegida, String senha, Usuario dono, File file){
		this.nome = nome;
		this.id = (int) (Math.random()*100); /// math.random 
		this.lista = lista;
		this.protegida = protegida;
		this.senha = senha;		
		this.dono = dono;
		this.conversa = file;
	}
		

	public File getConversa() {
		return conversa;
	}


	public void setConversa(File conversa) {
		this.conversa = conversa;
	}


	public ArrayList<Usuario> getLista() {
		return lista;
	}


	public void setLista(ArrayList<Usuario> lista) {
		this.lista = lista;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isProtegida() {
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
