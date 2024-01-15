package net.salesianos.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

        Socket socket = new Socket("localhost" , Ports.SERVER_PORT);
        ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("mandando " + user);
        objOutStream.writeUTF(user);

        // ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream());
        // ServerListener serverListener = new ServerListener(objInStream);
        // serverListener.start();

        while (userOption != "bye") {
            userOption = sc.nextLine();
            System.out.println("escribiendo " + userOption);
            objOutStream.writeUTF(userOption);
        }

        sc.close();

        // objInStream.close();
        objOutStream.close();
        socket.close();
    }
}
