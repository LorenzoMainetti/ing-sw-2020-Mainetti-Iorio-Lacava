package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameScene {
    private Pane root;
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private final List<String> gameGodsMessages = Arrays.asList("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.", "You may remove an unoccupied block (not dome) neighbouring your unmoved worker.", "Your Worker may move one additional time, but not back to its initial space.", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", "Your Worker may build a dome at any level.", "Your Worker may build one additional time, but not on the same space.", "Your Worker may build one additional block (not dome) on top of your first block.", "Your worker may build one additional time, but this cannot be on a perimeter space.", "Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", "You also win if your Worker moves down two or more levels.", "If your unmoved worker is on the ground level, it may build up to three times.", "If your Worker does not move up, it may build both before and after moving.", "Each time your worker moves into a perimeter space, it may immediately move again.", "Your worker may build a block under itself. You do not win by forcing yourself up to the third level.");
    private GridPane gameBoard;
    List<PlayersInfoMessage> players;
    private int counter=0;
    private Text phaseText;
    private Text phaseName;
    ArrayList<Text> textList = new ArrayList<>();
    ArrayList<StackPane> stackList = new ArrayList<>();
    String currClient;


    public GameScene(List<PlayersInfoMessage> players, String currClient) {
        try {
            root = FXMLLoader.load(getClass().getResource("/gameScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //board setup
        this.gameBoard = (GridPane) root.lookup("#board");
        this.players = players;
        this.currClient = currClient;


        for(PlayersInfoMessage player : players) {
            if (!player.getPlayerName().equals(currClient))
                counter++;
            else
                break;
        }


        //setup of the other objects
        ArrayList<ImageView> cardList = new ArrayList<>();
        cardList.add((ImageView) root.lookup("#firstCard"));
        cardList.add((ImageView) root.lookup("#secondCard"));
        cardList.add((ImageView) root.lookup("#thirdCard"));

        stackList.add(0, (StackPane) root.lookup("#firstStack"));
        stackList.add(1, (StackPane) root.lookup("#secondStack"));
        stackList.add(2, (StackPane) root.lookup("#thirdStack"));

        ArrayList<ImageView> flagList = new ArrayList<>();
        flagList.add((ImageView) root.lookup("#firstFlag"));
        flagList.add((ImageView) root.lookup("#secondFlag"));
        flagList.add((ImageView) root.lookup("#thirdFlag"));

        textList.add(0, (Text) root.lookup("#firstText"));
        textList.add(1, (Text) root.lookup("#secondText"));
        textList.add(2, (Text) root.lookup("#thirdText"));

        ImageView helpImage = (ImageView) root.lookup("#helpImage");
        ImageView helpPressed = (ImageView) root.lookup("#helpPressed");
        helpPressed.setMouseTransparent(true);
        phaseText = (Text) root.lookup("#phaseText");
        phaseName = (Text) root.lookup("#phaseName");


        helpImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                helpImage.setImage(null);
                helpPressed.setImage(new Image("/button-helper-down.png"));
            }
        });

        helpImage.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                helpImage.setImage(new Image("/button-helper-normal.png"));
                helpPressed.setImage(null);
            }
        });

        helpImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new HelpPopup().display();
                helpImage.setImage(new Image("/button-helper-normal.png"));
                helpPressed.setImage(null);
            }
        });


        int i = 1;
        int tmp = 0;
        for (PlayersInfoMessage player : players) {
            if (currClient.equals(player.getPlayerName())) {
                tmp = i;
                i = 0;
            }
            switch (player.getGodName()) {
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
            switch (player.getPlayerColor()) {
                case RED:
                    flagList.get(i).setImage(new Image("/gameGodFrameName1.png"));
                    break;
                case YELLOW:
                    flagList.get(i).setImage(new Image("/gameGodFrameName2.png"));
                    break;
                case BLUE:
                    flagList.get(i).setImage(new Image("/gameGodFrameName3.png"));
                    break;
            }
            textList.get(i).setText(player.getPlayerName());
            i++;

            if(tmp!=0) {
                i = tmp;
                tmp = 0;
            }

        }

        if(players.size() == 2){
            stackList.get(1).setTranslateX(72);
            textList.get(1).setTranslateX(72);
            flagList.get(1).setTranslateX(72);
            stackList.get(2).setMouseTransparent(true);
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

                    if(counter==0) {
                        for (PlayersInfoMessage player : players) {
                            if (player.getPlayerName().equals(currClient)) {
                                new SecondCardPopup((StackPane) event.getSource()).display(player.getGodName(), gameGodsMessages.get(gameGods.indexOf(player.getGodName())));
                                break;
                            }
                        }
                    }
                    else {
                        int tmp = 0;
                        for (PlayersInfoMessage player : players) {
                            if (player.getPlayerName().equals(currClient))
                                break;
                            tmp++;
                        }

                        if(tmp>=counter)
                            new SecondCardPopup((StackPane) event.getSource()).display(players.get(counter-1).getGodName(), gameGodsMessages.get(gameGods.indexOf(players.get(counter-1).getGodName())));
                        else
                            new SecondCardPopup((StackPane) event.getSource()).display(players.get(counter).getGodName(), gameGodsMessages.get(gameGods.indexOf(players.get(counter).getGodName())));

                    }
                }
            });

            DropShadow borderGlow = new DropShadow();
            borderGlow.setColor(javafx.scene.paint.Color.BLUE);
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
    }

    public void startMovePhase(){
        phaseName.setText("MOVE");
        phaseText.setText("Choose where you want to move.");
    }

    public void startBuildPhase(){
        phaseName.setText("BUILD");
        phaseText.setText("Choose where you want to build.");
    }

    public void displayCurrentPlayer(String currPlayer){
        phaseName.setText(null);
        phaseText.setText(currPlayer + "'s turn.");

        for(StackPane stack : stackList){
            stack.setStyle(null);
        }

        for(Text text : textList) {
            if(text.getText().equals(currPlayer))
                stackList.get(textList.indexOf(text)).setStyle("-fx-border-color: red;" + "-fx-border-width: 3;");
        }
    }

    public void askInitPosition() {
        phaseName.setText(null);
        phaseText.setText("Set workers' position.");

        for(Text text : textList) {
            if(text.getText().equals(currClient))
                stackList.get(textList.indexOf(text)).setStyle("-fx-border-color: red;" + "-fx-border-width: 3;");
        }

        for (Node node : this.gameBoard.getChildren()) {

            node.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();

                    if(findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString()) == null) {
                        Integer col = GridPane.getColumnIndex(pane);
                        Integer row = GridPane.getRowIndex(pane);

                        //TODO notifica col e row al server
                    }
                    else
                        new AlertPopup().display("Please choose a valid position.");
                }
            });

            node.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();

                    if (findId(pane, "#worker") == null) {
                        ImageView light = addImage(pane, "/playermoveindicator_" + players.get(counter).getPlayerColor().toString() + ".png");
                        light.setId("#light");
                    }
                }
            });

            node.setOnMouseExited(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();

                    pane.getChildren().remove(findId(pane, "#light"));
                }
            });
        }
    }


    public void askWorker(){
        phaseName.setText(null);
        phaseText.setText("Choose the worker you want to use.");

        for(Text text : textList) {
            if(text.getText().equals(currClient))
                stackList.get(textList.indexOf(text)).setStyle("-fx-border-color: red;" + "-fx-border-width: 3;");
        }

        for (Node node : this.gameBoard.getChildren()) {
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();

                    ImageView worker = (ImageView) findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString().toLowerCase());

                    if (worker != null) {
                        Integer col = GridPane.getColumnIndex(pane);
                        Integer row = GridPane.getRowIndex(pane);

                        //TODO notifica al server posizione worker scelto
                    } else
                        new AlertPopup().display("You have to select one of your workers.");
                }
            });

            node.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();

                    ImageView workerImage = (ImageView) findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString().toLowerCase());
                    if (workerImage != null) {
                        DropShadow borderGlow = new DropShadow();
                        borderGlow.setColor(javafx.scene.paint.Color.BLUE);
                        borderGlow.setOffsetX(0f);
                        borderGlow.setOffsetY(0f);

                        workerImage.setEffect(borderGlow);
                    }
                }
            });

            node.setOnMouseExited(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();

                    ImageView workerImage = (ImageView) findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString().toLowerCase());
                    if (workerImage != null)
                        workerImage.setEffect(null);
                }
            });
        }
    }


    public void askPosition(List<Position> availablePositions) {

        for(Position pos : availablePositions){
            Pane pane = (Pane) getNodeByRowColumnIndex(pos.getPosRow(), pos.getPosColumn(), gameBoard);

            ImageView light = addImage(pane, "/playermoveindicator_" + players.get(counter).getPlayerColor().toString() + ".png");
            light.setId("#light");
        }

        for (Node node : this.gameBoard.getChildren()) {

            node.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();

                    if(findId(pane, "#light") != null) {
                        Integer col = GridPane.getColumnIndex(pane);
                        Integer row = GridPane.getRowIndex(pane);

                        //TODO notifica col e row al server
                    }
                    else
                        new AlertPopup().display("Please choose a valid position.");
                }
            });

            node.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();
                    ImageView light = (ImageView) findId(pane, "#light");

                    if (light != null) {
                        Glow glow = new Glow();
                        glow.setLevel(5);
                        light.setEffect(glow);
                    }
                }
            });

            node.setOnMouseExited(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Pane pane = (Pane) event.getSource();
                    ImageView light = (ImageView) findId(pane, "#light");

                    if(light != null)
                        light.setEffect(null);
                }
            });
        }
    }


    public void askPowerActivation(){
        new PowerPopup().display();
    }

    public void displayLoser(String loser){
        new LoserPopup().display(loser, currClient);
    }


    public void displayBoard(Board board){

        for(int i=0; i<board.getGrid().length; i++){
            for(int j=0; j<board.getGrid()[i].length; j++) {
                switch(board.getGrid()[i][j].getLevel()){
                    case 0:
                        break;
                    case 1:
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/firstLevel.png");
                        break;
                    case 2:
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/firstLevel.png");
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/secondLevel.png");
                        break;
                    case 3:
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/firstLevel.png");
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/secondLevel.png");
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/thirdLevel.png");
                        break;
                    case 4:
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/firstLevel.png");
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/secondLevel.png");
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/thirdLevel.png");
                        addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/dome.png");
                        break;
                }
                if(board.getGrid()[i][j].getWorker() != null){
                    switch(board.getGrid()[i][j].getWorker().getColor()){
                        case RED:
                            ImageView red = addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/worker_red.png");
                            red.setId("#worker_red");
                            break;
                        case YELLOW:
                            ImageView yellow = addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/worker_yellow.png");
                            yellow.setId("#worker_yellow");
                            break;
                        case BLUE:
                            ImageView blue = addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/worker_blue.png");
                            blue.setId("#worker_blu");
                            break;
                    }
                }
            }
        }
    }

    public ImageView addImage(Pane pane, String image){
        ImageView view = new ImageView();
        view.setImage(new Image(image));

        pane.getChildren().add(view);
        view.fitWidthProperty().bind(pane.widthProperty());
        view.fitHeightProperty().bind(pane.heightProperty());

        return view;
    }

    public Node findId(Pane pane, String id){
        for(Node node : pane.getChildren()) {
            ImageView img = (ImageView) node;
            if (img.getId() != null && img.getId().contains(id))
                return node;
        }

        return null;
    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public Pane getRoot() {
        return root;
    }
}
