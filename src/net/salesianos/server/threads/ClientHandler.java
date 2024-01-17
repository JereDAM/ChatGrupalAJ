package net.salesianos.server.threads;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private DataInputStream clientObjInStream;
    private DataOutputStream clientObjOutStream;
    private ArrayList<DataOutputStream> connectedObjOutputStreamList;

    public ClientHandler(DataInputStream clientObjInStream, DataOutputStream clientObjOutStream,
        ArrayList<DataOutputStream> connectedObjOutputStreamList) {
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

        if(msgReceived.startsWith("msg:")){
          System.out.println(hora.of(NORM_PRIORITY, MIN_PRIORITY, MAX_PRIORITY) + " " + clientName + ":" + msgReceived.toString().substring(4));
          for (DataOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
            if (otherObjOutputStream != this.clientObjOutStream) {
              otherObjOutputStream.writeUTF(msgReceived);
            }
          }
        }
        
      }
    } catch (IOException e) {
      // e.printStackTrace();
      System.out.println("CERRANDO CONEXIÓN CON " + clientName.toUpperCase());
      this.connectedObjOutputStreamList.remove(this.clientObjOutStream);
    }

  }
}
