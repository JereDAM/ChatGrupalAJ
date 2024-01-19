package net.salesianos.shared.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Chat {
    private ArrayList<String> totalMsg = new ArrayList<>();

    public synchronized void addMsg (String msg){
        totalMsg.add(msg);
    }

    public synchronized void showMsg(DataOutputStream otherObjOutputStream){
        if(!totalMsg.isEmpty()){
            // for (String msg : totalMsg) {
            //     try {
            //         otherObjOutputStream.writeUTF(msg);
            //         otherObjOutputStream.flush();
            //     } catch (IOException e) {
            //         e.printStackTrace();
            //     }
            // }
            totalMsg.forEach( msg -> {
                try {
                    otherObjOutputStream.writeUTF(msg);
                    otherObjOutputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
