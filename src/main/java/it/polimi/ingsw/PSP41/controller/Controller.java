package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.controller.GodPower;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.server.VirtualView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class that handles the game logic
 */
public class Controller {
    private Board board;
    private UserInputManager uim;
    private VirtualView virtualView;
    private List<GodPower> activeGodList;

    public Controller(Board board, UserInputManager uim, VirtualView virtualView, List<GodPower> activeGodList) {
        this.board = board;
        this.uim = uim;
        this.virtualView = virtualView;
        this.activeGodList = activeGodList;
    }

    public void setWorkers() {
        for (GodPower godPower : activeGodList) {

            virtualView.setCurrPlayer(godPower.getPlayer().getNickname());
            boolean illegal = true;
            virtualView.startTurn();

            // Posiziono worker 1
            while (illegal) {
                virtualView.requestInitPos();
                try {
                    godPower.getPlayer().getWorker1().setPosition(board, uim.getChosenRow(), uim.getChosenColumn());
                    illegal = false;
                } catch (IllegalStateException e) {
                    virtualView.errorTakenPosition();
                }
            }

            illegal = true;

            // Posiziono worker 2
            while (illegal) {
                virtualView.requestInitPos();
                try {
                    godPower.getPlayer().getWorker2().setPosition(board, uim.getChosenRow(), uim.getChosenColumn());
                    illegal = false;
                } catch (IllegalStateException e) {
                    virtualView.errorTakenPosition();
                }
            }
        }
    }

    /**
     * setup game and handle turn shifts
     */
    public void play() {

        outside:
        while(activeGodList.size() > 1) {
            for(GodPower godPower : activeGodList) {
                performTurn(godPower, board);
                if(godPower.getPlayer().isWinner())
                    break outside;
            }
        }
    }

    /**
     * handle a player's turn
     * @param godPower current player's GodPower
     * @param board current game state
     */
    private void performTurn(GodPower godPower, Board board) {
        virtualView.setCurrPlayer(godPower.getPlayer().getNickname());
        virtualView.startTurn();
        godPower.activeWorkers(board);
        if(godPower.getPlayer().isStuck()) {
            activeGodList.stream().
                    filter(godPower1 -> !godPower.getPlayer().isStuck()).
                    collect(Collectors.toList());
            if (activeGodList.size() == 1)
                activeGodList.get(0).getPlayer().setWinner(true);
        }
        else {
            virtualView.movePhase();
            godPower.moveBehaviour(board);
            if(!godPower.getPlayer().isWinner()) {
                virtualView.buildPhase();
                godPower.buildBehaviour(board);
                virtualView.endTurn();
            }
        }
    }


}