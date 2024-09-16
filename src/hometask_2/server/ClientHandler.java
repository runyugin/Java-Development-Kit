package hometask_2.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Server server;
    private Socket socket; // сокет, через который сервер общается с клиентом,
    private DataInputStream in; // поток чтения из сокета
    private String name;

    public ClientHandler(Server parent,Socket socket) throws IOException {
        this.socket = socket;
        this.server = parent;
        in = new DataInputStream(socket.getInputStream()); 
        this.start(); // вызываем run()     
    }

    @Override
    public void run() {      
        String word;
        try {            
        server.sendHistory(socket);  
        //server.updateNames(name, true);
            //this.name = in.readUTF(); //получение имени
            while (true) {
                word = in.readUTF();
                server.sendToClients(word, this.socket);
            }

        } catch (IOException e) {
        }
    }

    public String getNickname() {
        return this.name;
    }

    public Socket getSocket() {
        return this.socket;
    }

}
