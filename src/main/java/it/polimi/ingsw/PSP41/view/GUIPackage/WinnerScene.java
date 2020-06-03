package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class WinnerScene {
    private Pane root;

    public WinnerScene(String winner) {
        try {
            root = FXMLLoader.load(getClass().getResource("/winnerScene.fxml"));

        } catch (IOException e){
            e.printStackTrace();
        }

        Text text = (Text) root.lookup("#text");
        text.setText(winner.toUpperCase());
        ImageView button = (ImageView) root.lookup("nextButton");
        Text endText = (Text) root.lookup("nextText");

        button.setOnMouseEntered(event -> {
            button.setImage(new Image("/btn_blue_pressed.png"));
            endText.setTranslateY(2.0);
        });

        button.setOnMouseExited(event -> {
            button.setImage(new Image("/btn_blue.png"));
            endText.setTranslateY(-0.5);
        });

        //TODO: passare alla endScene
        //button.setOnMouseClicked(event -> TransitionHandler.toEndScene());

    }

    public Pane getRoot() {
        return root;
    }
}

