package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends Application {


    @Override
    public void start(Stage primaryStage) {
        //serve per testare
        PlayersInfoMessage info = new PlayersInfoMessage("gine", Color.BLUE, "");
        List<Boolean> list = Arrays.asList(false, true, false, true, false, false, false, true, false, false, false, false, false, false);
        Map<String, Integer> playerCard = new HashMap<>();
        playerCard.put("gine", 0);
        playerCard.put("lore", 0);
        playerCard.put("marco", 0);
        PlayersInfoMessage gine = new PlayersInfoMessage("gine", Color.RED, "ARES");
        PlayersInfoMessage lore = new PlayersInfoMessage("lore", Color.YELLOW, "PAN");
        PlayersInfoMessage marco = new PlayersInfoMessage("marco", Color.BLUE, "HESTIA");
        List<PlayersInfoMessage> players = Arrays.asList(gine, lore, marco);
        String challenger = "gine";
        String currPlayer = "gine";

        TransitionHandler.setPrimaryStage(primaryStage);
        NumberScene numberScene = new NumberScene();
        TransitionHandler.setNumberScene(numberScene);
        LoginScene loginScene = new LoginScene();
        TransitionHandler.setLoginScene(loginScene);
        GodPowerScene godPowerScene = new GodPowerScene(2);
        TransitionHandler.setGodPowerScene(godPowerScene);
        WaitingScene waitingScene = new WaitingScene();
        TransitionHandler.setWaitingScene(waitingScene);
        CardChoiceScene cardChoiceScene = new CardChoiceScene(list, info, playerCard);
        TransitionHandler.setCardChoiceScene(cardChoiceScene);
        PlayerScene playerScene = new PlayerScene(players, challenger);
        TransitionHandler.setPlayerScene(playerScene);
        GameScene gameScene = new GameScene(players);
        TransitionHandler.setGameScene(gameScene);


        //il server deve chiamare updatePlayerChoice
        playerCard.replace("lore", 2);
        cardChoiceScene.updatePlayerChoice(playerCard);

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

            fadeOut.setOnFinished((e) -> {
                TransitionHandler.toGameScene();
            });

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




}
