package it.polimi.ingsw.PSP41.client;

import it.polimi.ingsw.PSP41.observer.UiObserver;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.utils.*;
import it.polimi.ingsw.PSP41.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

public class NetworkHandler implements Runnable, UiObserver {

    Socket socket;
    ObjectInputStream socketIn;
    PrintWriter socketOut;
    private View view;

    public NetworkHandler(String ip, String port, View view) {
        try {
            socket = new Socket(ip, Integer.parseInt(port));
            this.view = view;

        } catch (IOException e) {
            System.out.println("Server unreachable");
            return;
        }
        System.out.println("Connection established");
    }

    /**
     * Server messages manager
     * @param inputObject message from server
     */
    private void manageInputFromServer(Object inputObject) {
        Thread t = new Thread(() -> {
            synchronized (this) {
                if (inputObject instanceof NameMessage) {

                    NameMessage message = (NameMessage) inputObject;
                    switch (message.getType()) {
                        case winMessage:
                            view.displayWinner(message.getName());
                            break;
                        case loseMessage:
                            view.displayLoser(message.getName());
                            break;
                        case godLikeMessage:
                            view.displayChallenger(message.getName());
                            break;
                        case chooseStarterMessage:
                            view.askFirstPlayer(message.getName());
                            break;
                        default:
                            view.displayCurrentPlayer(message.getName());
                            break;

                    }
                }

                else if (inputObject instanceof ChooseGodMessage) {
                    ChooseGodMessage message = (ChooseGodMessage) inputObject;
                    switch (message.getType()) {
                        case gameGodsMessage:
                            view.askGameGods(message.getGodList());
                            break;
                        case yourGodMessage:
                            view.askGodCard(message.getGodList());
                    }
                }

                else if (inputObject instanceof Board) {
                    view.displayBoard((Board) inputObject);
                }

                else if (inputObject instanceof PositionMessage) {
                    PositionMessage message = (PositionMessage) inputObject;
                    view.askPosition(message.getValidPos());
                }

                else if (inputObject instanceof PlayersInfoMessage) {
                    PlayersInfoMessage message = (PlayersInfoMessage) inputObject;
                    view.addPlayersInfo(message);
                }

                else if (inputObject instanceof Integer) {
                    view.displayPlayersNumber((Integer) inputObject);
                }

                else if (inputObject instanceof String) {
                    if (!inputObject.equals("")) {
                        String message = (String) inputObject;
                        switch (message) {
                            case playersNumMessage:
                                view.askPlayersNumber();
                                break;
                            case nicknameMessage:
                                view.askNickname();
                                break;
                            case takenNameMessage:
                                view.displayTakenNickname();
                                break;
                            case initPosMessage:
                                view.askInitPosition();
                                break;
                            case occupiedCellMessage:
                                view.displayTakenPosition();
                                break;
                            case workerNumMessage:
                                view.askWorker();
                                break;
                            case activatePowMessage:
                                view.askPowerActivation();
                                break;
                            case moveMessage:
                                view.startMovePhase();
                                break;
                            case buildMessage:
                                view.startBuildPhase();
                                break;
                            case waitMessage:
                                view.waiting();
                                break;
                            case fullLobby:
                                view.displayFullLobby();
                                break;
                            case wrongTurnMessage:
                                view.displayWrongTurn();
                                break;
                        }
                    }
                }

                else {
                    throw new IllegalArgumentException();
                }
            }

        });
        t.start();
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
            while (true) {
                try {
                    socket.setSoTimeout(5000);
                    Object inputObject = socketIn.readObject();
                    manageInputFromServer(inputObject);
                    // close socket if winMessage is received
                    if (inputObject instanceof NameMessage && ((NameMessage) inputObject).getType().equals(winMessage))
                        break;
                } catch (IOException | ClassNotFoundException e) {
                    view.displayNetworkError();
                    break;
                }
            }
        } catch(NoSuchElementException e) {
            view.displayNetworkError();
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
