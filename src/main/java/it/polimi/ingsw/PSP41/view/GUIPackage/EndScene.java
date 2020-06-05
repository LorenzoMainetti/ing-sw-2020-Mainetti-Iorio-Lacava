package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

/**
 * End scene with credits and thanks
 */
public class EndScene {
    private Pane root;

    public EndScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/endScene.fxml"));

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
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                fadeOut.play();

                fadeOut.setOnFinished((ev) -> {
                    Platform.exit();
                });
            }); 

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Pane getRoot() {
        return root;
    }
}

