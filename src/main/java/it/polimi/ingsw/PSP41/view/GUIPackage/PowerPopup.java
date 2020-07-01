package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.client.NetworkHandler;
import it.polimi.ingsw.PSP41.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


/**
 * Popup used to ask the user if he wants to activate his god power
 */
public class PowerPopup extends UiObservable {
    private Pane root;
    private ImageView yesButton;
    private ImageView noButton;
    private Text yesText;
    private Text noText;

    public PowerPopup(NetworkHandler networkHandler) {
        try {
            root = FXMLLoader.load(getClass().getResource("/powerPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addObserver(networkHandler);

        yesButton = (ImageView) root.lookup("#yesButton");
        noButton = (ImageView) root.lookup("#noButton");

        yesText = (Text) root.lookup("#yesText");
        noText = (Text) root.lookup("#noText");
        yesText.setMouseTransparent(true);
        noText.setMouseTransparent(true);
    }

    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setY(325);
        stage.setX(476);


        yesButton.setOnMouseEntered(event -> {
            yesButton.setImage(new Image("/btn_green_pressed.png"));
            yesText.setTranslateY(2.0);
        });

        yesButton.setOnMouseExited(event -> {
            yesButton.setImage(new Image("/btn_green.png"));
            yesText.setTranslateY(-0.5);
        });

        yesButton.setOnMouseClicked(event -> {
            notify("yes");
            stage.close();
        });

        noButton.setOnMouseEntered(event -> {
            noButton.setImage(new Image("/btn_coral_pressed.png"));
            noText.setTranslateY(2.0);
        });

        noButton.setOnMouseExited(event -> {
            noButton.setImage(new Image("/btn_coral.png"));
            noText.setTranslateY(-0.5);
        });

        noButton.setOnMouseClicked(event -> {
            notify("no");
            stage.close();
        });

        stage.setScene(scene);
        stage.show();
    }

}
