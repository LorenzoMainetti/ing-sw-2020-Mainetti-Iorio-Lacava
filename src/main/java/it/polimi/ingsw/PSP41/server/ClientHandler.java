package it.polimi.ingsw.PSP41.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class ClientHandler implements Runnable {

    private Socket socket;
    private int position;
    private boolean active;
    private boolean connected;
    private ObjectOutputStream socketOut;
    private BufferedReader socketIn;
    private static Lobby lobby = new Lobby();

    public int getPosition() {
        return position;
    }

    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public BufferedReader getSocketIn() {
        return socketIn;
    }

    public ClientHandler(Socket socket, int position) {
        this.socket = socket;
        this.position = position;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public synchronized void send(Object message) {
        try {
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public synchronized void closeConnection() throws IOException {
        socketOut.writeObject("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

/*
    private void close() {
        closeConnection();
        System.out.println("Unregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }
*/

    public void run() {

        try{
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new ObjectOutputStream(socket.getOutputStream());

            // Creazione lobby
            if (position == 1) {
                lobby.setPlayersNumber(this);
            }
            else {
                socketOut.writeObject("The lobby creator is choosing the number of players...");
                lobby.waitPlayersNumber(this);
            }

            lobby.setNickname(this);
            lobby.setGodLike();
            lobby.setGodCard(this);

            // TODO Gestione disconnessione client
            /*while(active && connected){
                if (socket.getInetAddress().isReachable(2000)) {
                    connected = false;
                }
            }*/
        } catch (NoSuchElementException | IOException e) {
            System.err.println("[SERVER] Error!" + e.getMessage());
        }
    }
}