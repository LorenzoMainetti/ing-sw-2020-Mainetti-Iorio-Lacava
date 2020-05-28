package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.client.NetworkHandler;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import it.polimi.ingsw.PSP41.view.View;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUI extends Application implements View {

    //variables sent from Server
    private int playersNumber = 0;
    private String challenger = null;
    private final List<PlayersInfoMessage> playersInfo = new ArrayList<>();
    private final List<String> players = new ArrayList<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private NetworkHandler networkHandler;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

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
            });

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        TransitionHandler.toLoadingScene();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Santorini Board Game");
        
        primaryStage.setOnCloseRequest(event -> {
            //TODO gestire disconnessione dopo chiusura finestra
            System.out.println("Disconnected.");
            Platform.exit();
        });

        primaryStage.show();


        //askPort e askView
        String ip = "127.0.0.1";
        String port = "9090";

        //TODO gestire conflitto javaFX thread e networkHandler

        networkHandler = new NetworkHandler(ip, port, this);

        executor.submit(networkHandler);
        //new Thread(networkHandler).start();
    }


    @Override
    public void askPlayersNumber() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
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

    }

    @Override
    public void askWorker() {

    }

    @Override
    public void askPowerActivation() {

    }

    @Override
    public void askPosition(List<Position> positions) {

    }

    @Override
    public void displayTakenNickname() {
        Platform.runLater(() -> new AlertPopup().display("ERROR: Nickname already taken. Try again"));
        askNickname();
    }

    @Override
    public void displayTakenPosition() {
        Platform.runLater(() -> new AlertPopup().display("ERROR: this position is occupied. Try again."));
        askInitPosition();
    }

    @Override
    public void displayNetworkError() {
        Platform.runLater(() -> new AlertPopup().display("ERROR: Connection closed from server side."));
    }

    @Override
    public void displayFullLobby() {
        Platform.runLater(() -> new AlertPopup().display("Sorry, the lobby is already full. Try again later."));
    }

    @Override
    public void waitPlayersNum() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
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

    }

    @Override
    public void addPlayersInfo(PlayersInfoMessage message) {
        playersInfo.add(message);
        players.add(message.getPlayerName());
    }

    @Override
    public void displayPlayersNumber(int number) {
        playersNumber = number;
    }

    @Override
    public void displayChallenger(String name) {
        challenger = name;
        Platform.runLater(() -> new AlertPopup().display(name.toUpperCase() + " is the most godlike! " + name.toUpperCase() + " is the challenger!"));
    }

    @Override
    public void displayCurrentPlayer(String name) {

    }

    @Override
    public void displayLoser(String name) {

    }

    @Override
    public void displayWinner(String name) {

    }

    @Override
    public void displayWrongTurn() {}

    @Override
    public void endTurn() {

    }

    @Override
    public void startMovePhase() {

    }

    @Override
    public void startBuildPhase() {

    }

}