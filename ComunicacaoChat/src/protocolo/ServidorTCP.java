package protocolo;

import java.io.*;
import java.net.*;
 
 
public class ServidorTCP {
        public static void main(String[] args) throws Exception {
               
               
                String inFormClient;
                String outToClient;
               
               
                ServerSocket welcomeSocket = new ServerSocket(2000);
                Socket sock = welcomeSocket.accept();
                Socket socketRetorno = new Socket("localhost", 2001);
                Imprimir imprimir = new Imprimir(sock);
                Enviar enviar  = new Enviar(socketRetorno);
                imprimir.start();
                enviar.start();
                       
                        while(true);
        }
}
 