package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GodPower {
    final ActionManager am = new ActionManager();
    TurnPhase affectPhase = TurnPhase.NONE;
    List<TurnPhase> phases = new ArrayList<>();
    boolean actionable = true;
    boolean triggered = false;

    /**
     * @param board current board state
     * @param worker chosen worker
     * @return if the godPower is actionable
     */
    public boolean isActionable(Board board, Worker worker) {
        return actionable;
    }

    /**
     * Gets the worker's number if the only available move is guaranteed by the use of the GodPower
     * @param board current board state
     * @param player chosen worker
     * @return no available workers (-1), worker1 (1), worker2 (2), user's choice (0)
     */
    public int godPowerRequired(Board board, Player player) { return -1; }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public TurnPhase getAffectPhase() {
        return affectPhase;
    }

    public List<TurnPhase> getPhases() {
        return Collections.unmodifiableList(phases);
    }

    /**
     * @param phase current phase
     * @return number of phases of the given type
     */
    public int countPhases(TurnPhase phase) {
        int i=0;
        for(TurnPhase turnPhase : phases) {
            if(turnPhase == phase) i++;
        }
        return i;
    }

    /**
     * Modifies the given list of positions applying godPower's constraints that are triggered during opponent's turn
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     */
    public void applyOpponentConstraints(List<Position> positions, Board board, Worker worker) { }

    /**
     * Modifies the given list of positions applying godPower's extenders/constraints that trigger during owner's turn
     * @param positions current list of valid positions
     * @param board current board state
     * @param worker chosen worker
     * @param phase current phase
     */
    public void applyEffect(List<Position> positions, Board board, Worker worker, TurnPhase phase) { }

    /**
     * @return true if current turn phase needs to be executed with the Player's other worker
     */
    public boolean switchWorker() { return false; }

    /**
     * Adds a phase to owner's turn
     */
    public void addPhase() { }

    /**
     * Resets to initial status
     */
    public void reset() {
        triggered = false;
    }

    /**
     * Default strategy for MOVE: sets worker position equals to the input cell
     * @param worker worker that the player wants to move
     * @param board board state
     * @param row selected by the player where the worker will move
     * @param column selected by the player where the worker will move
     */
    public void move(Worker worker, Board board, int row, int column) {
        if (row != worker.getRow() || column != worker.getColumn()) {
            // Delete worker from the cell it is situated
            board.getCell(worker.getRow(), worker.getColumn()).detachWorker();

            // Add worker to the cell it has to move to and update worker's position
            worker.setPosition(board, row, column);
        }
    }

    /**
     * Default strategy for BUILD: builds a level on the input cell
     * @param board board state
     * @param row where the player wants to build
     * @param column where the player wants to build
     */
    public void build(Board board, int row, int column) {
        board.getCell(row, column).addLevel();
    }

    /**
     * Default strategy for WIN CONDITION:
     * checks player's win condition comparing the position of the worker
     * that is going to move with the position chosen for the movement
     * @param startCell worker present cell
     * @param endCell worker future cell
     */
    public boolean checkWinCondition (Cell startCell, Cell endCell) {
        return startCell.getLevel() == 2 && endCell.getLevel() == 3;
    }

}