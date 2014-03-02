package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Window.Type;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import core.Core;


import exceptions.CampoObrigatorioException;
import exceptions.SenhaIncorretaException;
import exceptions.UsuarioInexistenteException;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField tf_login;
	private JPasswordField tf_senha;
	private static Core core = new Core();

	public static Core getCore() {
		return core;
	}

	public static void setCore(Core core) {
		Login.core = core;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					core.inicializarListas();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("FALA+");
		setResizable(false);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null); 
		
		JLabel lblBemvindoAo = new JLabel("Bem-vindo ao:");
		lblBemvindoAo.setBounds(23, 38, 98, 14);
		contentPane.add(lblBemvindoAo);
		
		JLabel lblLogin = new JLabel("Login : ");
		lblLogin.setBounds(23, 209, 46, 14);
		contentPane.add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha : ");
		lblSenha.setBounds(23, 239, 46, 14);
		contentPane.add(lblSenha);
		
		tf_login = new JTextField();
		tf_login.setBounds(73, 209, 86, 20);
		contentPane.add(tf_login);
		tf_login.setColumns(10);
		
		tf_senha = new JPasswordField();
		tf_senha.setBounds(73, 234, 86, 20);
		contentPane.add(tf_senha);
		
		JButton btn_entrar = new JButton("Entrar");
		btn_entrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					System.out.println(tf_login.getText());
					System.out.println( tf_senha.getText());
					
					core.fazerLoginUsuario(tf_login.getText(), tf_senha.getText());
					System.out.println("login realizado !");
				} catch (CampoObrigatorioException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					//e1.printStackTrace();
				} catch (SenhaIncorretaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					//e1.printStackTrace();
				} catch (UsuarioInexistenteException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					//e1.printStackTrace();
				}
				
			
			}
		});
		btn_entrar.setForeground(Color.WHITE);
		btn_entrar.setBackground(new Color(0, 102, 153));
		btn_entrar.setBounds(63, 280, 89, 23);
		contentPane.add(btn_entrar);
		
		JButton btn_cadastar = new JButton("Cadastrar");
		btn_cadastar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cadastro tela = new Cadastro();
				tela.setVisible(true);
			}
		});
		btn_cadastar.setForeground(Color.WHITE);
		btn_cadastar.setBackground(new Color(0, 102, 153));
		btn_cadastar.setBounds(301, 280, 98, 23);
		contentPane.add(btn_cadastar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/assets/logooo.png")));
		lblNewLabel.setBounds(51, 68, 414, 160);
		contentPane.add(lblNewLabel);
		
		JLabel fundo = new JLabel("");
		fundo.setToolTipText("");
		fundo.setBounds(0, 0, 444, 326);
		fundo.setIcon(new ImageIcon(Login.class.getResource("/assets/fundo.png")));
		contentPane.add(fundo);
	}
}
