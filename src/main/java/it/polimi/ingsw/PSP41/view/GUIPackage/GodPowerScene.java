package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Scene that displays all the available god cards and asks the challenger
 * to choose a number of them equal to the number of players
 */
public class GodPowerScene extends UiObservable {
    private Pane root;
    private ArrayList<StackPane> cardList;
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private final List<String> gameGodsMessages = Arrays.asList("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.", "Your Worker may move one additional time, but not back to its initial space.", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", "Your Worker may build a dome at any level.", "Your Worker may build one additional time, but not on the same space.", "Your Worker may build one additional block (not dome) on top of your first block.", "Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", "You also win if your Worker moves down two or more levels.", "If your Worker does not move up, it may build both before and after moving.", "Your worker may build one additional time, but this cannot be on a perimeter space.", "Your worker may build a block under itself. You do not win by forcing yourself up to the third level.", "Each time your worker moves into a perimeter space, it may immediately move again.", "If your unmoved worker is on the ground level, it may build up to three times.", "You may remove an unoccupied block (not dome) neighbouring your unmoved worker.");
    private final List<Boolean> selectedCards = new ArrayList<>();
    private ImageView nextButton;
    private Text nextText;

    public GodPowerScene(int playersCount) {
        for(String ignored : gameGods){
            selectedCards.add(false);
        }

        try {
            root = FXMLLoader.load(getClass().getResource("/godPowerScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardList = new ArrayList<>();
        cardList.add((StackPane) root.lookup("#apolloCard"));
        cardList.add((StackPane) root.lookup("#artemisCard"));
        cardList.add((StackPane) root.lookup("#athenaCard"));
        cardList.add((StackPane) root.lookup("#atlasCard"));
        cardList.add((StackPane) root.lookup("#demeterCard"));
        cardList.add((StackPane) root.lookup("#hephaestusCard"));
        cardList.add((StackPane) root.lookup("#minotaurCard"));
        cardList.add((StackPane) root.lookup("#panCard"));
        cardList.add((StackPane) root.lookup("#prometheusCard"));
        cardList.add((StackPane) root.lookup("#hestiaCard"));
        cardList.add((StackPane) root.lookup("#zeusCard"));
        cardList.add((StackPane) root.lookup("#tritonCard"));
        cardList.add((StackPane) root.lookup("#poseidonCard"));
        cardList.add((StackPane) root.lookup("#aresCard"));

        nextButton = (ImageView) root.lookup("#nextButton");
        nextText = (Text) root.lookup("#nextText");
        nextText.setMouseTransparent(true);
        Text choiceText = (Text) root.lookup("#choiceText");
        choiceText.setText("You are the Challenger. Choose " + playersCount + " God Power cards for this game:");

        for (StackPane card : cardList) {
            card.setOnMouseClicked(event -> {

                int counter = 0;
                for (StackPane card1 : cardList) {
                    if (card1 == event.getSource()) {
                        break;
                    }
                    counter++;
                }

                new FirstCardPopup((StackPane) event.getSource(), selectedCards, counter).display(gameGods.get(counter), gameGodsMessages.get(counter));
            });

            DropShadow borderGlow = new DropShadow();
            borderGlow.setColor(Color.BLUE);
            borderGlow.setOffsetX(0f);
            borderGlow.setOffsetY(0f);

            card.setOnMouseEntered(event -> card.setEffect(borderGlow));

            card.setOnMouseExited(event -> card.setEffect(null));

        }

        nextButton.setOnMouseEntered(event -> {
            nextButton.setImage(new Image("/btn_blue_pressed.png"));
            nextText.setTranslateY(2.0);
        });

        nextButton.setOnMouseExited(event -> {
            nextButton.setImage(new Image("/btn_blue.png"));
            nextText.setTranslateY(-0.5);
        });

        nextButton.setOnMouseClicked(event -> {
            int num = 0;
            for(Boolean card : selectedCards) {
                if (card)
                    num++;
            }

            if(playersCount == num) {

                String s = "";
                int j = 0;
                for(int i=0; i<gameGods.size(); i++) {
                    if(selectedCards.get(i)) {
                        s = s.concat(gameGods.get(i));
                        j++;
                        if(j < playersCount)
                            s = s.concat("/");
                    }
                    if(j == playersCount)
                        break;
                }

                notify(s);

            }
            else
                new AlertPopup().display("Wrong number of cards.\nYou must select " + playersCount + ".\nTry again!");
        });

    }

    public Pane getRoot() {
        return root;
    }
}
