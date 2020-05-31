package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Scene that asks for ip and port needed for connecting to server
 */
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


        okButton.setOnAction(event -> {
            if (ipText.getText() != null && portText.getText() != null) {

                //TODO inviare ipText.getText() e portText.getText() al server
                TransitionHandler.toNumberScene();
            } else
                new AlertPopup().display("Please enter a valid IP Address and Port Number.");
        });
    }

    public Pane getRoot() {
        return root;
    }
}
