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
    private final List<ClientHandler> clientsList = Collections.synchronizedList(new ArrayList<>());
    private boolean first = true;

    /**
     * Manages clients reception
     */
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

                addClientToList(clientHandler);

            } catch (IOException e) {
                System.out.println("[SERVER] Restarting server...");
                return;
            }
        }
    }

    /**
     * Adds client to the server's list: if the client is the first connected, creates a new lobby
     * @param client current client
     */
    private void addClientToList(ClientHandler client) {
        synchronized (clientsList) {
            clientsList.add(client);
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

    /**
     * Takes the first client from the server's list and adds it to the clients list of the lobby that called the method
     * @param lobby lobby that called the method
     */
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

    /**
     * Starts a new lobby on a new thread
     */
    @Override
    public void createNewLobby() {
        Thread t = new Thread(() -> addLobby(new Lobby()));
        t.start();
    }

    /**
     * Takes the first client from server's list and adds it to the clients list of the lobby just created
     * @param lobby lobby just created
     */
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