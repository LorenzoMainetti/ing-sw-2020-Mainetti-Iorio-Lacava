package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class PowerPopup {
    private Pane root;
    private ImageView yesButton;
    private ImageView noButton;
    private Text yesText;
    private Text noText;

    public PowerPopup() {
        try {
            root = FXMLLoader.load(getClass().getResource("/powerPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setY(325);
        stage.setX(476);

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


        yesButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                yesButton.setImage(new Image("/btn_green_pressed.png"));
                yesText.setTranslateY(2.0);
            }
        });

        yesButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                yesButton.setImage(new Image("/btn_green.png"));
                yesText.setTranslateY(-0.5);
            }
        });

        yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO: invia risposta (SI) al server
                stage.close();
            }
        });

        noButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                noButton.setImage(new Image("/btn_coral_pressed.png"));
                noText.setTranslateY(2.0);
            }
        });

        noButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                noButton.setImage(new Image("/btn_coral.png"));
                noText.setTranslateY(-0.5);
            }
        });

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO: invia risposta (NO) al server
                stage.close();
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
