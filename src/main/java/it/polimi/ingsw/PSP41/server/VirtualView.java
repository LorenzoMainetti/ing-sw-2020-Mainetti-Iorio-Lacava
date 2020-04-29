package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.ModelObserver;
import it.polimi.ingsw.PSP41.ViewObservable;
import it.polimi.ingsw.PSP41.model.Board;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static it.polimi.ingsw.PSP41.GameMessage.*;

public class VirtualView extends ViewObservable implements ModelObserver {
    private Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    String currPlayer;

    public void addClient(String name, ClientHandler client) {
        clients.put(name, client);
    }

    public void setCurrPlayer(String currPlayer) {
        this.currPlayer = currPlayer;
    }


    //TODO:  deserialize messages from Clients and notify inputs to UIM

    /*public void requestNickname(ClientHandler currClient) {
        try {
            String nickname = currClient.getSocketIn().readLine();
            //notifyNickname(nickname);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestPlayersNum(ClientHandler currClient) {
        //send a message that when received by the client trigger askPlayersNumber method in CLI
        currClient.asyncSend(playersNumMessage);
        try {
            String message = currClient.getSocketIn().readLine();
            int playersNum = Integer.parseInt(message);
            //notifyPlayersNumber(playersNum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void requestInitPos() {
        //MODIFICHE TEMP PER TESTING
        //posso mandarlo a tutti i clients
        //clients.get(currPlayer).asyncSend(initPosMessage);
        try {
            //clients.get(currPlayer).getSocketOut().writeObject("Turn start!");
            //clients.get(currPlayer).getSocketOut().writeObject("ROW: ");
            clients.get(currPlayer).send("Row: ");
            String row = clients.get(currPlayer).getSocketIn().readLine();
            //clients.get(currPlayer).getSocketOut().writeObject("COL: ");
            clients.get(currPlayer).send("Col: ");
            String col = clients.get(currPlayer).getSocketIn().readLine();

            notifyPosition(new Position(Integer.parseInt(row), Integer.parseInt(col)));

            //int initPos = Integer.parseInt(message);
            //notifyPosition(new Position(initPos / 10, initPos % 10));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestWorkerNum() {
        clients.get(currPlayer).send(workerNumMessage);
        try {
            String message = clients.get(currPlayer).getSocketIn().readLine();
            int workerNum = Integer.parseInt(message);
            notifyWorker(workerNum == 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestActivatePow() {
        clients.get(currPlayer).send(activatePowMessage);
        try {
            String message = clients.get(currPlayer).getSocketIn().readLine();
            notifyPower(message.equals("yes"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public void requestPosition(PositionMessage positionMessage) {
        clients.get(currPlayer).asyncSend(positionMessage);
        try {
            String message = clients.get(currPlayer).getSocketIn().readLine();
            int initPos = Integer.parseInt(message);
            notifyPosition(new Position(initPos / 10, initPos % 10));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    //TODO:  serialize Strings and Objects into messages to be sent to Clients
    //OBSERVER

    @Override
    public void updateState(Board board) {
        //manda sul socket la board serializzata
        for (ClientHandler ch : clients.values()) {
            ch.send(board);
        }
    }

    @Override
    public void updateWinner(String winner) {
        //manda sul socket il nome del giocatore vincente. Conoscendo il nickname univoco
        //posso mandare messaggi personalizzati a ciascun client
        for (String ch : clients.keySet()) {
            if(ch.equals(winner))
                clients.get(ch).send(winMessage);
            else
                clients.get(ch).send("Game over! The winner is "+ winner.toUpperCase() +"!!!\nThanks for playing.");
        }

    }

    @Override
    public void updateLoser(String loser) {
        //manda sul socket il nome del giocatore perdente. Conoscendo il nickname univoco
        //posso mandare messaggi personalizzati a ciascun client
        for (String ch : clients.keySet()) {
            if(ch.equals(loser))
                clients.get(ch).send(loseMessage);
            else
                clients.get(ch).send(loser.toUpperCase()+"'s workers are both stuck. His/Her workers will be removed.\n");
        }

    }


    //SET UP
    public void sendGodName(String godName) {
        clients.get(currPlayer).send(godName);
        //send a message that when received by the client trigger printGodPower method in CLI
    }


    //TURN:
    public void startTurn() {
        for (ClientHandler ch : clients.values()) {
            if (clients.get(currPlayer).equals(ch)) {
                ch.send(startTurnMessage);
            }
            else
                ch.send("Wait! " + currPlayer + " is playing");
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

    //HANDLE ERRORS:

    /*public void errorTakenNickname(String nickName) {
        if(clients.containsKey(nickName))
            try {
            clients.get(nickName).getSocketOut().writeObject(takenNameMessage);
                clients.get(nickName).getSocketOut().flush();
            } catch(IOException e){
                System.err.println(e.getMessage());
            }
        //send an error message that when received by the client trigger nicknameTaken method in CLI
    }*/

    public void errorTakenPosition() {
        clients.get(currPlayer).send(occupiedCellMessage);
        //send an error message that when received by the client trigger positionTaken method in CLI
    }

}