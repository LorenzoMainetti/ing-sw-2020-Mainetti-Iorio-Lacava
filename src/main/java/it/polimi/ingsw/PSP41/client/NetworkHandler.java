package it.polimi.ingsw.PSP41.client;

import it.polimi.ingsw.PSP41.observer.UiObserver;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.utils.*;
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

    public NetworkHandler(String ip, String port, CLI cli) {
        try {
            socket = new Socket(ip, Integer.parseInt(port));
            this.cli = cli;
            cli.addObserver(this);
        } catch (IOException e) {
            System.out.println("Server unreachable");
            return;
        }
        System.out.println("Connection established");
    }

    public void setCli(CLI cli) {
        this.cli = cli;
    }

    /**
     * Server messages manager
     * @param inputObject message from server
     */
    public synchronized void manageInputFromServer(Object inputObject) {
        if (inputObject instanceof String) {
            if (!inputObject.equals("")) {
                if (inputObject.equals(playersNumMessage)) {
                    cli.askPlayersNumber();
                }
                else if(inputObject.equals(nicknameMessage)) {
                    cli.askNickname();
                }
                else if (inputObject.equals(takenNameMessage)) {
                    cli.displayTakenNickname();
                }
                else if (inputObject.equals(initPosMessage)) {
                    cli.askInitPosition();
                }
                else if (inputObject.equals(occupiedCellMessage)) {
                    cli.displayTakenPosition();
                }
                else if (inputObject.equals(workerNumMessage)) {
                    cli.askWorker();
                }
                else if (inputObject.equals(activatePowMessage)) {
                    cli.askPowerActivation();
                }
                else if (inputObject.equals(moveMessage)) {
                    cli.startMovePhase();
                }
                else if (inputObject.equals(buildMessage)) {
                    cli.startBuildPhase();
                }
                else if (inputObject.equals(endTurn)) {
                    cli.endTurn();
                }
            }

        }

        else if (inputObject instanceof PlayerMessage) {
            for (PlayersInfoMessage info : playersInfo) {
                if (info.getPlayerName().equals(((PlayerMessage) inputObject).getPlayer())) {
                    if (((PlayerMessage) inputObject).getType().equals(winMessage)) {
                        cli.displayWinner(info);
                    } else if (((PlayerMessage) inputObject).getType().equals(loseMessage)) {
                        cli.displayLoser(info);
                    } else {
                        cli.displayCurrentPlayer(info);
                    }
                    break;
                }
            }
        }

        else if (inputObject instanceof Board) {
            System.out.println("\n");
            for(PlayersInfoMessage message : playersInfo) {
                cli.showPlayersInfo(message.getPlayerName(), message.getPlayerColor(), message.getGodName());
            }
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

    public void pingToServer() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socketOut.println("");
            }
        });
        t.start();
    }

    public void run() {

        try {
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(this::pingToServer).start();

        try{
            while (active) {
                try {
                    socket.setSoTimeout(5000);
                    Object inputObject = socketIn.readObject();
                    manageInputFromServer(inputObject);
                    // chiudo socket se ricevo winMessage
                    if (inputObject instanceof PlayerMessage && ((PlayerMessage) inputObject).getType().equals(winMessage)) break;
                } catch (IOException | ClassNotFoundException e) {
                    cli.displayNetworkError();
                    break;
                }
            }
        } catch(NoSuchElementException e) {
            cli.displayNetworkError();
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
