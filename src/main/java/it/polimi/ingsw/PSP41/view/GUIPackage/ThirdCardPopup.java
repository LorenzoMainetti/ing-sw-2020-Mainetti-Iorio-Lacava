package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ThirdCardPopup extends CardPopup {

    public ThirdCardPopup(StackPane eventSource){
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

        closeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeButton.setImage(new Image("/btn_coral_pressed.png"));
                closeText.setTranslateY(2.0);
            }
        });

        closeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeButton.setImage(new Image("/btn_coral.png"));
                closeText.setTranslateY(-0.5);
            }
        });

        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

}
