package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


import java.io.IOException;

/**
 * Scene used to get user's nickname
 */
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

        loginButton.setOnAction(event -> {
            String nickname = nicknameTextField.getText();

            if(nickname != null) {
                LoginScene.this.notify(nickname);

            }
            else
                new AlertPopup().display("Please enter a valid nickname.");
        });
    }

    public Pane getRoot() {
        return root;
    }

}
