package entidades;

import java.io.Serializable;
import java.util.ArrayList;

import entidades.Usuario;


public class Sala implements Serializable{
	String nome;
	String id;
	boolean protegida;
	String senha;
	ArrayList<Usuario> lista; 
	
	public Sala(ArrayList<Usuario> lista, String id, String nome, boolean protegida, String senha){
		this.nome = nome;
		this.id = id;
		this.lista = lista;
		this.protegida = protegida;
		this.senha = senha;		
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
	
	 	
}
