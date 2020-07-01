package it.polimi.ingsw.PSP41.view.GUIPackage;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.observer.UiObservable;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Main game scene where the board and all the changes that happen to it are displayed
 */
public class GameScene extends UiObservable {
    private Pane root;
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private final List<String> gameGodsMessages = Arrays.asList("Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.", "Your Worker may move one additional time, but not back to its initial space.", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", "Your Worker may build a dome at any level.", "Your Worker may build one additional time, but not on the same space.", "Your Worker may build one additional block (not dome) on top of your first block.", "Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", "You also win if your Worker moves down two or more levels.", "If your Worker does not move up, it may build both before and after moving.", "Your worker may build one additional time, but this cannot be on a perimeter space.", "Your worker may build a block under itself. You do not win by forcing yourself up to the third level.", "Each time your worker moves into a perimeter space, it may immediately move again.", "If your unmoved worker is on the ground level, it may build up to three times.", "You may remove an unoccupied block (not dome) neighbouring your unmoved worker.");
    private GridPane gameBoard;
    private List<PlayersInfoMessage> players;
    private int counter=0;
    private Text phaseText;
    private Text phaseName;
    private ArrayList<Text> textList = new ArrayList<>();
    private ArrayList<StackPane> stackList = new ArrayList<>();
    private ArrayList<ImageView> cardList = new ArrayList<>();
    private ArrayList<ImageView> flagList = new ArrayList<>();
    private String clientName;
    private boolean myTurn;

    /**
     * Sets up the GameScene
     * @param players current players' info
     * @param clientName user's nickname
     */
    public GameScene(List<PlayersInfoMessage> players, String clientName) {
        try {
            root = FXMLLoader.load(getClass().getResource("/gameScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //board setup
        this.gameBoard = (GridPane) root.lookup("#board");
        this.players = players;
        this.clientName = clientName;


        for(PlayersInfoMessage player : players) {
            if (!player.getPlayerName().equals(clientName))
                counter++;
            else
                break;
        }

        //setup of the other objects
        cardList.add(0, (ImageView) root.lookup("#firstCard"));
        cardList.add(1, (ImageView) root.lookup("#secondCard"));
        cardList.add(2, (ImageView) root.lookup("#thirdCard"));

        stackList.add(0, (StackPane) root.lookup("#firstStack"));
        stackList.add(1, (StackPane) root.lookup("#secondStack"));
        stackList.add(2, (StackPane) root.lookup("#thirdStack"));

        flagList.add(0, (ImageView) root.lookup("#firstFlag"));
        flagList.add(1, (ImageView) root.lookup("#secondFlag"));
        flagList.add(2, (ImageView) root.lookup("#thirdFlag"));

        textList.add(0, (Text) root.lookup("#firstText"));
        textList.add(1, (Text) root.lookup("#secondText"));
        textList.add(2, (Text) root.lookup("#thirdText"));

        ImageView helpImage = (ImageView) root.lookup("#helpImage");
        ImageView helpPressed = (ImageView) root.lookup("#helpPressed");
        helpPressed.setMouseTransparent(true);
        phaseText = (Text) root.lookup("#phaseText");
        phaseName = (Text) root.lookup("#phaseName");


        helpImage.setOnMouseEntered(event -> {
            helpImage.setImage(null);
            helpPressed.setImage(new Image("/button-helper-down.png"));
        });

        helpImage.setOnMouseExited(event -> {
            helpImage.setImage(new Image("/button-helper-normal.png"));
            helpPressed.setImage(null);
        });

        helpImage.setOnMouseClicked(event -> {
            new HelpPopup().display();
            helpImage.setImage(new Image("/button-helper-normal.png"));
            helpPressed.setImage(null);
        });


        int i = 1;
        int tmp = 0;
        for (PlayersInfoMessage player : players) {
            if (clientName.equals(player.getPlayerName())) {
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

            card.setOnMouseClicked(event -> {

                int counter = 0;
                for (StackPane card1 : stackList) {
                    if (card1 == event.getSource()) {
                        break;
                    }
                    counter++;
                }

                if(counter==0) {
                    for (PlayersInfoMessage player : players) {
                        if (player.getPlayerName().equals(clientName)) {
                            new SecondCardPopup((StackPane) event.getSource()).display(player.getGodName(), gameGodsMessages.get(gameGods.indexOf(player.getGodName())));
                            break;
                        }
                    }
                }
                else {
                    int tmp1 = 0;
                    for (PlayersInfoMessage player : players) {
                        if (player.getPlayerName().equals(clientName))
                            break;
                        tmp1++;
                    }

                    if(tmp1 >=counter)
                        new SecondCardPopup((StackPane) event.getSource()).display(players.get(counter-1).getGodName(), gameGodsMessages.get(gameGods.indexOf(players.get(counter-1).getGodName())));
                    else
                        new SecondCardPopup((StackPane) event.getSource()).display(players.get(counter).getGodName(), gameGodsMessages.get(gameGods.indexOf(players.get(counter).getGodName())));

                }
            });

            DropShadow borderGlow = new DropShadow();
            borderGlow.setColor(javafx.scene.paint.Color.BLUE);
            borderGlow.setOffsetX(0f);
            borderGlow.setOffsetY(0f);

            card.setOnMouseEntered(event -> card.setEffect(borderGlow));

            card.setOnMouseExited(event -> card.setEffect(null));
        }
    }

    /**
     * Updates phase to move phase
     */
    public void startMovePhase() {
        phaseName.setText("MOVE");
        phaseText.setText("Choose where you want to move.");
    }

    /**
     * Updates phase to build phase
     */
    public void startBuildPhase() {
        phaseName.setText("BUILD");
        phaseText.setText("Choose where you want to build.");
    }

    /**
     * Highlights current player's god card
     * @param currPlayer current player's nickname
     */
    public void displayCurrentPlayer(String currPlayer) {
        myTurn = currPlayer.equals(clientName);

        phaseName.setText(null);
        phaseText.setText(currPlayer + "'s turn.");

        for(StackPane stack : stackList){
            stack.setStyle(null);
        }

        for(Text text : textList) {
            if(text.getText().equals(currPlayer))
                stackList.get(textList.indexOf(text)).setStyle("-fx-border-color: fuchsia;" + "-fx-border-width: 3;");
        }
    }

    /**
     * Asks the user where to place his worker and gets the input
     */
    public void askInitPosition() {
        phaseName.setText(null);
        phaseText.setText("Set workers' position.");

        for (Text text : textList) {
            if (text.getText().equals(clientName))
                stackList.get(textList.indexOf(text)).setStyle("-fx-border-color: fuchsia;" + "-fx-border-width: 3;");
        }

        for (Node node : gameBoard.getChildren()) {

            node.setOnMouseClicked(event -> {
                Pane pane = (Pane) event.getSource();

                if (findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString()) == null) {
                    Integer col = GridPane.getColumnIndex(pane);
                    Integer row = GridPane.getRowIndex(pane);

                    if(myTurn) {
                        String pos = Integer.toString(row) + Integer.toString(col);
                        notify(pos);
                    }
                }
                //else
                //new AlertPopup().display("Please choose a valid position.");
            });

            node.setOnMouseEntered(event -> {
                Pane pane = (Pane) event.getSource();

                if(myTurn) {
                    if (findId(pane, "#worker") == null) {
                        ImageView light;
                        if(players.get(counter).getPlayerColor() == Color.RED) {
                            light = addImage(pane, "/playermoveindicator_red.png");
                        }
                        else if(players.get(counter).getPlayerColor() == Color.YELLOW) {
                            light = addImage(pane, "/playermoveindicator_yellow.png");
                        }
                        else {
                            light = addImage(pane, "/playermoveindicator_blue.png");
                        }
                        light.setId("#light");
                    }
                }
            });

            node.setOnMouseExited(event -> {
                Pane pane = (Pane) event.getSource();

                pane.getChildren().remove(findId(pane, "#light"));
            });
        }

    }

    /**
     * Asks the user to select on of his/her workers and gets the input
     */
    public void askWorker() {
        phaseName.setText(null);
        phaseText.setText("Choose the worker you want to use.");

        for(Text text : textList) {
            if(text.getText().equals(clientName))
                stackList.get(textList.indexOf(text)).setStyle("-fx-border-color: fuchsia;" + "-fx-border-width: 3;");
        }

        for (Node node : gameBoard.getChildren()) {
            node.setOnMouseClicked(event -> {
                Pane pane = (Pane) event.getSource();

                if (findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString().toLowerCase() + "1") != null) {
                    notify("1");
                } else if (findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString().toLowerCase() + "2") != null) {
                    notify("2");
                }
                //else
                //new AlertPopup().display("You have to select one of your workers.");
            });

            node.setOnMouseEntered(event -> {
                Pane pane = (Pane) event.getSource();

                ImageView workerImage = (ImageView) findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString().toLowerCase());
                if (workerImage != null) {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setColor(javafx.scene.paint.Color.BLUE);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setOffsetY(0f);

                    workerImage.setEffect(borderGlow);
                }
            });

            node.setOnMouseExited(event -> {
                Pane pane = (Pane) event.getSource();

                ImageView workerImage = (ImageView) findId(pane, "#worker_" + players.get(counter).getPlayerColor().toString().toLowerCase());
                if (workerImage != null)
                    workerImage.setEffect(null);
            });
        }

    }

    /**
     * Displays available cells to move or build, asks the user to choose one and gets the input
     * @param availablePositions where player can play its action
     */
    public void askPosition(List<Position> availablePositions) {

        for(Position pos : availablePositions){
            Pane pane = (Pane) getNodeByRowColumnIndex(pos.getPosRow(), pos.getPosColumn(), gameBoard);

            ImageView light;
            if(players.get(counter).getPlayerColor() == Color.RED) {
                light = addImage(pane, "/playermoveindicator_red.png");
            }
            else if(players.get(counter).getPlayerColor() == Color.YELLOW) {
                light = addImage(pane, "/playermoveindicator_yellow.png");
            }
            else {
                light = addImage(pane, "/playermoveindicator_blue.png");
            }
            light.setId("#light");
        }

        for (Node node : gameBoard.getChildren()) {

            node.setOnMouseClicked(event -> {
                Pane pane = (Pane) event.getSource();

                if(findId(pane, "#light") != null) {
                    Integer col = GridPane.getColumnIndex(pane);
                    Integer row = GridPane.getRowIndex(pane);

                    String pos = Integer.toString(row) + Integer.toString(col);
                    notify(pos);
                }
                //else
                    //new AlertPopup().display("Please choose a valid position.");
            });

            node.setOnMouseEntered(event -> {
                Pane pane = (Pane) event.getSource();
                ImageView light = (ImageView) findId(pane, "#light");

                if (light != null) {
                    Glow glow = new Glow();
                    glow.setLevel(5);
                    light.setEffect(glow);
                }
            });

            node.setOnMouseExited(event -> {
                Pane pane = (Pane) event.getSource();
                ImageView light = (ImageView) findId(pane, "#light");

                if(light != null)
                    light.setEffect(null);
            });
        }
    }

    /**
     * Displays the loser
     * @param loser loser's nickname
     */
    public void displayLoser(String loser) {

        int i = 0;
        for(Text text : textList) {
            if(text.getText().equals(loser))
                i = textList.indexOf(text);
        }

        textList.get(i).setText(null);
        textList.get(i).setMouseTransparent(true);
        stackList.get(i).setMouseTransparent(true);
        stackList.get(i).setStyle(null);
        cardList.get(i).setImage(null);
        cardList.get(i).setMouseTransparent(true);
        flagList.get(i).setImage(null);
        flagList.get(i).setMouseTransparent(true);

        textList.remove(i);
        stackList.remove(i);
        flagList.remove(i);
        cardList.remove(i);

        if(i==1) {
            stackList.get(1).setTranslateX(-72);
            textList.get(1).setTranslateX(-72);
            flagList.get(1).setTranslateX(-72);
        }
        else if(i==2) {
            stackList.get(1).setTranslateX(72);
            textList.get(1).setTranslateX(72);
            flagList.get(1).setTranslateX(72);
        }

        players.removeIf(message -> message.getPlayerName().equals(loser));
        counter = 0;
        for(PlayersInfoMessage player : players) {
            if(!clientName.equals(loser)) {
                if (!player.getPlayerName().equals(clientName))
                    counter++;
                else
                    break;
            }
        }
        new LoserPopup().display(loser, clientName);
    }

    /**
     * Refreshes and shows the game board
     * @param board that has to be represented
     */
    public void displayBoard(Board board) {

        //clear the old board state
        for(Node node : gameBoard.getChildren())
            ((Pane) node).getChildren().clear();

        //update at the current board state
        for (int i = 0; i < Board.MAX_SIZE; i++) {
            for (int j = 0; j < Board.MAX_SIZE; j++) {
                switch (board.getCell(i, j).getLevel()) {
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
                }
                if (board.getCell(i, j).isDome()) {
                    addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/dome.png");
                } else if (board.getCell(i, j).isOccupied()) {
                    switch (board.getCell(i, j).getWorker().getColor()) {
                        case RED:
                            ImageView red = addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/worker_red.png");
                            if (board.getCell(i, j).getWorker().getNumber() == 1)
                                red.setId("#worker_red1");
                            else
                                red.setId("#worker_red2");
                            break;
                        case YELLOW:
                            ImageView yellow = addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/worker_yellow.png");
                            if (board.getCell(i, j).getWorker().getNumber() == 1)
                                yellow.setId("#worker_yellow1");
                            else
                                yellow.setId("#worker_yellow2");
                            break;
                        case BLUE:
                            ImageView blue = addImage((Pane) getNodeByRowColumnIndex(i, j, gameBoard), "/worker_blue.png");
                            if (board.getCell(i, j).getWorker().getNumber() == 1)
                                blue.setId("#worker_blue1");
                            else
                                blue.setId("#worker_blue2");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * Adds an ImageView to a Pane
     * @param pane where to add the image
     * @param image path
     * @return the new image
     */
    private ImageView addImage(Pane pane, String image) {
        ImageView view = new ImageView();
        view.setImage(new Image(image));

        pane.getChildren().add(view);
        view.fitWidthProperty().bind(pane.widthProperty());
        view.fitHeightProperty().bind(pane.heightProperty());

        return view;
    }

    /**
     * Looks for a node between the children of a Pane by part of its id
     * @param pane where the node is contained
     * @param id contained in the node's id
     * @return node child of the Pane with the wanted id
     */
    private Node findId(Pane pane, String id){
        for(Node node : pane.getChildren()) {
            ImageView img = (ImageView) node;
            if (img.getId() != null && img.getId().contains(id))
                return node;
        }

        return null;
    }

    /**
     * @param row of the node
     * @param column of the node
     * @param gridPane where the node is contained
     * @return node of the GridPane that has row and column as indexes
     */
    private Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
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