import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class GUI extends JFrame{
	
	private JPanel contentPane;
	
	
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
			try{
				GUI frame = new GUI();
				frame.setVisible(true);
			}catch(Exception e){
				e.printStackTrace();
				
			}
			}
		});
	}
	
	
	public GUI(){
		setTitle("Fala+"); //Coloca subtitulo
		setResizable(false);  //Não permite mudar o tamanho
		setType(Type.UTILITY);  //Deixa ajustado
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Faz com que o programa seja terminado ao fecha-lo
		setBounds(0, 0, 350, 735); //Delimita onde o frame aparecerá e seu tamanho (horizontal, vertical, largura, altura)
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null); 
		
		
	}
	
	
}



