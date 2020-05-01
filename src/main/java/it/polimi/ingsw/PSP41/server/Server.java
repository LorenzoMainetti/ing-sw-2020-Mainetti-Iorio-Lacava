package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static final int PORT = 9090;
    private static ExecutorService executor = Executors.newFixedThreadPool(128);
    private List<ClientHandler> clients = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private List<String> gods = new ArrayList<>();
    private static int playerNumber;

    //Deregister client
    public synchronized void deregisterConnection(ClientHandler client) {
        clients.remove(client);
    }

    /*public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }*/

    //TODO rendere questo metodo run e fare ServerApp con il main
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        int clients = 0;
        while(true){
            try {
                clients++;
                // Massimo 3 client connessi
                if (clients > 3) break;
                // Creo socket
                System.out.println("[SERVER] Waiting for client connection...");
                Socket newSocket = serverSocket.accept();
                System.out.println("[SERVER] Client connected to server");
                ClientHandler clientHandler = new ClientHandler(newSocket, clients);
                // Eseguo handler dei client
                executor.submit(clientHandler);
            } catch (IOException e) {
                System.out.println("[SERVER] Connection Error!");
            }
        }
    }

}