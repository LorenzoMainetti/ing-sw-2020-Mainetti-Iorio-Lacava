package it.polimi.ingsw.PSP41.view.GUIPackage;

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
 * Popup that contains the game rules
 */
public class HelpPopup {
    private Pane root;
    private ImageView closeButton;
    private Text closeText;

    public HelpPopup() {
        try {
            root= FXMLLoader.load(getClass().getResource("/helpPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeButton = (ImageView) root.lookup("#closeButton");
        closeText = (Text) root.lookup("#closeText");
    }

    public void display(){
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setY(70);
        stage.setX(430);


        closeText.setMouseTransparent(true);

        closeButton.setOnMouseEntered(event -> {
            closeButton.setImage(new Image("/btn_blue_pressed.png"));
            closeText.setTranslateY(2.0);
        });

        closeButton.setOnMouseExited(event -> {
            closeButton.setImage(new Image("/btn_blue.png"));
            closeText.setTranslateY(-0.5);
        });

        closeButton.setOnMouseClicked(event -> stage.close());

        stage.setScene(scene);
        stage.show();
    }
}
