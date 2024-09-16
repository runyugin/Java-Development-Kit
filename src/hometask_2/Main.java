package hometask_2;

import hometask_2.client.Client;
import hometask_2.server.Server;

public class Main {
    public static void main(String[] args) {
        new Thread(() ->new Server().main(args)).start();
        new Thread(() ->new Client().main(args)).start();
    }
}
