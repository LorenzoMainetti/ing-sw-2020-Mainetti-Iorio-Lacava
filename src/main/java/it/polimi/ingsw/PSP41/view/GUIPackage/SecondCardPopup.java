package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Popup used to display a god card effect (not selectable)
 */
public class SecondCardPopup extends CardPopup {

    public SecondCardPopup(StackPane eventSource){
        try {
            root= FXMLLoader.load(getClass().getResource("/cardPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        messageLabel = (Label)root.lookup("#messageLabel");
        closeButton = (ImageView)root.lookup("#closeButton");
        closeText = (Text) root.lookup("#closeText");
        godName = (Label) root.lookup("#godName");
        selectButton = (ImageView)root.lookup("#selectButton");
        selectText = (Text) root.lookup("#selectText");

        this.eventSource = eventSource;
    }

    @Override
    public void display(String title, String message) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        messageLabel.setText(message);
        messageLabel.setWrapText(true);
        godName.setText(title);
        godName.setAlignment(Pos.CENTER);

        closeText.setMouseTransparent(true);
        selectText.setText("      ");
        selectButton.setImage(null);
        selectButton.setMouseTransparent(true);

        closeButton.setOnMouseEntered(event -> {
            closeButton.setImage(new Image("/btn_coral_pressed.png"));
            closeText.setTranslateY(2.0);
        });

        closeButton.setOnMouseExited(event -> {
            closeButton.setImage(new Image("/btn_coral.png"));
            closeText.setTranslateY(-0.5);
        });

        closeButton.setOnMouseClicked(event -> stage.close());

        stage.setScene(scene);
        stage.show();
    }

}
