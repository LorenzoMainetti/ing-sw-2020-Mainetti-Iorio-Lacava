package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerScene {
    private Pane root;
    private ArrayList<StackPane> stackList;
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private final List<String> gameGodsMessages = Arrays.asList("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.", "You may remove an unoccupied block (not dome) neighbouring your unmoved worker.", "Your Worker may move one additional time, but not back to its initial space.", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", "Your Worker may build a dome at any level.", "Your Worker may build one additional time, but not on the same space.", "Your Worker may build one additional block (not dome) on top of your first block.", "Your worker may build one additional time, but this cannot be on a perimeter space.", "Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", "You also win if your Worker moves down two or more levels.", "If your unmoved worker is on the ground level, it may build up to three times.", "If your Worker does not move up, it may build both before and after moving.", "Each time your worker moves into a perimeter space, it may immediately move again.", "Your worker may build a block under itself. You do not win by forcing yourself up to the third level.");


    public PlayerScene(List<PlayersInfoMessage> players, String challenger) {

        try {
            root = FXMLLoader.load(getClass().getResource("/playerScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ImageView> cardList = new ArrayList<>();
        cardList.add((ImageView) root.lookup("#firstCard"));
        cardList.add((ImageView) root.lookup("#secondCard"));
        cardList.add((ImageView) root.lookup("#thirdCard"));

        ArrayList<Label> labelList = new ArrayList<>();
        labelList.add((Label) root.lookup("#firstPlayer"));
        labelList.add((Label) root.lookup("#secondPlayer"));
        labelList.add((Label) root.lookup("#thirdPlayer"));

        stackList = new ArrayList<>();
        stackList.add((StackPane) root.lookup("#firstStack"));
        stackList.add((StackPane) root.lookup("#secondStack"));
        stackList.add((StackPane) root.lookup("#thirdStack"));

        ImageView thirdPlayerDrap = (ImageView) root.lookup("#thirdPlayerDrap");
        TextField challengerTextField = (TextField) root.lookup("#challengerTextField");
        ImageView playButton = (ImageView) root.lookup("#playButton");
        ImageView versusImage = (ImageView) root.lookup("#versusImage");

        challengerTextField.setDisable(true);
        challengerTextField.setStyle("-fx-background-color: transparent;");
        versusImage.setMouseTransparent(true);

        if(players.size()==2){
            thirdPlayerDrap.setImage(null);
            thirdPlayerDrap.setMouseTransparent(true);
            stackList.get(2).setMouseTransparent(true);
            versusImage.setImage(new Image("/versus_particle.png"));
            versusImage.setTranslateX(-70);
        }

        int i=0;
        while(i<players.size()) {
            for (PlayersInfoMessage player : players) {
                switch(player.getGodName()){
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

        int j=0;
        while(j<players.size()) {
            for (PlayersInfoMessage player : players) {
                labelList.get(j).setText(player.getPlayerName());
                j++;
            }
        }

        for(StackPane card : stackList) {

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

                    new ThirdCardPopup((StackPane) event.getSource()).display(players.get(counter).getGodName(), gameGodsMessages.get(gameGods.indexOf(players.get(counter).getGodName())));
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


        //TODO ricavare il nome del currPlayer (nome del player che sta usando il client) dal server
        String currPlayer = "gine";

        if(currPlayer.equals(challenger)){
            challengerTextField.setDisable(false);
            challengerTextField.setStyle("-fx-background-color: white;");
            playButton.setImage(null);
            playButton.setMouseTransparent(true);

            ImageView challengerBackground = (ImageView) root.lookup("#challengerBackground");
            challengerBackground.setImage(new Image("/challengerBackground.png"));

            Text challengerText = (Text) root.lookup("#challengerText");
            challengerText.setText("Choose the first player:");

            challengerTextField.setOnKeyPressed(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent ke)
                {
                    ArrayList<String> nicknames = new ArrayList<>();
                    for(PlayersInfoMessage player : players)
                    {
                        nicknames.add(player.getPlayerName());
                    }

                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        if(nicknames.contains(challengerTextField.getText()) && challengerTextField.getText() != null) {
                            challengerBackground.setImage(null);
                            challengerText.setText(null);
                            challengerTextField.setDisable(true);
                            challengerTextField.setText(null);
                            challengerTextField.setStyle("-fx-background-color: transparent;");

                            playButton.setImage(new Image("/button-play-normal.png"));
                            playButton.setMouseTransparent(false);
                            //TODO invia nome al server
                        }
                        else
                            new AlertPopup().display("Please enter a valid nickname.");
                    }
                }
            });
        }

        playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playButton.setImage(new Image("/button-play-down.png"));
            }
        });

        playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playButton.setImage(new Image("/button-play-normal.png"));
            }
        });

        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TransitionHandler.toGameScene();
            }
        });


    }

    public Pane getRoot() {
        return root;
    }
}