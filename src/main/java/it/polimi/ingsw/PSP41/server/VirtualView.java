package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.observer.ModelObserver;
import it.polimi.ingsw.PSP41.observer.ViewObservable;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import it.polimi.ingsw.PSP41.utils.PositionMessage;
import it.polimi.ingsw.PSP41.view.ColorCLI;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

//TODO VirtualView e RemoteView implementano la stessa interfaccia View
public class VirtualView extends ViewObservable implements ModelObserver {

    private Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    String currPlayer;

    public void addClient(String name, ClientHandler client) {
        clients.put(name, client);
    }

    /**
     * Set the current playing client
     * @param currPlayer current player
     */
    public void setCurrPlayer(String currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * ask the nickname
     * @param currClient player addressee
     */
    public void requestNickname(ClientHandler currClient) {
        currClient.send(nicknameMessage);
        String nickname = currClient.read();
        notifyNickname(nickname);
    }

    public void errorTakenNickname(ClientHandler currClient) {
        currClient.send(takenNameMessage);
        requestNickname(currClient);
    }

    /**
     * ask the lobby size
     * @param currClient player addresse (first player in the lobby)
     */
    public void requestPlayersNum(ClientHandler currClient) {
        //send a message that when received by the client trigger askPlayersNumber method in CLI
        currClient.send(playersNumMessage);
        String message = currClient.read();

        while (!message.equals("2") && !message.equals("3")) {
            currClient.send("Sorry, we support only a 2 or 3 players game.");
            message = currClient.read();
        }

        notifyPlayersNumber(Integer.parseInt(message));
    }

    /**
     * Ask workers' initial position
     */
    public void requestInitPos() {
        ClientHandler current = clients.get(currPlayer);
        current.send(initPosMessage);
        int row = askRow(current);
        int column = askColumn(current);
        notifyPosition(new Position(row, column));
    }


    /**
     * Ask row and get the input
     * @param current player addressee
     * @return chosen row
     */
    private int askRow(ClientHandler current) {
        current.send("Row:");
        String currentRow = current.read();

        while (Integer.parseInt(currentRow) < 0 || Integer.parseInt(currentRow) > 4) {
            current.send("Invalid row. Choose a number between 0 and 4:");
            currentRow = current.read();
        }

        return Integer.parseInt(currentRow);
    }


    /**
     * Ask column to the Player and get the input
     * @param current player addressee
     * @return chosen column
     */
    private int askColumn(ClientHandler current) {
        current.send("Column:");
        String currentColumn = current.read();

        while (Integer.parseInt(currentColumn) < 0 || Integer.parseInt(currentColumn) > 4) {
            current.send("Invalid column. Choose a number between 0 and 4:");
            currentColumn = current.read();
        }

        return Integer.parseInt(currentColumn);
    }

    /**
     * Ask the number of the worker to play with
     */
    public void requestWorkerNum() {
        clients.get(currPlayer).send(workerNumMessage);
        String chosenWorker = clients.get(currPlayer).read();

        while (Integer.parseInt(chosenWorker) != 1 && Integer.parseInt(chosenWorker) != 2) {
            clients.get(currPlayer).send("Invalid worker number, choose 1 or 2:");
            chosenWorker = clients.get(currPlayer).read();
        }

        notifyWorker(Integer.parseInt(chosenWorker) == 1);
    }

    /**
     * Ask if the power would be activated
     */
    public void requestActivatePow() {
        clients.get(currPlayer).send(activatePowMessage);
        String power = clients.get(currPlayer).read();

        while (!power.equals("yes") && !power.equals("no")) {
            clients.get(currPlayer).send("Invalid answer, choose yes or no:");
            power = clients.get(currPlayer).read();
        }

        notifyPower(power.equals("yes"));
    }

    /**
     * Send the available cells for the move or build and ask a cell
     * @param positionMessage available cells
     */
    public void requestPosition(PositionMessage positionMessage) {
        ClientHandler current = clients.get(currPlayer);
        current.send(positionMessage);
        int row = askRow(current);
        int column = askColumn(current);
        boolean valid = false;
        for (Position position : positionMessage.getValidPos()) {
            if (position.getPosRow() == row && position.getPosColumn() == column) {
                valid = true;
                break;
            }
        }

        while (!valid) {
            current.send("The cell is not valid");
            current.send(positionMessage);
            row = askRow(current);
            column = askColumn(current);
            for (Position position : positionMessage.getValidPos()) {
                if (position.getPosRow() == row && position.getPosColumn() == column) {
                    valid = true;
                    break;
                }
            }
        }
        notifyPosition(new Position(row, column));
    }

    public void errorTakenPosition() {
        clients.get(currPlayer).send(occupiedCellMessage);
    }

    //OBSERVER

    @Override
    public void updateState(Board board) {
        //manda sul socket la board serializzata
        for (ClientHandler ch : clients.values()) {
            ch.send(board);
        }
    }

    /**
     * Send players a win message with the winner nickname
     * @param winner winner nickname
     */
    @Override
    public void updateWinner(String winner) {
        //manda sul socket il nome del giocatore vincente. Conoscendo il nickname univoco
        //posso mandare messaggi personalizzati a ciascun client
        for (String ch : clients.keySet()) {
            if(ch.equals(winner)) {
                clients.get(ch).send(winMessage);
            }
            else {
                clients.get(ch).send("Game over! The winner is " + ColorCLI.ANSI_GREEN + winner.toUpperCase() + ColorCLI.RESET + "!");
            }
            clients.get(ch).send("Thanks for playing!");
        }

    }

    /**
     * Notifies players a player who lost
     * @param loser loser nickname
     */
    @Override
    public void updateLoser(String loser) {
        //manda sul socket il nome del giocatore perdente. Conoscendo il nickname univoco
        //posso mandare messaggi personalizzati a ciascun client
        for (String ch : clients.keySet()) {
            if(ch.equals(loser)) {
                clients.get(ch).send(loseMessage);
                clients.get(loser).setActive(false);
            }
            else
                clients.get(ch).send(loser.toUpperCase()+"'s workers are both stuck. His/Her workers will be removed.\n");
        }

    }

    //SET UP

    public void requestInfo(ClientHandler currClient) {
        currClient.send(infoMessage);
    }

    /**
     * Send players information
     * @param message nickname, color and god chosen by the player
     */
    public void sendPlayersInfo(PlayersInfoMessage message) {
        for (ClientHandler ch : clients.values()) {
            ch.send(message);
        }
    }


    //TURN:
    public void startTurn() {
        for (ClientHandler ch : clients.values()) {
            if (clients.get(currPlayer).equals(ch)) {
                ch.send(startTurnMessage);
                ch.send("Turn starts!");
            }
            else
                ch.send("It's " + currPlayer + "'s turn!");
        }
    }

    public void movePhase() {
        clients.get(currPlayer).send(moveMessage);
    }

    public void buildPhase() {
        clients.get(currPlayer).send(buildMessage);
    }

    public void endTurn() {
        clients.get(currPlayer).send(endTurnMessage);
    }

    /**
     * Prints empty board
     * @param board
     */
    public void emptyBoard(Board board) {
        for (ClientHandler client : clients.values()) {
            client.send(board);
        }
    }
}