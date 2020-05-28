package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.*;


public class CardChoiceScene extends UiObservable {
    private Pane root;
    private ImageView nextButton;
    private Text nextText;
    private ArrayList<StackPane> stackList;
    private ArrayList<Label> labelList;
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private final List<String> gameGodsMessages = Arrays.asList("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.", "Your Worker may move one additional time, but not back to its initial space.", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", "Your Worker may build a dome at any level.", "Your Worker may build one additional time, but not on the same space.", "Your Worker may build one additional block (not dome) on top of your first block.", "Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", "You also win if your Worker moves down two or more levels.", "If your Worker does not move up, it may build both before and after moving.", "Your worker may build one additional time, but this cannot be on a perimeter space.", "Your worker may build a block under itself. You do not win by forcing yourself up to the third level.", "Each time your worker moves into a perimeter space, it may immediately move again.", "If your unmoved worker is on the ground level, it may build up to three times.", "You may remove an unoccupied block (not dome) neighbouring your unmoved worker.");
    private List<Boolean> selectedCards = new ArrayList<>();


    public CardChoiceScene(List<String> selectedByChallenger) {

        for(String god : selectedByChallenger) {
            selectedCards.add(false);
        }

        try {
            root = FXMLLoader.load(getClass().getResource("/cardChoiceScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        labelList = new ArrayList<>();
        labelList.add((Label) root.lookup("#firstLabel"));
        labelList.add((Label) root.lookup("#secondLabel"));
        labelList.add((Label) root.lookup("#thirdLabel"));

        ArrayList<ImageView> cardList = new ArrayList<>();
        cardList.add((ImageView) root.lookup("#firstCard"));
        cardList.add((ImageView) root.lookup("#secondCard"));
        cardList.add((ImageView) root.lookup("#thirdCard"));

        stackList = new ArrayList<>();
        stackList.add((StackPane) root.lookup("#firstStack"));
        stackList.add((StackPane) root.lookup("#secondStack"));
        stackList.add((StackPane) root.lookup("#thirdStack"));

        int i = 0;
        while (i < selectedByChallenger.size()) {
            for (String card : selectedByChallenger) {
                switch (card) {
                    case "APOLLO":
                        cardList.get(i).setImage(new Image("/godCards/01.png"));
                        break;
                    case "ARTEMIS":
                        cardList.get(i).setImage(new Image("/godCards/02.png"));
                        break;
                    case "ATHENA":
                        cardList.get(i).setImage(new Image("/godCards/03.png"));
                        break;
                    case "ATLAS":
                        cardList.get(i).setImage(new Image("/godCards/04.png"));
                        break;
                    case "DEMETER":
                        cardList.get(i).setImage(new Image("/godCards/05.png"));
                        break;
                    case "HEPHAESTUS":
                        cardList.get(i).setImage(new Image("/godCards/06.png"));
                        break;
                    case "MINOTAUR":
                        cardList.get(i).setImage(new Image("/godCards/08.png"));
                        break;
                    case "PAN":
                        cardList.get(i).setImage(new Image("/godCards/09.png"));
                        break;
                    case "PROMETHEUS":
                        cardList.get(i).setImage(new Image("/godCards/10.png"));
                        break;
                    case "HESTIA":
                        cardList.get(i).setImage(new Image("/godCards/21.png"));
                        break;
                    case "ZEUS":
                        cardList.get(i).setImage(new Image("/godCards/30.png"));
                        break;
                    case "TRITON":
                        cardList.get(i).setImage(new Image("/godCards/29.png"));
                        break;
                    case "POSEIDON":
                        cardList.get(i).setImage(new Image("/godCards/27.png"));
                        break;
                    case "ARES":
                        cardList.get(i).setImage(new Image("/godCards/12.png"));
                        break;
                }
                i++;
            }
        }

        if(selectedByChallenger.size() == 2) {
            stackList.get(0).setTranslateX(150.0);
            stackList.get(1).setTranslateX(-150.0);
            stackList.get(2).setMouseTransparent(true);
        }

        nextButton = (ImageView) root.lookup("#nextButton");
        nextText = (Text) root.lookup("#nextText");
        nextText.setMouseTransparent(true);

        for (StackPane card : stackList) {

            card.setOnMouseClicked(event -> {

                int counter = 0;
                for (StackPane card1 : stackList) {
                    if (card1 == event.getSource()) {
                        break;
                    }
                    counter++;
                }

                new FirstCardPopup((StackPane) event.getSource(), selectedCards, counter).display(selectedByChallenger.get(counter), gameGodsMessages.get(gameGods.indexOf(selectedByChallenger.get(counter))));

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

            if(num == 1) {

                String s = selectedByChallenger.get(selectedCards.indexOf(true));

                CardChoiceScene.this.notify(s);

            }
            else
                new AlertPopup().display("Please select one card.");
        });

    }

    public Pane getRoot() {
        return root;
    }

}
