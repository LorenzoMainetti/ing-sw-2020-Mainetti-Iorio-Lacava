package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.observer.LobbyObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static it.polimi.ingsw.PSP41.utils.GameMessage.waitPlayersNum;

public class Server implements Runnable, LobbyObserver {

    ServerSocket serverSocket;
    private final int PORT = 9090;
    //TODO might use a queue instead
    private final List<ClientHandler> clientsList = Collections.synchronizedList(new ArrayList<>());
    private boolean first = true;

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
                //Thread t = new Thread(clientHandler);
                //t.start();
                clientHandler.run();

                addClientToLog(clientHandler);

            } catch (IOException e) {
                System.out.println("[SERVER] Restarting server...");
                return;
            }
        }
    }

    private void addClientToLog(ClientHandler client) {
        synchronized (clientsList) {
            clientsList.add(client);
            //System.out.println("[SERVER] new log.size(): " + clientsList.size());
            System.out.println("[SERVER] client added in log");
            if (first) {
                first = false;
                createNewLobby();
            }
            else {
               client.send(waitPlayersNum);
            }
            clientsList.notifyAll();
        }
    }

    @Override
    public void updatePlayersNumber(Lobby lobby) {
        synchronized (clientsList) {
            while (clientsList.size() == 0) {
                try {
                    clientsList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ClientHandler client = clientsList.get(0);
            clientsList.remove(0);
            lobby.addClient(client);
        }
    }

    @Override
    public void createNewLobby() {
        Thread t = new Thread(() -> addLobby(new Lobby()));
        t.start();
    }

    public void addLobby(Lobby lobby) {
        lobby.addObserver(this);
        synchronized (clientsList) {
            //System.out.println("[SERVER] log.size() in addLobby(): " + clientsList.size());
            while (clientsList.size() == 0) {
                try {
                    clientsList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ClientHandler client = clientsList.get(0);
            clientsList.remove(0);
            lobby.addClient(client);
        }
    }

}