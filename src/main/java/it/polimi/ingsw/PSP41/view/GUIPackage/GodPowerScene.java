package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GodPowerScene {
    private Pane root;
    private ArrayList<StackPane> cardList;
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private final List<String> gameGodsMessages = Arrays.asList("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.", "You may remove an unoccupied block (not dome) neighbouring your unmoved worker.", "Your Worker may move one additional time, but not back to its initial space.", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", "Your Worker may build a dome at any level.", "Your Worker may build one additional time, but not on the same space.", "Your Worker may build one additional block (not dome) on top of your first block.", "Your worker may build one additional time, but this cannot be on a perimeter space.", "Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", "You also win if your Worker moves down two or more levels.", "If your unmoved worker is on the ground level, it may build up to three times.", "If your Worker does not move up, it may build both before and after moving.", "Each time your worker moves into a perimeter space, it may immediately move again.", "Your worker may build a block under itself. You do not win by forcing yourself up to the third level.");
    private final List<Boolean> selectedCards = new ArrayList<>();
    private ImageView nextButton;
    private Text nextText;
    private int playersCount;

    public GodPowerScene(int playersCount) {
        for(String god : gameGods){
            selectedCards.add(false);
        }

        this.playersCount = playersCount;

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

        for (StackPane card : cardList) {
            card.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    int counter = 0;
                    for (StackPane card : cardList) {
                        if (card == event.getSource()) {
                            break;
                        }
                        counter++;
                    }

                    new FirstCardPopup((StackPane) event.getSource(), selectedCards, counter).display(gameGods.get(counter), gameGodsMessages.get(counter));
                }
            });

            DropShadow borderGlow = new DropShadow();
            borderGlow.setColor(Color.BLUE);
            borderGlow.setOffsetX(0f);
            borderGlow.setOffsetY(0f);

            card.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    card.setEffect(borderGlow);
                }
            });

            card.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    card.setEffect(null);
                }
            });

        }

        nextButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nextButton.setImage(new Image("/btn_blue_pressed.png"));
                nextText.setTranslateY(2.0);
            }
        });

        nextButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nextButton.setImage(new Image("/btn_blue.png"));
                nextText.setTranslateY(-0.5);
            }
        });

        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int num = 0;
                for(Boolean card : selectedCards) {
                    if (card)
                        num++;
                }

                if(playersCount == num) {
                    TransitionHandler.toCardChoiceScene();
                    new AlertPopup().display("God Card selected!\nNow wait for the other players to choose their Gods.");
                }
                else
                    new AlertPopup().display("Wrong number of cards.\nYou must select " + playersCount + ".\nTry again!");

                //TODO popup che dice di aspettare altro player
            }
        });

    }

    public Pane getRoot() {
        return root;
    }
}
