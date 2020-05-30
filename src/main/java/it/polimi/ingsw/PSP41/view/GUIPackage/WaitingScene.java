package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Scene used to wait for other players'choices
 */
public class WaitingScene {
    private Pane root;

    public WaitingScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/waitingScene.fxml"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Pane getRoot() {
        return root;
    }
}
