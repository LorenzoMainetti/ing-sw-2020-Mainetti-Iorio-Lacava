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

    private boolean active = true;

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

    /*private synchronized void send(Object message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }*/

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

    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            public void run() {
                send(message);
            }
        }).start();
    }
*/
    public void run() {

        try{

            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new ObjectOutputStream(socket.getOutputStream());

            // Creazione lobby
            Lobby lobby = new Lobby();
            if (position == 1) {
                lobby.setPlayersNumber(this);
            }
            else {
                socketOut.writeObject("The lobby creator is choosing the number of players...");
            }
            // Mentre il primo giocatore non ha scelto il numero di giocatori, aspetto
            while (Lobby.getPlayersNumber() == 0) {
                Thread.sleep(1000);
            }

            if (position > Lobby.getPlayersNumber()) {
                socketOut.writeObject("The lobby is full!");
                closeConnection();
            }
            else {
                lobby.setGodLike(this);
                /*lobby.addPlayer(this Per gestire posizionamento worker: r1, c1, r2, c2??);*/
            }

            // DA IMPLEMENTARE: SELEZIONE POSIZIONE INIZIALE DEI WORKER (DA FARE QUI?)

            socketIn.readLine();

            // Creazione lobby
            lobby.addPlayer(this /*Per gestire posizionamento worker: r1, c1, r2, c2??*/);

            /*while (Lobby2.godLock == 1) {
                socketOut.writeObject("Wait for other players to choose their god");
                wait();
            }
            lobby.selectGodPower(this, Lobby2.nickname);*/

            // Ricezione prima risposta del giocatore (da lobby) e successive (dalla view)
            /*while(isActive()){
                socketOut.writeObject("Loop");
                read = socketIn.readLine();
                // Notifico la removeView del messaggio del giocatore (che lo serializzerà)
                notify(read);
            }*/
        } catch (NoSuchElementException | IOException | InterruptedException e) {
            System.err.println("[SERVER] Error!" + e.getMessage());
        } /*finally{
            close();
        }*/
    }
}