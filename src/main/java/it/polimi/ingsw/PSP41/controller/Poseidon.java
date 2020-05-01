package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;
import java.util.List;

public class Poseidon extends GodPower {

    public Poseidon(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normal management of active workers inherited from GodPower

    // Normal move inherited from GodPower

    /**
     * End of your turn: If your Worker that hasn't been moved is at ground 0, it can build up to three times
     * @param board current board state
     */
    //TODO devo aggiornare otherBuildCell dopo ogni build (potrebbe aver costruito una dome)
    @Override
    public void buildBehaviour(Board board) {
        /*Worker otherWorker;

        // Normal behaviour of a worker's builds
        uim.readChosenDirection(am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn()), currWorker.getRow(), currWorker.getColumn());
        player.build(board, uim.getChosenRow(), uim.getChosenColumn());

        if(uim.isChosenWorker())
            otherWorker = player.getWorker2();
        else
            otherWorker = player.getWorker1();

        // Ask power activation only if the worker that hasn't been moved is at level zero
        if(board.getCell(otherWorker.getRow(), otherWorker.getColumn()).getLevel() == 0)
        {
            uim.readPower();
            // If the power is active, build three times with unmoved worker
            if (uim.isPower()) {
                List<Position> otherBuildCells = am.getValidBuilds(board, otherWorker.getRow(), otherWorker.getColumn());

                uim.readChosenDirection(otherBuildCells, otherWorker.getRow(), otherWorker.getColumn());
                player.build(board, uim.getChosenRow(), uim.getChosenColumn());
                 if(uim.askBuild()){
                     uim.readChosenDirection(otherBuildCells, otherWorker.getRow(), otherWorker.getColumn());
                     player.build(board, uim.getChosenRow(), uim.getChosenColumn());
                     if(uim.askBuild()){
                         uim.readChosenDirection(otherBuildCells, otherWorker.getRow(), otherWorker.getColumn());
                         player.build(board, uim.getChosenRow(), uim.getChosenColumn());
                     }
                 }
            }
        } */
    }

    @Override
    public String toString() {
        return ("If your unmoved worker is on the ground level, it may build up to three times.");
    }

}
