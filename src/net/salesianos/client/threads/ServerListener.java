package net.salesianos.client.threads;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerListener extends Thread {
    
    private DataInputStream objInStream;


    public ServerListener(DataInputStream objInStream2 ) {
        this.objInStream = objInStream2;
    }
    @Override
    public void run() {
        try {
            while (true) {
                String newClient = this.objInStream.readUTF();
                System.out.println(newClient);
            }
        } catch (IOException e2) {
           System.out.println("Conexion cerrada...");
        }
    }
}
