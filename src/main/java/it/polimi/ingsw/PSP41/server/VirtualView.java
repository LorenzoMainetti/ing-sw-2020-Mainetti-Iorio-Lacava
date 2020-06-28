package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.observer.ModelObserver;
import it.polimi.ingsw.PSP41.observer.ViewObservable;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.utils.NameMessage;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import it.polimi.ingsw.PSP41.utils.PositionMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

public class VirtualView extends ViewObservable implements ModelObserver {

    private Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    String currPlayer;

    public void addClient(String name, ClientHandler client) {
        clients.put(name, client);
    }

    /**
     * Sets the current playing client
     * @param currPlayer current player
     */
    public void setCurrPlayer(String currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * Asks the nickname
     * @param currClient player addressee
     */
    public void requestNickname(ClientHandler currClient) {
        currClient.send(nicknameMessage);
        String nickname = currClient.read();
        notifyNickname(nickname);
    }

    /**
     * Sends an error message during the nickname choice
     * @param currClient player addressee
     */
    public void errorTakenNickname(ClientHandler currClient) {
        currClient.send(takenNameMessage);
        String nickname = currClient.read();
        notifyNickname(nickname);
    }

    /**
     * Asks the lobby size
     * @param currClient first player in the lobby
     */
    public void requestPlayersNum(ClientHandler currClient) {
        //send a message that when received by the client trigger askPlayersNumber method in View
        currClient.send(playersNumMessage);
        String message = currClient.read();

        // Server side check
        while (!message.equals("2") && !message.equals("3")) {
            currClient.send(playersNumMessage);
            message = currClient.read();
        }

        notifyPlayersNumber(Integer.parseInt(message));
    }

    /**
     * Asks workers' initial position
     */
    public void requestInitPos() {
        ClientHandler current = clients.get(currPlayer);
        current.send(initPosMessage);
        String message = current.read();
        int position = Integer.parseInt(message);

        notifyPosition(new Position(position/10, position%10));
    }

    /**
     * Sends an error message during the workers' initial position choice
     */
    public void errorTakenPosition() {
        clients.get(currPlayer).send(occupiedCellMessage);
    }

    /**
     * Asks the number of the worker to play with
     */
    public void requestWorkerNum() {
        clients.get(currPlayer).send(workerNumMessage);
        String chosenWorker = clients.get(currPlayer).read();
        /*
        while (Integer.parseInt(chosenWorker) != 1 && Integer.parseInt(chosenWorker) != 2) {
            clients.get(currPlayer).send("Invalid worker number, choose 1 or 2:");
            chosenWorker = clients.get(currPlayer).read();
        }
        */
        notifyWorker(Integer.parseInt(chosenWorker) == 1);
    }

    /**
     * Asks if the god power would be activated
     */
    public void requestActivatePow() {
        clients.get(currPlayer).send(activatePowMessage);
        String power = clients.get(currPlayer).read();
        /*
        while (!power.equals("yes") && !power.equals("no")) {
            clients.get(currPlayer).send("Invalid answer, choose yes or no:");
            power = clients.get(currPlayer).read();
        }
        */
        notifyPower(power.equals("yes"));
    }

    /**
     * Sends the available cells for the move or build and asks a cell
     * @param positionMessage available cells
     */
    public void requestPosition(PositionMessage positionMessage) {
        int row;
        int column;

        outside:
        do {
            ClientHandler current = clients.get(currPlayer);
            current.send(positionMessage);
            String message = current.read();
            row = Integer.parseInt(message) / 10;
            column = Integer.parseInt(message) % 10;

            if (row >= 0 && row <= 4 && column >= 0 && column <= 4) {
                for (Position position : positionMessage.getValidPos()) {
                    if (position.getPosRow() == row && position.getPosColumn() == column) {
                        break outside;
                    }
                }
            }
        } while(true);

        notifyPosition(new Position(row, column));
    }


    //OBSERVER

    /**
     * Sends the updated board to all the players
     * @param board
     */
    @Override
    public void updateState(Board board) {
        //send serialized board
        for (ClientHandler ch : clients.values()) {
            ch.send(board);
        }
    }

    /**
     * Sends players a win message with the winner nickname
     * @param winner winner player
     */
    @Override
    public void updateWinner(String winner) {
        System.out.println("[SERVER] The winner is " + winner);
        for (ClientHandler ch : clients.values()) {
            ch.send(new NameMessage(winMessage, winner));
        }
    }

    /**
     * Notifies players the loser player (stuck)
     * @param loser loser nickname
     */
    @Override
    public void updateLoser(String loser) {
        clients.get(loser).setActive(false);
        for (ClientHandler ch : clients.values()) {
            ch.send(new NameMessage(loseMessage, loser));
        }
    }

    /**
     * Sends players information
     * @param message nickname, color and god chosen by the player
     */
    public void sendPlayersInfo(PlayersInfoMessage message) {
        for (ClientHandler ch : clients.values()) {
            ch.send(message);
        }
    }


    //TURN:

    /**
     * Sends players the name of the current player
     */
    public void startTurn() {
        for (ClientHandler ch : clients.values()) {
            if(ch.equals(clients.get(currPlayer)))
                ch.send(startTurnMessage);
            ch.send(new NameMessage(currPlayer, currPlayer));
        }
    }

    /**
     * Sends a move message to the current player
     */
    public void movePhase() {
        clients.get(currPlayer).send(moveMessage);
    }

    /**
     * Sends a build message to the current player
     */
    public void buildPhase() {
        clients.get(currPlayer).send(buildMessage);
    }

    /**
     * Sends an end turn message to the current player
     */
    public void endTurn() {
        clients.get(currPlayer).send(endTurnMessage);
    }

    /**
     * Sends empty board
     * @param board empty board
     */
    public void emptyBoard(Board board) {
        for (ClientHandler client : clients.values()) {
            client.send(board);
        }
    }
}