package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.observer.ConnectionObservable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

public class ClientHandler extends ConnectionObservable implements Runnable {

    private Socket socket;
    private Lobby lobby; // NON FINAL ALTRIMENTI NON FUNZIONA NIENTE
    private int position;
    private boolean active = true;
    private boolean myTurn = false;
    private String answer;
    private boolean answerReady = false;
    private ObjectOutputStream socketOut;
    private BufferedReader socketIn;

    public int getPosition() {
        return position;
    }

    public ClientHandler(Socket socket, Lobby lobby, int position) {
        this.socket = socket;
        this.lobby = lobby;
        this.position = position;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    /**
     * Message sender from server to client
     * @param message object sent to client
     */
    public void send(Object message) {
        try {
            if (message instanceof String) {
                if (message.equals(startTurnMessage)) {
                    myTurn = true;
                }
                if (message.equals(endTurnMessage)) {
                    myTurn = false;
                }
            }
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch (IOException | NullPointerException e) {
            System.err.println("Error in method send #" + position + ": " + e.getMessage());
        }
    }

    /**
     * Closes connection with client
     */
    public synchronized void closeConnection() {
        try {
            socket.close();
            System.out.println("Connection close with client #" + position);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error in method close connection: " + e.getMessage());
        }
    }

/*
    private void close() {
        closeConnection();
        System.out.println("Unregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }
*/

    /**
     * Loop read from client: when a message is read, sets answerReady true. If the client is unreachable, notifies the server
     */
    public void readFromClient(){
        Thread t = new Thread(() -> {
            // Altrimenti da problemi di sincronizzazione
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    String fromClient = socketIn.readLine();
                    if (!fromClient.equals("")) {
                        if (myTurn) {
                            //System.out.println("From client:" + answer);
                            answer = fromClient;
                            answerReady = true;
                            synchronized (this) {
                                notifyAll();
                            }
                        } else {
                            if (active) {
                                send(wrongTurnMessage);
                                System.out.println("[SERVER] Wrong turn message");
                            } else {
                                send(spectator);
                            }
                        }
                    }
                } catch (IOException | NullPointerException e) {
                        //System.out.println("[SERVER] Client #" + position + " unreachable");
                        notifyDisconnection(this);
                        break;
                }
            }
        });
        t.start();
    }

    /**
     * Return client message: waits until a message is received
     * @return client message
     */
    public String read() {
        synchronized (this) {
            while (!answerReady) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println("Read: " + answer);
        answerReady = false;
        return answer;
    }

    public void run() {

        try{
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new ObjectOutputStream(socket.getOutputStream());

            if (lobby.getPlayersNumber() == -1 || position <= lobby.getPlayersNumber()) {
                if (position <= 3) {
                    // Creazione lobby
                    if (position == 1) {
                        lobby.setPlayersNumber(this);
                    } else {
                        send("The lobby creator is choosing the number of players...");
                        lobby.waitPlayersNumber(this);
                    }

                    lobby.setNickname(this);
                    lobby.setGodLike(this);
                }
            }
            else {
                send(fullLobby);
                setActive(false);
                closeConnection();
            }

        } catch (NoSuchElementException | IOException e) {
            System.err.println("[SERVER] Error!" + e.getMessage());
        }
    }
}