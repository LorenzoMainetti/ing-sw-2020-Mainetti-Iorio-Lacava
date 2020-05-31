package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ConnectionScene {
    private Pane root;

    public ConnectionScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/connectionScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        TextField ipText = (TextField) root.lookup("#IPText");
        TextField portText = (TextField) root.lookup("#portText");
        Button okButton = (Button) root.lookup("#okButton");
        ipText.setText(null);
        portText.setText(null);


        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ipText.getText() != null && portText.getText() != null) {

                    //TODO inviare ipText.getText() e portText.getText() al server
                    TransitionHandler.toNumberScene();
                } else
                    new AlertPopup().display("Please enter a valid IP Address and Port Number.");
            }
        });
    }

    public Pane getRoot() {
        return root;
    }
}
