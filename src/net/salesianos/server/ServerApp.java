package net.salesianos.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;

import net.salesianos.server.threads.ClientHandler;
import net.salesianos.shared.constant.Ports;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Ports.SERVER_PORT);
        ArrayList<ObjectOutputStream> connectedObjOutputStream = new ArrayList<>();

        while (true) {
            System.out.println("Esperando conexión");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Conexión establecida");

            // ObjectOutputStream clientObjOutStream = new ObjectOutputStream(clientSocket.getOutputStream());
            // connectedObjOutputStream.add(clientObjOutStream);

            ObjectInputStream clientObjInStream = new ObjectInputStream(clientSocket.getInputStream());
            // ClientHandler clientHandler = new ClientHandler(clientObjInStream,
            // clientObjOutStream, connectedObjOutputStream);
            // clientHandler.start();

            String clientName = "";
            // LocalTime hora = LocalTime.now();
            try {
                System.out.println("Recibiendo nombre");
                clientName = clientObjInStream.readUTF();
                System.out.println("nombre: " + clientName);
                while (true) {
                    String msgReceived = clientObjInStream.readUTF();
                    System.out.println(clientName + ":" + msgReceived.toString());

                    
                }
            } catch (EOFException eofException) {
                System.out.println("CERRANDO CONEXIÓN CON " + clientName.toUpperCase());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // serverSocket.close();
    }
}
