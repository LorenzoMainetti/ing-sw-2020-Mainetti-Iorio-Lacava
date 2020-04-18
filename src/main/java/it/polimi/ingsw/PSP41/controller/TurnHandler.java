package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;

import java.util.*;

public class TurnHandler {
    private final List<Player> playerList;
    private final ActionManager actionManager;
    private UserInputManager userInputManager;
    private final Board board;
    //private View theView

    //avrà theView in input
    public TurnHandler(Board board) {
        playerList = new ArrayList<>();
        actionManager = new ActionManager();
        //userInputManager = new UserInputManager();
        this.board = board;
        //this.theView = theView;
    }

    /**
     * setup game and handle turn shifts
     */
    public void play() {
        //seleziona view: Welcome ecc.
        ArrayList<String> godList = new ArrayList<>(Arrays.asList("Apollo", "Artemis", "Athena", "Atlas", "Demeter", "Hephaestus", "Minotaur", "Pan", "Prometheus"));
        GodPowerFactory godFactory = new GodPowerFactory();

        ArrayList<GodPower> activeGodList = new ArrayList<>();
        //seleziona view: askNickname()
        playerList.add(new Player("name1", Color.RED));
        playerList.add(new Player("name2", Color.BLUE));
        //timeout -> ask for 2 o 3 players
        //if (3players) playersList.add(new Player("name3", Color.YELLOW));
        for(Player player : playerList) {
            Collections.shuffle(godList);
            String godName = godList.get(0);
            godList.remove(0);
            activeGodList.add(godFactory.createGodPower(godName, player, actionManager, userInputManager));
            //seleziona view: ask position worker1
            player.getWorker1().setPosition(board, userInputManager.getChosenRow(), userInputManager.getChosenColumn());
            //seleziona view: ask position worker2
            player.getWorker2().setPosition(board, userInputManager.getChosenRow(), userInputManager.getChosenColumn());
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
        //seleziona view: print "is your turn" (start turn)
        godPower.activeWorkers(board);
        //if(godPower.getPlayer().isStuck())
            //seleziona view: "'name' got stuck! Game over!"
            //playerList.remove(godPower.getPlayer());
            //if(playerList.size()==1)
               //seleziona view: theView.endGame(playerList.get(0).getNickname();
        //else
        godPower.moveBehaviour(board);
        //if(godPower.getPlayer().isWinner())
            //seleziona view: theView.endGame(godPower.getPlayer().getNickname());
        //else
            godPower.buildBehaviour(board);
            //seleziona view: end turn
    }

}
