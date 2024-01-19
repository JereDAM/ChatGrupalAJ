package net.salesianos.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import net.salesianos.client.threads.ServerListener;
import net.salesianos.shared.constant.Ports;

public class ClientApp {
    public static void main(String[] args) throws Exception {
        
        String userOption = "";
        final Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca su nombre de usuario :");
        String user = sc.nextLine();

        Socket socket = new Socket(Ports.JEREMY_IP , Ports.SERVER_PORT);
        DataOutputStream objOutStream = new DataOutputStream(socket.getOutputStream());
        objOutStream.writeUTF(user);
        objOutStream.flush();

    
        DataInputStream objInStream = new DataInputStream(socket.getInputStream());
        ServerListener serverListener = new ServerListener(objInStream);
        serverListener.start();
        
        while (userOption != "bye") {

            System.out.println("Introduzca el mensaje : ");

            userOption = sc.nextLine();

            if (userOption.startsWith("msg:")) {
                objOutStream.writeUTF(userOption);
                objOutStream.flush();
            }
           

            if(userOption.startsWith("bye")){
                userOption = "bye";
                sc.close();
                objInStream.close();
                objOutStream.close();
                socket.close();
            }
        }

        
        
        
    }
}
