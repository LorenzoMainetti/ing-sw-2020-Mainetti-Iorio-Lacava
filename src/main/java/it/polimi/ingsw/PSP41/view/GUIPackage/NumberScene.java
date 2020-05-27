package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class NumberScene {
    private Pane root;
    private Integer number;

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

        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                String str = (String) choiceBox.getValue();
                if(str.equals("2 Players"))
                    number = 2;
                else
                    number = 3;

                //TODO inviare al server la scelta
                TransitionHandler.toLoginScene();
            }
        });
    }

    public Pane getRoot() {
        return root;
    }
}
