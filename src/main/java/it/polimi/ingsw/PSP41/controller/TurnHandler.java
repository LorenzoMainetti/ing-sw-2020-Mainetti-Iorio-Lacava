package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.view.CLI;

import java.util.*;

/**
 * Controller class that handles the game logic
 */
public class TurnHandler {
    private final List<Player> playerList;
    private UserInputManager userInputManager;
    private final Board board;
    private CLI theView;

    public TurnHandler(Board board, CLI theView) {
        playerList = new ArrayList<>();
        userInputManager = new UserInputManager(theView);
        this.board = board;
        this.theView = theView;
    }

    /**
     * setup game and handle turn shifts
     */
    public void play() {
        theView.setUpGame();
        ArrayList<String> godList = new ArrayList<>(Arrays.asList("Apollo", "Artemis", "Athena", "Atlas", "Demeter", "Hephaestus", "Minotaur", "Pan", "Prometheus"));
        GodPowerFactory godFactory = new GodPowerFactory();
        ArrayList<GodPower> activeGodList = new ArrayList<>();
        Color color = Color.RED;

        playerList.add(new Player(userInputManager.getNickname(), color));

        while(playerList.size() != userInputManager.getPlayersNumber()) {
            theView.askNickname();
            while (takenNickname(playerList))
                theView.printError();
            color = color.next();
            playerList.add(new Player(userInputManager.getNickname(), color));
        }

        //timeout -> ask for 2 o 3 players

        for(Player player : playerList) {
            Collections.shuffle(godList);
            String godName = godList.get(0);
            theView.printGodPower(player.getNickname(), godName);
            godList.remove(0);
            activeGodList.add(godFactory.createGodPower(godName, player, userInputManager));

            //view osserva i player nel model
            player.addObserver(theView);
            player.getWorker1().addObserver(theView);
            player.getWorker2().addObserver(theView);

            boolean illegal = true;

            while(illegal) {
                theView.askInitialPosition();
                try {
                    player.getWorker1().setPosition(board, userInputManager.getChosenRow(), userInputManager.getChosenColumn());
                    illegal = false;
                } catch (IllegalStateException e) {
                    theView.positionTaken();
                }
            }

            illegal = true;

            while(illegal) {
                theView.askInitialPosition();
                try {
                    player.getWorker2().setPosition(board, userInputManager.getChosenRow(), userInputManager.getChosenColumn());
                    illegal = false;
                } catch (IllegalStateException e) {
                    theView.positionTaken();
                }
            }
        }

        int i;
        outside:
        while(playerList.size() >= 2) {
            i = 0;
            for(GodPower godPower : activeGodList) {
                performTurn(godPower, board);
                if(playerList.get(i).isWinner())
                    break outside;
                i++;
            }
        }
    }

    /**
     * handle a player's turn
     * @param godPower current player's GodPower
     * @param board current game state
     */
    private void performTurn(GodPower godPower, Board board) {
        theView.startTurn(godPower.getPlayer().getNickname());
        godPower.activeWorkers(board);
        if(godPower.getPlayer().isStuck()) {
            theView.looser(godPower.getPlayer().getNickname());
            playerList.remove(godPower.getPlayer());
            if (playerList.size() == 1)
                theView.endGame(playerList.get(0).getNickname());
        }
        else {
            theView.MovePhase();
            godPower.moveBehaviour(board);
            if(godPower.getPlayer().isWinner())
                theView.endGame(godPower.getPlayer().getNickname());
            else {
                theView.BuildPhase();
                godPower.buildBehaviour(board);
                theView.endTurn();
               }
            }
    }

    /**
     * Check if a nickname is already taken
     * @param playerList list of players
     * @return true if the nickname is taken
     */
    private boolean takenNickname(List<Player> playerList) {
        for(Player player : playerList) {
            if(player.getNickname().equals(userInputManager.getNickname()))
                return true;
        }
        return false;
    }

}
