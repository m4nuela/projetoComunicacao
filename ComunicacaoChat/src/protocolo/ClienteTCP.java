package protocolo;

import java.io.*;
import java.net.*;
import java.util.Scanner;
 
 
public class ClienteTCP {
        public static void main(String[] args) throws Exception {
               
               
                //lendo do teclado
               
 
                //criando um socket TCP
                Socket sock = new Socket("localhost", 2000);
                ServerSocket socket1 = new ServerSocket(2001);
                Socket socketRetorno = socket1.accept();  
                Enviar enviar = new Enviar(sock);
                Imprimir imprimir = new Imprimir(socketRetorno);
 
                //Stream de saída
 
                //resposta do servidor
               
                enviar.start();
                imprimir.start();
               
                while(true);
                }
}
 
 
//"172.20.4.193"
//"localhost"