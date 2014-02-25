package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import core.Core;


import exceptions.CampoObrigatorioException;
import exceptions.LoginJaExistenteException;

import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Cadastro extends JFrame {

	private JPanel contentPane;
	private JTextField tf_nome;
	private JTextField tf_login;
	private JTextField tf_email;
	private JPasswordField tf_senha;
	private Core core;
	private Color aux;
	JLabel tf_avatar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cadastro frame = new Cadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param core 
	 * 
	 * 
	 */
	
	
	public Cadastro() {
		core = Login.getCore();
		setResizable(false);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null); 
		
		JLabel lblNome = new JLabel("Nome : ");
		lblNome.setBounds(27, 71, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblLogin = new JLabel("Login : ");
		lblLogin.setBounds(27, 108, 46, 14);
		contentPane.add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha :");
		lblSenha.setBounds(27, 144, 46, 14);
		contentPane.add(lblSenha);
		
		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(27, 181, 46, 14);
		contentPane.add(lblEmail);
		
		tf_nome = new JTextField();
		tf_nome.setBounds(72, 68, 105, 20);
		contentPane.add(tf_nome);
		tf_nome.setColumns(10);
		
		tf_login = new JTextField();
		tf_login.setBounds(72, 105, 105, 20);
		contentPane.add(tf_login);
		tf_login.setColumns(10);
		
		tf_email = new JTextField();
		tf_email.setBounds(72, 178, 105, 20);
		contentPane.add(tf_email);
		tf_email.setColumns(10);
		
		tf_senha = new JPasswordField();
		tf_senha.setBounds(72, 141, 105, 20);
		contentPane.add(tf_senha);
		
		final JLabel lblAlterarFoto = new JLabel("Alterar foto de perfil");
		lblAlterarFoto.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAlterarFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				aux = lblAlterarFoto.getForeground();
				lblAlterarFoto.setForeground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblAlterarFoto.setForeground(aux);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();  
		        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
		int res = fc.showOpenDialog(null);  

						if(res == JFileChooser.APPROVE_OPTION){  
							File diretorio = fc.getSelectedFile();     
							String local = diretorio.getAbsolutePath();
							local = local +  File.separatorChar;

							String directoryName = local;

							String diretorioo = directoryName.replace('/', File.separatorChar);
		                                        
		                                        char a = 92;
		                                        int i;
		                                        String aux = null;
		                                       
		                                        for(i=0;i<diretorioo.length()-1;i++){
		                                    if(diretorioo.charAt(i) == 92 ){
		                                        aux += diretorioo.charAt(i);
		                                        aux += diretorioo.charAt(i);
		                                    }else{
		                                        if(i==0){
		                                            aux= diretorioo.charAt(i)+"";
		                                        }else{
		                                        aux += diretorioo.charAt(i);
		                                    }
		                                    }
		                                }
		                                        
		                                     a = 34;   
		                               
		                               String haha = a + aux + a;
		                               
		                               
		                               
		                               tf_avatar.setIcon(new ImageIcon(haha));
		                               tf_avatar.setVisible(true);
						}
			}
		});
		
		tf_avatar = new JLabel("");
		tf_avatar.setIcon(new ImageIcon("C:\\Users\\Manuela\\Desktop\\AvatarDefault.png"));
		tf_avatar.setBounds(235, 11, 227, 239);
		contentPane.add(tf_avatar);
		lblAlterarFoto.setForeground(new Color(0, 0, 153));
		lblAlterarFoto.setBounds(284, 246, 130, 14);
		contentPane.add(lblAlterarFoto);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			Cadastro.this.dispose();
				
				
			}
		});
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setBackground(new Color(0, 102, 153));
		btnVoltar.setBounds(20, 273, 89, 23);
		contentPane.add(btnVoltar);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					core.cadastrarUsuario(tf_nome.getText(), tf_login.getText(), tf_senha.getText(), tf_email.getText(), tf_avatar);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (LoginJaExistenteException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (CampoObrigatorioException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
			}
		});
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBackground(new Color(0, 102, 153));
		btnCadastrar.setBounds(119, 273, 98, 23);
		contentPane.add(btnCadastrar);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Manuela\\Desktop\\fundo.png"));
		label.setToolTipText("");
		label.setBounds(0, 0, 444, 326);
		contentPane.add(label);
	}

}
