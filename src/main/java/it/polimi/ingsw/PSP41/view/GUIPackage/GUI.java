package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.client.NetworkHandler;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
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

public class GUI extends Application implements View {
    private PlayersInfoMessage info, gine, lore, marco;
    private int playersNumber = 0;
    private List<PlayersInfoMessage> playersInfo = new ArrayList<>();
    private List<String> players = new ArrayList<>();
    private NetworkHandler networkHandler;


    @Override
    public void start(Stage primaryStage) {

        networkHandler = new NetworkHandler("127.0.0.1", "9090", this);
        new Thread(networkHandler).start();

        //serve per testare
        info = new PlayersInfoMessage("gine", Color.BLUE, "");
        List<Boolean> list = Arrays.asList(false, true, false, true, false, false, false, true, false, false, false, false, false, false);
        Map<String, Integer> playerCard = new HashMap<>();
        playerCard.put("gine", 0);
        playerCard.put("lore", 0);
        playerCard.put("marco", 0);
        gine = new PlayersInfoMessage("gine", Color.RED, "ARES");
        lore = new PlayersInfoMessage("lore", Color.YELLOW, "PAN");
        marco = new PlayersInfoMessage("marco", Color.BLUE, "HESTIA");
        List<PlayersInfoMessage> players = Arrays.asList(gine, lore, marco);
        String challenger = "gine";

        TransitionHandler.setPrimaryStage(primaryStage);

        /*TransitionHandler.setLoginScene(loginScene);
        GodPowerScene godPowerScene = new GodPowerScene(2);
        TransitionHandler.setGodPowerScene(godPowerScene);
        WaitingScene waitingScene = new WaitingScene();
        TransitionHandler.setWaitingScene(waitingScene);
        CardChoiceScene cardChoiceScene = new CardChoiceScene(list, info, playerCard);
        TransitionHandler.setCardChoiceScene(cardChoiceScene);
        PlayerScene playerScene = new PlayerScene(players, challenger);
        TransitionHandler.setPlayerScene(playerScene);
        //GameScene gameScene = new GameScene();
        //TransitionHandler.setGameScene(gameScene);


        //il server deve chiamare updatePlayerChoice
        playerCard.replace("lore", 2);
        cardChoiceScene.updatePlayerChoice(playerCard);*/

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

            //fadeOut.setOnFinished((e) -> TransitionHandler.toLoginScene());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        TransitionHandler.toLoadingScene();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Santorini Board Game");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event){
                //TODO gestire disconnessione dopo chiusura finestra
                System.out.println("Disconnected."); 
                Platform.exit();
            }

        });
        primaryStage.show();

        //TODO se il giocatore è il primo, popup che chiede il numero di giocatori
    }


    @Override
    public void askPlayersNumber() {

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
        Platform.runLater(TransitionHandler::toGodPowerScene);
    }

    @Override
    public void askGodCard(List<String> gods) {

    }

    @Override
    public void askFirstPlayer() {

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

    }

    @Override
    public void displayTakenPosition() {

    }

    @Override
    public void displayNetworkError() {

    }

    @Override
    public void displayFullLobby() {

    }

    @Override
    public void waiting() {

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
    public void endTurn() {

    }

    @Override
    public void startMovePhase() {

    }

    @Override
    public void startBuildPhase() {

    }

}
