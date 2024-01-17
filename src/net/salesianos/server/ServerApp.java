package net.salesianos.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;

import net.salesianos.server.threads.ClientHandler;
import net.salesianos.shared.constant.Ports;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Ports.SERVER_PORT);
        ArrayList<DataOutputStream> connectedDataOutputStream = new ArrayList<>();

        while (true) {
            System.out.println("Esperando conexión");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Conexión establecida");

            DataOutputStream clientObjOutStream = new DataOutputStream(clientSocket.getOutputStream());
            connectedDataOutputStream.add(clientObjOutStream);

            DataInputStream clientDataInStream = new DataInputStream(clientSocket.getInputStream());
            ClientHandler clientHandler = new ClientHandler(clientDataInStream,
            clientObjOutStream, connectedDataOutputStream);
            clientHandler.start();
        }
        // serverSocket.close();
    }
}
