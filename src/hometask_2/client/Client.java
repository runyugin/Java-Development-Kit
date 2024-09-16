package hometask_2.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client implements ClientSend  {
   private static ClientRecieve clientRecieve;
   private static ClientWindow clientWindow;

   static Socket clientSocket;
   static DataInputStream din;
   static DataOutputStream dout;
   static ObjectInputStream ois;

   public ClientSend getInterface() {
      return this;
   }

   public static void main(String[] args) {
      Client myClient =new Client();
      clientWindow = new ClientWindow(myClient);
      clientRecieve = clientWindow.getInterface();
      try {
         clientSocket = new Socket(clientWindow.getServerIP(), clientWindow.getServerPort());
         din = new DataInputStream(clientSocket.getInputStream());
         //ois = new ObjectInputStream(clientSocket.getInputStream());
         dout = new DataOutputStream(clientSocket.getOutputStream());
         clientRecieve.checkConnection("Успешно подключились",true);
         //dout.writeUTF(clientWindow.getNick());// выслать имя юзера на сервер
         String msgin = "";
         while (!clientWindow.closed) {
            // while (!msgin.equals("exit")) {
                  // получение списка пользователей
                 /*  try {
                     clientRecieve.nameListUpdate((String[]) ois.readObject());
                  } catch (Exception ex) {
                     System.out.println(ex.getMessage());
                  } */
            msgin = din.readUTF();
            clientRecieve.resieveMsg(msgin);
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         clientRecieve.checkConnection(e.getMessage(),false);
      }
   }

   @Override
   public void sendtoServer(String s) {
      try {
         dout.writeUTF(s.trim());
      } catch (Exception e) {
         clientRecieve.checkConnection(e.getMessage(),false);
         System.out.println(e.getMessage());
      }
   }

}
