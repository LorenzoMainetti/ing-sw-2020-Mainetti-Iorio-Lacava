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
import java.util.List;
import java.util.Map;

public class SecondCardPopup extends CardPopup {
    private List<Boolean> chosenCards;
    private Map<String, Integer> playerCard;

    public SecondCardPopup(StackPane eventSource, List<Boolean> chosenCards, int counter, Map<String, Integer> playerCard){

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
        this.chosenCards = chosenCards;
        this.playerCard = playerCard;
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
        selectText.setMouseTransparent(true);



        for (Map.Entry<String,Integer> entry : this.playerCard.entrySet()){
            if(entry.getValue()!= 0) {
                if (counter+1 == entry.getValue()) {
                    selectText.setText("      ");
                    selectButton.setImage(null);
                    selectButton.setMouseTransparent(true);
                }
            } else {
                if (chosenCards.get(counter)) {
                    selectText.setText("DESELECT");
                    selectText.setTranslateX(-10.0);
                }

                selectButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectButton.setImage(new Image("/btn_blue_pressed.png"));
                        selectText.setTranslateY(2.0);
                    }
                });

                selectButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectButton.setImage(new Image("/btn_blue.png"));
                        selectText.setTranslateY(-0.5);
                    }
                });

                selectButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(chosenCards.get(counter)){
                            eventSource.setStyle("-fx-border-color: none");
                            chosenCards.set(counter, false);

                        }
                        else {
                            eventSource.setStyle("-fx-border-color: red;" + "-fx-border-width: 3;");
                            chosenCards.set(counter, true);
                        }

                        stage.close();
                    }
                });
            }
        }

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
