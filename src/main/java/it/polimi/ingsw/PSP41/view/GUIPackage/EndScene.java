package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EndScene {
    private Pane root;

    public EndScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/endScene.fxml"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Pane getRoot() {
        return root;
    }
}

