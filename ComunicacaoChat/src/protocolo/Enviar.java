package protocolo;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
 
 
public class Enviar extends Thread {
        Socket Aux;
       
        public Enviar(Socket A) throws Exception{
                this.Aux = A;
        }
       
        public void Enviar (Socket A) throws Exception {
                DataOutputStream socketOut = new DataOutputStream(A.getOutputStream());
               
                while(true){
                        String inFromUser = new Scanner(System.in).nextLine();
                        socketOut.writeBytes(inFromUser + '\n');
                }
               
        }
       
        public void run(){
                try {
                        Enviar(Aux);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
 
}