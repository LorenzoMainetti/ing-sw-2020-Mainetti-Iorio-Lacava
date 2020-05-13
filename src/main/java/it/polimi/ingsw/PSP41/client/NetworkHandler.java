package it.polimi.ingsw.PSP41.client;

import it.polimi.ingsw.PSP41.observer.UiObserver;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import it.polimi.ingsw.PSP41.utils.PositionMessage;
import it.polimi.ingsw.PSP41.view.CLI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

public class NetworkHandler implements Runnable, UiObserver {

    Socket socket;
    ObjectInputStream socketIn;
    PrintWriter socketOut;
    private boolean active = true;
    List<PlayersInfoMessage> playersInfo = new ArrayList<>();
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

    /**
     * Server messages manager
     * @param inputObject
     */
    public synchronized void manageInputFromServer(Object inputObject) {
        if (inputObject instanceof String) {
            /*if (inputObject.equals(startTurnMessage)) {
                cli.printMessage((String) inputObject);
            }
            else if (inputObject.equals(endTurnMessage) || inputObject.equals("Wait for the players to join...")) {
                cli.printMessage((String) inputObject);
            }
            else if(inputObject.equals(playersNumMessage))
                cli.askPlayersNumber(); ok
            else if (inputObject.equals(initPosMessage))
                cli.askInitialPosition(); ok
            else if(inputObject.equals(nicknameMessage))
                cli.askNickname(); ok
            else if (inputObject.equals(infoMessage))
                cli.askClient(); ok
            else if(inputObject.equals(workerNumMessage))
                cli.askWorker(); ok
            else if(inputObject.equals(activatePowMessage))
                cli.askPowerActivation(); ok
            }
            else*/
            if (!inputObject.equals(startTurnMessage) && !inputObject.equals(endTurnMessage)) {
                cli.printMessage((String) inputObject);
            }

        }
        else if (inputObject instanceof Board) {
            for(PlayersInfoMessage message : playersInfo)
                cli.showPlayersInfo(message.getPlayerName(), message.getPlayerColor(), message.getGodName());
            cli.printBoard((Board) inputObject);
        }

        else if (inputObject instanceof PositionMessage) {
            PositionMessage message = (PositionMessage) inputObject;
            cli.displayOptions(message.getValidPos());
        }

        else if (inputObject instanceof PlayersInfoMessage) {
            PlayersInfoMessage message = (PlayersInfoMessage) inputObject;
            playersInfo.add(message);
            cli.showPlayersInfo(message.getPlayerName(), message.getPlayerColor(), message.getGodName());

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
                    // chiudo socket se ricevo winMessage
                    if (inputObject.equals(winMessage)) break;
                } catch (IOException | ClassNotFoundException e) {
                    cli.printMessage("Connection closed from server side");
                    break;
                }
            }
        } catch(NoSuchElementException e) {
            System.out.println("Connection closed from client side");
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
        socketOut.println(inputLine);
        socketOut.flush();
    }

}
