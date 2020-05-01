package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Worker;
import it.polimi.ingsw.PSP41.server.VirtualView;

import java.util.List;

/**
 * Controller class that handles the game logic
 */
public class Controller {
    private final Board board;
    private UserInputManager uim;
    private VirtualView virtualView;
    private List<GodPower> activeGodList;

    public Controller(Board board, UserInputManager uim, VirtualView virtualView, List<GodPower> activeGodList) {
        this.board = board;
        this.uim = uim;
        this.virtualView = virtualView;
        this.activeGodList = activeGodList;
    }

    /**
     * Set Worker initial Positions for each player
     */
    public void setWorkers() {
        for (GodPower godPower : activeGodList) {
            virtualView.setCurrPlayer(godPower.getPlayer().getNickname());
            virtualView.startTurn();

            // set position worker 1
            checkWorkerPos(godPower.getPlayer().getWorker1());

            // set position worker 2
            checkWorkerPos(godPower.getPlayer().getWorker2());

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
    //TODO gestire rimozione player/client
    private void performTurn(GodPower godPower, Board board) {

        virtualView.setCurrPlayer(godPower.getPlayer().getNickname());
        virtualView.startTurn();
        godPower.activeWorkers(board);

        boolean isStuck = godPower.getPlayer().isStuck();

        activeGodList.removeIf(godPower1 -> godPower1.getPlayer().isStuck());
        if(activeGodList.size() == 1)
            activeGodList.get(0).getPlayer().setWinner(true);

        if(!isStuck) {
            virtualView.movePhase();
            godPower.moveBehaviour(board);

            if(!godPower.getPlayer().isWinner()) {
                virtualView.buildPhase();
                godPower.buildBehaviour(board);
            }
        }
        virtualView.endTurn();
    }

    /**
     * Set a Worker initial Position and handle any possible exception
     * @param worker worker to be set
     */
    private void checkWorkerPos(Worker worker) {
        boolean illegal = true;

        while (illegal) {

            virtualView.requestInitPos();

            try {
                worker.setPosition(board, uim.getChosenRow(), uim.getChosenColumn());
                illegal = false;
            } catch (IllegalStateException e) {
                virtualView.errorTakenPosition();
            }
        }
    }


}
