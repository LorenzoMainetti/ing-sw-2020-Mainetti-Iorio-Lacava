package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;

import java.util.List;

public class Demeter extends GodPower {

    public Demeter(Player player, UserInputManager uim) {
        this.player = player;
        am = new ActionManager();
        this.uim = uim;
    }

    // Normale gestione worker attivi ereditata da GodPower

    // Normale move ereditata da GodPower

    /**
     * Your Build: Your Worker may build one additional time, but not on the same space
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {

        super.buildBehaviour(board);

        // Per attivare il potere è necessario che il worker possa ancora costruire in una delle celle adiacenti esclusa la cella in cui ha già costruito
        List<Position> secondBuildCells = am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn());
        secondBuildCells.removeIf(p -> (p.getX()==uim.getChosenRow() && p.getY()==uim.getChosenColumn()));

        if(!secondBuildCells.isEmpty()) {
            uim.readPower();
            // Se il potere è attivo, faccio una seconda build escludendo la cella della prima build dalle possibili celle in cui costruire
            if (uim.isPower()) {
                uim.readChosenDirection(secondBuildCells, currWorker.getRow(), currWorker.getColumn());
                player.build(board, uim.getChosenRow(), uim.getChosenColumn());
            }
        }
    }

}