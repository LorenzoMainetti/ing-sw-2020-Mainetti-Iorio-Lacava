package it.polimi.ingsw.PSP41.view;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;

import java.util.List;

public interface View {

    //Get inputs from user
    //void askIP();
    //void askPort();

    void askPlayersNumber();

    void askNickname();

    void askGameGods(List<String> gods);

    void askGodCard(List<String> gods);

    void askFirstPlayer(String name);

    void askInitPosition();

    void askWorker();

    void askPowerActivation();

    void askPosition(List<Position> positions);

    //Show errors and re-ask input if needed
    void displayTakenNickname();

    void displayTakenPosition();

    void displayNetworkError();  //client-side

    void displayFullLobby();

    //Update User Interface
    void waitPlayersNum();

    void waiting();

    void displayBoard(Board board);

    void addPlayersInfo(PlayersInfoMessage message);

    void displayPlayersNumber(int number);

    void displayChallenger(String name);

    void displayCurrentPlayer(String name);

    void displayLoser(String name);

    void displayWinner(String name);

    void endTurn();

    void startMovePhase();

    void startBuildPhase();

}
