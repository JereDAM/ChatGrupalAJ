package net.salesianos.server.threads;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import net.salesianos.shared.chat.Chat;

public class ClientHandler extends Thread{
    private DataInputStream clientObjInStream;
    private DataOutputStream clientObjOutStream;
    private ArrayList<DataOutputStream> connectedObjOutputStreamList;
    private Chat chat;

    public ClientHandler(DataInputStream clientObjInStream, DataOutputStream clientObjOutStream,
        ArrayList<DataOutputStream> connectedObjOutputStreamList,Chat chat) {
        this.clientObjInStream = clientObjInStream;
        this.clientObjOutStream = clientObjOutStream;
        this.connectedObjOutputStreamList = connectedObjOutputStreamList;
        this.chat = chat;
    }

  @Override
  public void run() {
    String clientName = "";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalTime hora = LocalTime.now();
    String horaFormateada = hora.format(formatter);
    try {
        System.out.println("Recibiendo nombre");
        clientName = this.clientObjInStream.readUTF();
        System.out.println("nombre: " + clientName);
        chat.showMsg(clientObjOutStream);
      while (true) {
        String msgReceived = this.clientObjInStream.readUTF();

        if(msgReceived.startsWith("msg:")){
          String newMsg = horaFormateada + " " + clientName + ":" + msgReceived.toString().replace("msg:","");
          chat.addMsg(newMsg);
          for (DataOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
            if (otherObjOutputStream != this.clientObjOutStream) {
              otherObjOutputStream.writeUTF(newMsg);
              otherObjOutputStream.flush();
            }
          }
        } else {
          System.out.println("Debe escribir el prefijo correcto...");
        }
        
      }
    } catch (IOException e) {
      // e.printStackTrace();
      System.out.println("CERRANDO CONEXIÓN CON " + clientName.toUpperCase());
      this.connectedObjOutputStreamList.remove(this.clientObjOutStream);
    }

  }
}
