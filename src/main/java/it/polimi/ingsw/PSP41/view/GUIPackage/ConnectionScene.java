package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Scene that asks for ip and port needed to connect to the server
 */
public class ConnectionScene extends UiObservable {
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
                notifyConnection(ipText.getText(), portText.getText());
            } else
                new AlertPopup().display("Please enter a valid IP Address and Port Number.");
        });
    }

    public Pane getRoot() {
        return root;
    }
}
