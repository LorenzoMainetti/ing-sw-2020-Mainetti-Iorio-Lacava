package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.server.Server;

public class ServerApp {

    public static void main(String[] args) {
        Server server;

        server = new Server();
        server.run();

    }

}
