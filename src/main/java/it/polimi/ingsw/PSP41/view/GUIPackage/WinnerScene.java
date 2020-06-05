package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Scene used to display the winner of the game
 */
public class WinnerScene {
    private Pane root;

    public WinnerScene(String winner) {
        try {
            root = FXMLLoader.load(getClass().getResource("/winnerScene.fxml"));

        } catch (IOException e){
            e.printStackTrace();
        }

        Text text = (Text) root.lookup("#text");
        Text endText = (Text) root.lookup("#endText");
        ImageView button = (ImageView) root.lookup("#button");

        text.setText(winner.toUpperCase());
        endText.setMouseTransparent(true);

        button.setOnMouseEntered(event -> {
            button.setImage(new Image("/btn_blue_pressed.png"));
            endText.setTranslateY(2.0);
        });

        button.setOnMouseExited(event -> {
            button.setImage(new Image("/btn_blue.png"));
            endText.setTranslateY(-0.5);
        });

        button.setOnMouseClicked(event -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeOut.play();

            fadeOut.setOnFinished((e) -> {
                EndScene endScene = new EndScene();
                TransitionHandler.setEndScene(endScene);
                TransitionHandler.toEndScene();
            });
        });

    }

    public Pane getRoot() {
        return root;
    }
}

