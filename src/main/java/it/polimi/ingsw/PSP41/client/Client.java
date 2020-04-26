package it.polimi.ingsw.PSP41.client;

import it.polimi.ingsw.PSP41.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private boolean active = true;
    private boolean myTurn = false;
    //istanza remote view (CLI)

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if(inputObject instanceof String){
                            if (((String) inputObject).equals("Turn start!") || ((String)inputObject).equals("You are the first player in the lobby, choose the number of players")) {
                                myTurn = true;
                            }
                            if(inputObject.equals("End turn") || inputObject.equals("Wait for the players to join...")) {
                                myTurn = false;
                            }
                            System.out.println((String)inputObject);

                        }
                        /*else if (inputObject instanceof Board){
                        deserialize();
                        printBoard();
                        }*/
                        else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner scanner, final PrintWriter socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        String inputLine = scanner.nextLine();
                        if(myTurn || inputLine.equals("Quit")) {
                            socketOut.println(inputLine);
                            socketOut.flush();
                        }
                    }
                } catch(Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

    public void run() throws IOException {
        // Connessione al server
        Scanner scanner = new Scanner(System.in);
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();
        /* open a connection to the server */
        Socket socket;
        try {
            socket = new Socket(ip, Server.PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connection established");

        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(scanner, socketOut);
            t0.join();
            t1.join();
        } catch(NoSuchElementException | InterruptedException e){
            System.out.println("Connection closed from the client side");
        } finally {
            scanner.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }

        /*while (true) {
            // Attendo messaggio dal server (dal client handler)
            String fromServer = socketIn.readLine();
            System.out.println(fromServer);

            if(socketIn.readLine().equals("question")) {
                String input = scanner.nextLine();
                if (input.equals("quit")) break;
                socketOut.println(input);
            }
        }*/

        socket.close();
        System.exit(0);
    }

}
