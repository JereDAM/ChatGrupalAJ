package net.salesianos.server.threads;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private ObjectInputStream clientObjInStream;
    private ObjectOutputStream clientObjOutStream;
    private ArrayList<ObjectOutputStream> connectedObjOutputStreamList;

    public ClientHandler(ObjectInputStream clientObjInStream, ObjectOutputStream clientObjOutStream,
        ArrayList<ObjectOutputStream> connectedObjOutputStreamList) {
        this.clientObjInStream = clientObjInStream;
        this.clientObjOutStream = clientObjOutStream;
        this.connectedObjOutputStreamList = connectedObjOutputStreamList;
    }

  @Override
  public void run() {
    String clientName = "";
    LocalTime hora = LocalTime.now();
    try {
        System.out.println("Recibiendo nombre");
        clientName = this.clientObjInStream.readUTF();
        System.out.println("nombre: " + clientName);
      while (true) {
        String msgReceived = this.clientObjInStream.readUTF();
        System.out.println(hora + clientName + ":" + msgReceived.toString());

        for (ObjectOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
          if (otherObjOutputStream != this.clientObjOutStream) {
            otherObjOutputStream.writeUTF(msgReceived);
          }
        }
      }
    } catch (EOFException eofException) {
      this.connectedObjOutputStreamList.remove(this.clientObjOutStream);
      System.out.println("CERRANDO CONEXIÓN CON " + clientName.toUpperCase());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
