package hometask_2.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import hometask_2.Logger;

public class Server implements ServerSend {
   private static ServerWindow serverwindow;
   private static ServerRecieve serverRecieve;

   public static LinkedList<ClientHandler> serverList = new LinkedList<>(); // список подключений
   public static LinkedList<String> clientNames = new LinkedList<>();

   static ServerSocket serverSocket;
   static Socket clientSocket;
   static DataInputStream din;
   static DataOutputStream dout;

   public ServerSend getInterface() {
      return this;
   }

   public static void main(String[] args) {
      Server myserver = new Server();
      serverwindow = new ServerWindow(myserver);
      serverRecieve = serverwindow.getIntrf();
      try {
         serverSocket = new ServerSocket(1201);
         while (!serverwindow.closed) {
            clientSocket = serverSocket.accept();
            try {
               serverList.add(new ClientHandler(myserver,clientSocket));
               serverRecieve.getmessage("Client joined " + clientSocket.getInputStream().hashCode());               
            } catch (Exception e) {
            }            
         }
      } catch (Exception e) {
      }
   }

   public static void updateNames(String name, boolean action) throws IOException {
      // TODO добавить логику при выходе с сервера
      if (action == true) {
         clientNames.add(name);
      }
      sendUserList();
   }



   public static void sendUserList() throws IOException {
      String[] data = new String[] {};
      for (int i = 0; i < clientNames.size(); i++) {
         data[i] = clientNames.get(i);
      }
      ObjectOutputStream objOut;
      for (ClientHandler client : serverList) {
         objOut = new ObjectOutputStream(client.getSocket().getOutputStream());
         objOut.writeObject(data);
      }
   }
  

   public void sendToClients(String text, Socket current) throws IOException {
      for (ClientHandler client : serverList) {
         if (client.getSocket() != current) {
            dout = new DataOutputStream(client.getSocket().getOutputStream());
            dout.writeUTF(text);
            dout.flush();
         }
      }
      serverRecieve.getmessage(text); // meserverSocket mesasge from client
      Logger.writelog("Server", text);
   }

   @Override
   public void sendtoClient(String text) {
      try {
         sendToClients(text, null);
      } catch (Exception e) {
      }
   }
  
   public void sendHistory(Socket userSocket) {
      try {
         dout = new DataOutputStream(userSocket.getOutputStream());
         dout.writeUTF(Logger.readlog("Server"));
         dout.flush();
      } catch (IOException e) {
      }
   }


}
