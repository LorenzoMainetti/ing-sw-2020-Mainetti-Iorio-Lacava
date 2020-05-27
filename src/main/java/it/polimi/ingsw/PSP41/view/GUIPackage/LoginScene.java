package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.observer.UiObservable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


import java.io.IOException;


public class LoginScene extends UiObservable {
    private Pane root;

    public LoginScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/loginScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button loginButton = (Button) root.lookup("#loginButton");
        TextField nicknameTextField = (TextField) root.lookup("#nicknameBox");
        nicknameTextField.setText(null);

            loginButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    String nickname = nicknameTextField.getText();

                    if(nickname != null) {
                        LoginScene.this.notify(nickname);

                        //TODO controllare che il nickname non sia già stato preso

                        //TransitionHandler.toGodPowerScene();
                    }
                    else
                        new AlertPopup().display("Please enter a valid nickname.");
                }
            });
    }

    public Pane getRoot() {
        return root;
    }

}
