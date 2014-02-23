package entidades;

import java.net.InetAddress;
import java.io.Serializable;

import javax.swing.JLabel;

public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nome;
	String login;
	String senha;
	String email;
	InetAddress IP;
	JLabel avatar;
	boolean status; 
	
	public Usuario(String nome,String login, String senha,String email, JLabel avatar) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.avatar = avatar;
		this.status = true; // protegida
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public InetAddress getIP() {
		return IP;
	}

	public void setIP(InetAddress iP) {
		IP = iP;
	}

	public JLabel  getAvatar() {
		return avatar;
	}

	public void setAvatar(JLabel avatar) {
		this.avatar = avatar;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public void setStatus(boolean  status){
		this.status = status;
	}
	
}
