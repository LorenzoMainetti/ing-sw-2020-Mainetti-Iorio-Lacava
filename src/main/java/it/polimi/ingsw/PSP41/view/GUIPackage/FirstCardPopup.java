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
import java.util.List;


public class FirstCardPopup extends CardPopup {

    public FirstCardPopup(StackPane eventSource, List<Boolean> selectedCards, int counter) {
        try {
            root= FXMLLoader.load(getClass().getResource("/cardPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        messageLabel = (Label)root.lookup("#messageLabel");
        closeButton = (ImageView)root.lookup("#closeButton");
        selectButton = (ImageView)root.lookup("#selectButton");
        closeText = (Text) root.lookup("#closeText");
        selectText = (Text) root.lookup("#selectText");
        godName = (Label) root.lookup("#godName");

        this.eventSource = eventSource;
        this.counter = counter;
        this.selectedCards = selectedCards;
    }

    @Override
    public void display(String title, String message){
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
        selectText.setMouseTransparent(true);

        if(selectedCards.get(counter)){
            selectText.setText("DESELECT");
            selectText.setTranslateX(-10.0);
        }

        closeButton.setOnMouseEntered(event -> {
            closeButton.setImage(new Image("/btn_coral_pressed.png"));
            closeText.setTranslateY(2.0);
        });

        closeButton.setOnMouseExited(event -> {
            closeButton.setImage(new Image("/btn_coral.png"));
            closeText.setTranslateY(-0.5);
        });

        closeButton.setOnMouseClicked(event -> stage.close());

        selectButton.setOnMouseEntered(event -> {
            selectButton.setImage(new Image("/btn_blue_pressed.png"));
            selectText.setTranslateY(2.0);
        });

        selectButton.setOnMouseExited(event -> {
            selectButton.setImage(new Image("/btn_blue.png"));
            selectText.setTranslateY(-0.5);
        });

        selectButton.setOnMouseClicked(event -> {
            if(selectedCards.get(counter)){
                eventSource.setStyle("-fx-border-color: none");
                selectedCards.set(counter, false);
            }
            else {
                eventSource.setStyle("-fx-border-color: red;" + "-fx-border-width: 3;");
                selectedCards.set(counter, true);
            }

            stage.close();
        });

        stage.setScene(scene);
        stage.show();
    }

}

