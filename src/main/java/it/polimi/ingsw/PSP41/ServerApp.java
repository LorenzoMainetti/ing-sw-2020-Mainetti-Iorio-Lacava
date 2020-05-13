package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.server.Server;

public class ServerApp {

    public static void main(String[] args) {
        Server server;
        while(true) {
            server = new Server();
            server.run();
        }
    }

}
