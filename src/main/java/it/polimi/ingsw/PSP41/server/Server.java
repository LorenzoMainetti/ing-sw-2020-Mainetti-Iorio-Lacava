package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.observer.LobbyObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements Runnable, LobbyObserver {

    ServerSocket serverSocket;
    private final int PORT = 9090;
    private List<ClientHandler> log = new ArrayList<>();
    private boolean first = true;
    private final Object lock = new Object();

    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket close");
        }

        while (true) {
            try {
                // Create socket
                System.out.println("[SERVER] Waiting for client connection...");
                Socket newSocket = serverSocket.accept();
                System.out.println("[SERVER] Client connected to server");
                ClientHandler clientHandler = new ClientHandler(newSocket);
                clientHandler.run();
                clientHandler.pingToClient();
                clientHandler.readFromClient();

                addClientToLog(clientHandler);

            } catch (IOException e) {
                System.out.println("[SERVER] Restarting server...");
                return;
            }
        }
    }

    public void addClientToLog(ClientHandler client) {
        Thread t = new Thread(() -> {
            log.add(client);
            System.out.println("[SERVER] new log.size(): " + log.size());
            synchronized (lock) {
                lock.notifyAll();
            }
            System.out.println("[SERVER] client added in log");
            if (first) {
                System.out.println("[SERVER] First player");
                first = false;
                addLobby(new Lobby());
            }
        });
        t.start();
    }

    @Override
    public void updatePlayersNumber(Lobby lobby) {
        System.out.println("[SERVER] log.size() in updatePlayersNumber(): " + log.size());
        synchronized (lock) {
            System.out.println("[SERVER] log.size() in updatePlayersNumber(): " + log.size());
            while (log.size() == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ClientHandler client = log.get(0);
            log.remove(0);
            System.out.println("[SERVER] addClient()");
            lobby.addClient(client);
        }
    }

    @Override
    public void createNewLobby() {
        addLobby(new Lobby());
    }

    public void addLobby(Lobby lobby) {
        Thread t = new Thread(() -> {
            lobby.addObserver(this);
            System.out.println("[SERVER] log.size() in addLobby(): " + log.size());
            synchronized (lock) {
                System.out.println("[SERVER] log.size() in addLobby(): " + log.size());
                while (log.size() == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ClientHandler client = log.get(0);
                log.remove(0);
                System.out.println("[SERVER] addLobby()");
                lobby.addClient(client);
            }
        });
        t.start();
    }

}