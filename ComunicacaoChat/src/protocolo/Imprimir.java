package protocolo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
 
 
public class Imprimir extends Thread{
        Socket aux;
       
        public Imprimir(Socket sock) throws Exception{
                this.aux = sock;
        }
       
        public void Imprimir (Socket sock) throws Exception{
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
               
                while(true){
                        System.out.println(socketIn.readLine());
                }              
        }
       
        public void run() {
                try {
                        Imprimir(aux);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
 
}