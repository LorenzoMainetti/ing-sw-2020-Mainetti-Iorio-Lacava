package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Scene used to get the number of players for the match
 */
public class NumberScene extends UiObservable {
    private Pane root;
    private String number;

    public NumberScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/numberScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button okButton = (Button) root.lookup("#okButton");
        ChoiceBox choiceBox = (ChoiceBox) root.lookup("#choiceBox");
        choiceBox.getItems().add("2 Players");
        choiceBox.getItems().add("3 Players");

        choiceBox.setStyle("-fx-font-style: 'Avenir Book'");
        choiceBox.setStyle("-fx-font-size: 14;");
        choiceBox.setValue("2 Players");

        okButton.setOnAction(event -> {

            String str = (String) choiceBox.getValue();
            if(str.equals("2 Players"))
                number = "2";
            else
                number = "3";

            NumberScene.this.notify(number);
        });
    }

    public Pane getRoot() {
        return root;
    }
}
