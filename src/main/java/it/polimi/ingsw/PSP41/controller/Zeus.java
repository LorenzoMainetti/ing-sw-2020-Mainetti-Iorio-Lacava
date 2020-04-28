package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.ActionManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.Position;

import java.util.List;

public class Zeus extends GodPower {

    public Zeus(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers inherited from GodPower

    // Normal move inherited from GodPower

    /**
     * Your Build: Your Worker may build a block under itself
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {
        List<Position> validBuilds;
        Position position = new Position (currWorker.getRow(), currWorker.getColumn());

        // The worker's current position is also a valid build
        validBuilds = this.am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn());
        validBuilds.add(position);

        uim.readChosenDirection(validBuilds, currWorker.getRow(), currWorker.getColumn());
        player.build(board, uim.getChosenRow(), uim.getChosenColumn());
    }

    @Override
    public String toString() {
        return ("Your worker may build a block under itself.");
    }

}
