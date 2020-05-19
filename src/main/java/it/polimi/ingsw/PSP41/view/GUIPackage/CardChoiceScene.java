package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.*;


public class CardChoiceScene {
    private Pane root;
    private ImageView nextButton;
    private Text nextText;
    private ArrayList<StackPane> stackList;
    private ArrayList<Label> labelList;
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private final List<String> gameGodsMessages = Arrays.asList("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.", "You may remove an unoccupied block (not dome) neighbouring your unmoved worker.", "Your Worker may move one additional time, but not back to its initial space.", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", "Your Worker may build a dome at any level.", "Your Worker may build one additional time, but not on the same space.", "Your Worker may build one additional block (not dome) on top of your first block.", "Your worker may build one additional time, but this cannot be on a perimeter space.", "Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", "You also win if your Worker moves down two or more levels.", "If your unmoved worker is on the ground level, it may build up to three times.", "If your Worker does not move up, it may build both before and after moving.", "Each time your worker moves into a perimeter space, it may immediately move again.", "Your worker may build a block under itself. You do not win by forcing yourself up to the third level.");
    private List<Boolean> chosenCards = new ArrayList<>();
    private Map<String, Integer > playerCard;


    public CardChoiceScene(List<Boolean> selectedByChallenger, PlayersInfoMessage currPlayer, Map<String, Integer> playerCard) {
        this.playerCard = playerCard;

        for(int i = 0; i<playerCard.size(); i++){
            chosenCards.add(false);
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


        int counter = 0;
        int i = 0;
        while (i < playerCard.size()) {
            for (Boolean card : selectedByChallenger) {
                if (card) {
                    switch (counter) {
                        case 0:
                            cardList.get(i).setImage(new Image("/godCards/01.png"));
                            break;
                        case 1:
                            cardList.get(i).setImage(new Image("/godCards/02.png"));
                            break;
                        case 2:
                            cardList.get(i).setImage(new Image("/godCards/03.png"));
                            break;
                        case 3:
                            cardList.get(i).setImage(new Image("/godCards/04.png"));
                            break;
                        case 4:
                            cardList.get(i).setImage(new Image("/godCards/05.png"));
                            break;
                        case 5:
                            cardList.get(i).setImage(new Image("/godCards/06.png"));
                            break;
                        case 6:
                            cardList.get(i).setImage(new Image("/godCards/08.png"));
                            break;
                        case 7:
                            cardList.get(i).setImage(new Image("/godCards/09.png"));
                            break;
                        case 8:
                            cardList.get(i).setImage(new Image("/godCards/10.png"));
                            break;
                        case 9:
                            cardList.get(i).setImage(new Image("/godCards/21.png"));
                            break;
                        case 10:
                            cardList.get(i).setImage(new Image("/godCards/30.png"));
                            break;
                        case 11:
                            cardList.get(i).setImage(new Image("/godCards/29.png"));
                            break;
                        case 12:
                            cardList.get(i).setImage(new Image("/godCards/27.png"));
                            break;
                        case 13:
                            cardList.get(i).setImage(new Image("/godCards/12.png"));
                            break;
                    }
                    i++;
                }
                counter++;
            }
        }

        if(playerCard.size() == 2)
        {
            stackList.get(0).setTranslateX(150.0);
            stackList.get(1).setTranslateX(-150.0);
            stackList.get(2).setMouseTransparent(true);
        }

        nextButton = (ImageView) root.lookup("#nextButton");
        nextText = (Text) root.lookup("#nextText");
        nextText.setMouseTransparent(true);

        for (StackPane card : stackList) {

            card.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    int counter = 0;
                    for (StackPane card : stackList) {
                        if (card == event.getSource()) {
                            break;
                        }
                        counter++;
                    }

                    int tmp=counter;
                    int num = 0;

                    for(Boolean card : selectedByChallenger){
                        if(card){
                            if(tmp==0)
                                break;
                            else
                                tmp--;
                        }
                        num++;
                    }

                    new SecondCardPopup((StackPane) event.getSource(), chosenCards, counter, playerCard).display(gameGods.get(num), gameGodsMessages.get(num));
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
                for(Boolean card : chosenCards) {
                    if (card)
                        num++;
                }

                if(num == 1) {

                    //TODO invia al server nome god scelto

                    if(playerCard.size() == 2){
                        TransitionHandler.toPlayerScene();
                    }
                    else{
                        if(!(playerCard.containsValue(1) || playerCard.containsValue(2) || playerCard.containsValue(3)))
                            TransitionHandler.toWaitingScene();
                        else
                            TransitionHandler.toPlayerScene();
                    }
                }
                else
                    new AlertPopup().display("Please select one card.");
            }
        });

    }

    public Pane getRoot() {
        return root;
    }


    //metodo che il server chiama per aggiornare le scelte dei giocatori nel caso di 3 giocatori
    public void updatePlayerChoice(Map<String, Integer> playerCard){
        this.playerCard = playerCard;

        for (Map.Entry<String,Integer> entry : this.playerCard.entrySet()){
            if(entry.getValue()!=0){
                labelList.get(entry.getValue()-1).setText("Card taken by " + entry.getKey() + ".");
                labelList.get(entry.getValue()-1).setWrapText(true);
            }
        }
    }
}
