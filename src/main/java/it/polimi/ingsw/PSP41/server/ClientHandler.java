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
    private Lobby lobby; //NON FINAL ALTRIMENTI NON FUNZIONA NIENTE, INTELLIJ MENTE
    private int position;
    private boolean connected = true;
    private boolean active = true;
    private boolean myTurn = false;
    private String answer;
    private boolean answerReady = false;
    private ObjectOutputStream socketOut;
    private BufferedReader socketIn;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ClientHandler(Socket socket, Lobby lobby) {
        this.socket = socket;
        this.lobby = lobby;
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
            if (message.equals(startTurnMessage)) {
                myTurn = true;
            }
            else if (message.equals(endTurnMessage)) {
                myTurn = false;
            }
            else {
                socketOut.reset();
                socketOut.writeObject(message);
                socketOut.flush();
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error in method send #" + position + ": " + e.getMessage());
        }
    }

    /**
     * Closes connection with client
     */
    public void closeConnection() {
        try {
            connected = false;
            socket.close();
            System.out.println("Connection closed with client #" + position);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error in method close connection: " + e.getMessage());
        }
    }

    public void pingToClient() {
        Thread t = new Thread(() -> {
            while (connected) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    //socketOut.reset();
                    socketOut.writeObject("");
                    //socketOut.flush();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Loop read from client: when a message is read, answerReady is set true. If the client is unreachable, server is notified
     */
    public void readFromClient(){
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    socket.setSoTimeout(5000);
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
                            send(wrongTurnMessage);
                            System.out.println("[SERVER] Wrong turn message");
                        }
                    }
                } catch (IOException | NullPointerException | IllegalArgumentException e) {
                        System.out.println("[SERVER] Client #" + position + " unreachable");
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

    @Override
    public void run() {

        try {
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        pingToClient();
        readFromClient();

        if (position > 3) {
            send(fullLobby);
            active = false;
            closeConnection();
        }

        try {
            if (lobby.getPlayersNumber() == -1 || position <= lobby.getPlayersNumber()) {
                if (position <= 3) {
                    // Lobby creation
                    if (position == 1) {
                        lobby.setPlayersNumber(this);
                    }
                    else {
                        send(waitPlayersNum);
                        lobby.waitPlayersNumber(this);
                    }

                    lobby.setNickname(this);
                    lobby.setGodLike();
                }
            }
            else {
                send(fullLobby);
                setActive(false);
                closeConnection();
            }

        } catch (NoSuchElementException e) {
            System.err.println("[SERVER] Error!" + e.getMessage());
        }
    }
}