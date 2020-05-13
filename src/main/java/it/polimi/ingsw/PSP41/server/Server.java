package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.controller.Controller;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.observer.ConnectionObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.PSP41.utils.GameMessage.fullLobby;

public class Server implements Runnable, ConnectionObserver {

    ServerSocket serverSocket;
    private final int PORT = 9090;
    private final ExecutorService executor = Executors.newFixedThreadPool(64);
    private List<ClientHandler> log = new ArrayList<>();

    //Deregister client
    public synchronized void deregisterConnection(ClientHandler client) {
        System.out.println("[SERVER] Unregistering client...");
        log.remove(client);
        System.out.println("[SERVER] Done!");
    }

    /*public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }*/

    /**
     * Start a match using resources from the notify of lobby
     * @param uim UserInputManager class used for inputs handling
     * @param virtualView virtual view server side
     * @param sortedPlayers match players list (and associated gods)
     */
    @Override
    public void updateServer(UserInputManager uim, VirtualView virtualView, List<Player> sortedPlayers) {
        Thread t = new Thread(() -> {
            System.out.println("[SERVER] game starts");
            Board board = new Board();
            Controller controller = new Controller(board, uim, virtualView, sortedPlayers);
            // Prima faccio scegliere la posizione iniziale dei worker ad ogni giocatore, poi inizio la partita
            controller.setWorkers();
            controller.play();
        });
        t.start();
    }

    /**
     * Manages disconnection: if the client disconnected is active, all the clients will be disconnected; else the client disconnected is removed from the server clients log
     * @param client disconnected client
     */
    @Override
    public void updateDisconnection(ClientHandler client) {
        if (client.isActive()) {
            for (ClientHandler ch : log) {
                ch.closeConnection();
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            deregisterConnection(client);
        }
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket close");
        }

        boolean fullLog = false;
        Lobby lobby = new Lobby();
        lobby.addObserver(this);

        while(true){
            try {
                // Creo socket
                System.out.println("[SERVER] Waiting for client connection...");
                Socket newSocket = serverSocket.accept();
                System.out.println("[SERVER] Client connected to server");
                ClientHandler clientHandler = new ClientHandler(newSocket, lobby, log.size() + 1);
                // Massimo 3 client connessi
                if (log.size() < 3) {
                    log.add(clientHandler);
                    clientHandler.addObserver(this);
                }
                executor.submit(clientHandler);
                if (log.size() <= 3 && !fullLog) {
                    clientHandler.readFromClient();
                    if (log.size() == 3) {
                        fullLog = true;
                    }
                }
                else {
                    // Altrimenti da problemi di sincronizzazione
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clientHandler.send(fullLobby);
                    clientHandler.setActive(false);
                    clientHandler.closeConnection();
                }
            } catch (IOException e) {
                System.out.println("[SERVER] Restarting server...");
                return;
            }
        }

    }
}