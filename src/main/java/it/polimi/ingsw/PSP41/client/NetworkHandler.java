package it.polimi.ingsw.PSP41.client;

import it.polimi.ingsw.PSP41.ModelObservable;
import it.polimi.ingsw.PSP41.UiObserver;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.view.CLI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

public class NetworkHandler implements Runnable, UiObserver {

    Socket socket;
    ObjectInputStream socketIn;
    PrintWriter socketOut;
    private boolean active = true;
    private boolean myTurn = false;
    // Quando avrò anche la GUI, creerò un'interfaccia UserInterface da far implementare a CLI e GUI
    private CLI cli;

    public NetworkHandler(String ip, String port) {
        try {
            socket = new Socket(ip, Integer.parseInt(port));
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connection established");
    }

    public void setCli(CLI cli) {
        this.cli = cli;
    }

    public boolean isServerReachable() {
        try {
            return socket.getInetAddress().isReachable(2000);
        } catch (IOException e) {
            return false;
        }
    }

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public void manageInputFromServer(Object inputObject) {
        if (inputObject instanceof String) {
            if (inputObject.equals("Turn start!") ||
                    inputObject.equals("You are the first player in the lobby, choose the number of players")) {
                myTurn = true;
            }
            if (inputObject.equals("End turn") || inputObject.equals("Wait for the players to join...")) {
                myTurn = false;
            }
            System.out.println((String)inputObject);
        }
        else if (inputObject instanceof Board) {
            cli.printBoard((Board) inputObject);
        }

        else {
            throw new IllegalArgumentException();
        }
    }

    public void run() {

        try {
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            while (active) {
                try {
                    Object inputObject = socketIn.readObject();
                    manageInputFromServer(inputObject);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch(NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            try {
                socketIn.close();
                socketOut.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    @Override
    public void update(String inputLine) {
        if (inputLine.equals("Quit")) {
            try {
                active = false;
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (myTurn) {
            socketOut.println(inputLine);
            socketOut.flush();
        }
    }

}
