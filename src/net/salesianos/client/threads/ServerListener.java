package net.salesianos.client.threads;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListener extends Thread {
    
    private ObjectInputStream objInStream;

    public ServerListener(ObjectInputStream objInStream) {
        this.objInStream = objInStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String newClient = this.objInStream.readUTF();
                System.out.println("New user enter : " + newClient.toString().substring(4));
            }
        } catch (IOException e2) {
           System.out.println("Message sending are not working");
        }
    }
}
