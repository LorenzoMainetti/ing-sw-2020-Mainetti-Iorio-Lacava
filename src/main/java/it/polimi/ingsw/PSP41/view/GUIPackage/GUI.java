package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.client.NetworkHandler;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import it.polimi.ingsw.PSP41.view.View;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUI extends Application implements View {

    //variables sent from Server
    private int playersNumber = 0;
    private String clientName = null;
    private String challenger = null;
    private final List<PlayersInfoMessage> playersInfo = new ArrayList<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private NetworkHandler networkHandler;

    private GameScene gameScene;
    private boolean firstTime = true;
    private boolean emptyBoard = true;



    @Override
    public void start(Stage primaryStage) {

        networkHandler = new NetworkHandler(this);
        executor.submit(networkHandler);

        TransitionHandler.setPrimaryStage(primaryStage);

        try{
            Pane root = FXMLLoader.load(getClass().getResource("/SantoriniLogo.fxml"));

            Scene loadingScene = new Scene(root);
            TransitionHandler.setLoadingScene(loadingScene);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();
            fadeIn.setOnFinished((e) -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                fadeOut.play();
                askConnection();
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        TransitionHandler.toLoadingScene();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Santorini Board Game");

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Disconnected.");
            Platform.exit();
        });

        primaryStage.show();

    }


    private void askConnection() {
        //ask ip and port
        ConnectionScene connectionScene = new ConnectionScene();
        connectionScene.addObserver(networkHandler);
        Platform.runLater(() -> TransitionHandler.setConnectionScene(connectionScene));
        Platform.runLater(TransitionHandler::toConnectionScene);
    }

    @Override
    public void askPlayersNumber() {
        NumberScene numberScene = new NumberScene();
        numberScene.addObserver(networkHandler);
        Platform.runLater(() -> TransitionHandler.setNumberScene(numberScene));
        Platform.runLater(TransitionHandler::toNumberScene);
    }

    @Override
    public void askNickname() {
        LoginScene loginScene = new LoginScene();
        loginScene.addObserver(networkHandler);
        Platform.runLater(() -> TransitionHandler.setLoginScene(loginScene));
        Platform.runLater(TransitionHandler::toLoginScene);
    }

    @Override
    public void askGameGods(List<String> gods) {
        GodPowerScene godPowerScene = new GodPowerScene(playersNumber);
        godPowerScene.addObserver(networkHandler);
        Platform.runLater(() -> TransitionHandler.setGodPowerScene(godPowerScene));
        Platform.runLater(TransitionHandler::toGodPowerScene);
    }

    @Override
    public void askGodCard(List<String> gods) {
        CardChoiceScene cardChoiceScene = new CardChoiceScene(gods);
        cardChoiceScene.addObserver(networkHandler);
        Platform.runLater(() -> TransitionHandler.setCardChoiceScene(cardChoiceScene));
        Platform.runLater(TransitionHandler::toCardChoiceScene);
    }

    @Override
    public void askFirstPlayer(String name) {
        PlayerScene playerScene = new PlayerScene(playersInfo, challenger, name);
        playerScene.addObserver(networkHandler);
        Platform.runLater(() -> TransitionHandler.setPlayerScene(playerScene));
        Platform.runLater(TransitionHandler::toPlayerScene);
    }

    @Override
    public void askInitPosition() {
        Platform.runLater(() -> gameScene.askInitPosition());
    }

    @Override
    public void askWorker() {
        Platform.runLater(() -> gameScene.askWorker());
    }

    @Override
    public void askPowerActivation() {
        Platform.runLater(() -> new PowerPopup(networkHandler).display());
    }

    @Override
    public void askPosition(List<Position> positions) {
        Platform.runLater(() -> gameScene.askPosition(positions));
    }

    @Override
    public void displayTakenNickname() {
        Platform.runLater(() -> new AlertPopup().display("ERROR: Nickname already taken. Try again."));
        askNickname();
    }

    @Override
    public void displayTakenPosition() {
        //Platform.runLater(() -> new AlertPopup().display("ERROR: this position is occupied. Try again."));
        askInitPosition();
    }

    @Override
    public void displayNetworkError() {
        Platform.runLater(() -> new AlertPopup().display("ERROR: Connection closed from server side."));
        Platform.exit();
        System.exit(-1);
    }

    @Override
    public void displayFullLobby() {
        Platform.runLater(() -> new AlertPopup().display("Sorry, the lobby is already full. Try again later."));
    }

    @Override
    public void waitPlayersNum() {
        waiting();
    }

    @Override
    public void waiting() {
        WaitingScene waitingScene = new WaitingScene();
        Platform.runLater(() -> TransitionHandler.setWaitingScene(waitingScene));
        Platform.runLater(TransitionHandler::toWaitingScene);
    }

    @Override
    public void displayBoard(Board board) {
        //the first time the server send an emptyBoard
        if(emptyBoard)
            emptyBoard = false;
        else
            Platform.runLater(() -> gameScene.displayBoard(board));
    }

    @Override
    public void setClientName(String name) {
        clientName = name;
    }

    @Override
    public void addPlayersInfo(PlayersInfoMessage message) {
        playersInfo.add(message);
    }

    @Override
    public void displayPlayersNumber(int number) {
        playersNumber = number;
    }

    @Override
    public void displayChallenger(String name) {
        challenger = name;
        if(!clientName.equals(challenger))
            Platform.runLater(() -> new AlertPopup().display(name + " is the most godlike! " + name + " is the challenger!"));
    }

    @Override
    public void displayCurrentPlayer(String name) {
        //if it's the first time this method is called create the GameScene
        if(firstTime) {
            firstTime = false;
            gameScene = new GameScene(playersInfo, clientName);
            gameScene.addObserver(networkHandler);
            Platform.runLater(() -> TransitionHandler.setGameScene(gameScene));
            Platform.runLater(TransitionHandler::toGameScene);
        }

        Platform.runLater(() -> gameScene.displayCurrentPlayer(name));
    }

    @Override
    public void displayLoser(String name) {
        playersInfo.removeIf(message -> message.getPlayerName().equals(name));

        Platform.runLater(() -> gameScene.displayLoser(name));
    }

    @Override
    public void displayWinner(String name) {
        WinnerScene winnerScene = new WinnerScene(name);
        Platform.runLater(() -> TransitionHandler.setWinnerScene(winnerScene));
        Platform.runLater(TransitionHandler::toWinnerScene);
        EndScene endScene = new EndScene();
        Platform.runLater(() -> TransitionHandler.setEndScene(endScene));
    }

    @Override
    public void displayWrongTurn() {
        Platform.runLater(() -> new AlertPopup().display("It's not your turn."));
    }

    @Override
    public void startMovePhase() {
        Platform.runLater(() -> gameScene.startMovePhase());
    }

    @Override
    public void startBuildPhase() {
        Platform.runLater(() -> gameScene.startBuildPhase());
    }

    @Override
    public void stop() {
        System.exit(0);
    }

}