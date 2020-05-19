package it.polimi.ingsw.PSP41.view.GUIPackage;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


import java.io.IOException;



public class LoginScene {
    private Pane root;
    private String nickname;

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
                    if(nicknameTextField.getText() != null) {
                        nickname = nicknameTextField.getText();

                        //TODO controllare che il nickname non sia già stato preso

                        //TODO andare alla waiting scene
                        TransitionHandler.toGodPowerScene();
                    }
                    else
                        new AlertPopup().display("Please enter a valid nickname.");
                }
            });
    }

    public Pane getRoot() {
        return root;
    }

    public String getNickname() {
        return this.nickname;
    }

}
