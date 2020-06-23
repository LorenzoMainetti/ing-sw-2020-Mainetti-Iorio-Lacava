package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.observer.ConnectionObservable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

public class ClientHandler extends ConnectionObservable {

    private Socket socket;
    private boolean connected = true;
    private boolean active = true;
    private boolean myTurn = false;
    private String answer;
    private boolean answerReady = false;
    private ObjectOutputStream socketOut;
    private BufferedReader socketIn;

    public ClientHandler(Socket socket) {
        this.socket = socket;
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
            e.printStackTrace();
        }
    }

    /**
     * Closes connection with client
     */
    public synchronized void closeConnection() {
        try {
            connected = false;
            socket.close();
            System.out.println("[SERVER] Connection closed with client");
        } catch (IOException e) {
            e.printStackTrace();
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
                    socketOut.writeObject("");
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
    public void readFromClient() {
        Thread t = new Thread(() -> {
            while (connected) {
                try {
                    socket.setSoTimeout(5000);
                    String fromClient = socketIn.readLine();
                    if (!fromClient.equals("")) {
                        if (myTurn) {
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
                    System.out.println("[SERVER] Client unreachable");
                    //e.printStackTrace();
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
        answerReady = false;
        return answer;
    }

    public void run() {
        try {
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        readFromClient();
        pingToClient();
    }

}