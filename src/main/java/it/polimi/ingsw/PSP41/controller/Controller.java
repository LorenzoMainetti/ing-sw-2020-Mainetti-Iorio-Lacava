package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.*;
import it.polimi.ingsw.PSP41.server.VirtualView;

import java.util.Collections;
import java.util.List;

/**
 * Controller class that handles the game logic
 */
public class Controller {
    private final Board board;
    private final UserInputManager uim;
    private final VirtualView virtualView;
    private List<Player> activePlayers;
    private final ActionManager am;
    private Worker currWorker;

    public Controller(Board board, UserInputManager uim, VirtualView virtualView, List<Player> activePlayers) {
        this.board = board;
        this.uim = uim;
        this.virtualView = virtualView;
        this.activePlayers = activePlayers;
        am = new ActionManager();
    }

    /**
     * Set Worker initial Positions for each player
     */
    public void setWorkers() {
        virtualView.emptyBoard(board);

        for (Player player : activePlayers) {
            virtualView.setCurrPlayer(player.getNickname());
            virtualView.startTurn();

            //TODO some advanced gods might affect setup

            // set position worker 1
            checkWorkerPos(player.getWorker1());
            // set position worker 2
            checkWorkerPos(player.getWorker2());
        }
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

    /**
     * handle turn shifts
     */
    public void play() {

        outside:
        while(activePlayers.size() > 1) {
            for(Player player : activePlayers) {
                performTurn(player);
                if(player.isWinner())
                    break outside;
                if(player.isStuck())
                    break;
            }

            //if the second player in the list is stuck, reverse the list before removing it (for a 3 players game)
            if(activePlayers.get(1).isStuck())
                Collections.reverse(activePlayers);

            //remove the player that is stuck and set the winner if there is one player left
            activePlayers.removeIf(Player::isStuck);
            if(activePlayers.size() == 1) {
                activePlayers.get(0).setWinner(true);
                break;
            }

        }
    }

    /**
     * handle a player's turn
     * @param player current player
     */
    private void performTurn(Player player) {

        virtualView.setCurrPlayer(player.getNickname());
        virtualView.startTurn();

        activeWorkers(player);

        if(player.getGodCard().getAffectPhase().equals(TurnPhase.BEFORE_MOVE)) {
            activePower(player);

            //for gods that affects the entire turn structure
            for(TurnPhase phase : player.getGodCard().getPhases()) {
                switch (phase) {
                 case MOVE:
                     if (!player.isStuck()) {
                        virtualView.movePhase();
                        movePhase(player);
                     }
                     break;

                 case BUILD:
                     if (!player.isWinner()) {
                        virtualView.buildPhase();
                        buildPhase(player);
                    }
                     break;
                }
            }
        }
        else {
            if (!player.isStuck()) {
                virtualView.movePhase();
                movePhase(player);

                applyMorePhases(player, TurnPhase.MOVE);

                if (!player.isWinner()) {

                    if (player.getGodCard().getAffectPhase().equals(TurnPhase.BEFORE_BUILD))
                        activePower(player);

                    virtualView.buildPhase();
                    buildPhase(player);

                    applyMorePhases(player, TurnPhase.BUILD);
                }
            }
        }
        //at the end of each turn reset godPower's trigger
        player.getGodCard().setTriggered(false);
        virtualView.endTurn();
    }


    /**
     * First Phase: CHOOSE WORKER
     * management of the worker choice at the start of a turn:
     * the player can only choose workers that are able to move,
     * if both workers are blocked the player can check if his GodPower can be used
     * @param player current Player
     */
    private void activeWorkers(Player player) {

        int w = player.getGodCard().godPowerRequired(board, player);
        //apply opponents' constraints in this phase too, because a worker might be not selectable after a constraint
        List<Position> movesW1 = am.getValidMoves(board, player.getWorker1().getRow(), player.getWorker1().getColumn());
        List<Position> movesW2 = am.getValidMoves(board, player.getWorker2().getRow(), player.getWorker2().getColumn());
        for(Player p : activePlayers) {
            if (!p.equals(player) && p.getGodCard().getAffectPhase().equals(TurnPhase.MOVE))
                p.getGodCard().applyOpponentConstraints(movesW1, board, currWorker);
                p.getGodCard().applyOpponentConstraints(movesW2, board, currWorker);
        }

        if (movesW1.isEmpty() && movesW2.isEmpty()) {

            if(w == -1) {
                // Detach worker from the cells
                board.getCell(player.getWorker1().getRow(), player.getWorker1().getColumn()).detachWorker();
                board.getCell(player.getWorker2().getRow(), player.getWorker2().getColumn()).detachWorker();
                player.setStuck(true, board);
            }
            else
                poweredActiveWorkers(player, w);

        }
        else if (movesW1.isEmpty()) {

            if(w == -1 || w == 2)
                currWorker = player.getWorker2();
            else
                askWorkerNum(player);
        }
        else if (movesW2.isEmpty()) {

            if(w == -1 || w == 1)
                currWorker = player.getWorker1();
            else
                askWorkerNum(player);
        }
        else {
           askWorkerNum(player);
        }
    }

    /**
     * In some cases, the player has to use the worker that doesn't make him lose
     * @param player current Player
     * @param w pre calculated return value of getPowerRequired
     */
    private void poweredActiveWorkers(Player player, int w) {

        if(w == 1)
            currWorker = player.getWorker1();
        else if(w == 2)
            currWorker = player.getWorker2();
        else
            askWorkerNum(player);
    }

    /**
     * tells VirtualView to ask for worker number and set the answer
     * @param player current Player
     */
    private void askWorkerNum(Player player) {
        virtualView.requestWorkerNum();

        if (uim.isChosenWorker())
            currWorker = player.getWorker1();
        else
            currWorker = player.getWorker2();
    }

    /**
     * Second Phase: MOVE
     * handle a Player's move phase applying opponent's or his godPowers in case they are active
     * @param player current Player
     */
    private void movePhase(Player player) {

        List<Position> moves = am.getValidMoves(board, currWorker.getRow(), currWorker.getColumn());

        player.getGodCard().applyEffect(moves, board, currWorker, TurnPhase.MOVE);
        for(Player p : activePlayers) {
            if (!p.equals(player) && p.getGodCard().getAffectPhase().equals(TurnPhase.MOVE))
                p.getGodCard().applyOpponentConstraints(moves, board, currWorker);
        }

        uim.readChosenDirection(moves);
        int oldRow = currWorker.getRow();
        int oldColumn = currWorker.getColumn();
        int newRow = uim.getChosenRow();
        int newColumn = uim.getChosenColumn();

        player.move(currWorker, board, newRow, newColumn);

        if(player.getGodCard().checkWinCondition(board.getCell(oldRow, oldColumn), board.getCell(newRow, newColumn)))
            player.setWinner(true);

        //TODO some advanced gods can affect current player win condition
    }

    /**
     * Third Phase: BUILD
     * handle a Player's build phase applying opponent's or his godPowers in case they are active
     * @param player current Player
     */
    private void buildPhase(Player player) {

        List<Position> builds = am.getValidBuilds(board, currWorker.getRow(), currWorker.getColumn());

        player.getGodCard().applyEffect(builds, board, currWorker, TurnPhase.BUILD);
        for(Player p : activePlayers) {
            if (!p.equals(player) && p.getGodCard().getAffectPhase().equals(TurnPhase.BUILD))
                p.getGodCard().applyOpponentConstraints(builds, board, currWorker);
        }

        uim.readChosenDirection(builds);
        player.build(board, uim.getChosenRow(), uim.getChosenColumn());

    }

    /**
     * apply any additional phase
     * @param player current Player
     * @param turnPhase current turnPhase
     */
    private void applyMorePhases(Player player, TurnPhase turnPhase) {

        if (player.getGodCard().getAffectPhase().equals(turnPhase))
            activePower(player);

        int i=1;
        while (i != player.getGodCard().countPhases(turnPhase)) {
            if (player.getGodCard().getAffectPhase().equals(turnPhase) && i!=1)
                activePower(player);

            if(player.getGodCard().isTriggered()) {
                if(turnPhase == TurnPhase.MOVE) {
                    virtualView.movePhase();
                    movePhase(player);
                }

                if(turnPhase == TurnPhase.BUILD) {
                    if(player.getGodCard().switchWorker() && i==1)
                        applySwitchWorker(player);

                    virtualView.buildPhase();
                    buildPhase(player);
                }
            }
            else
                break;
            i++;
        }
    }

    /**
     * switch the current Worker with the other one of the same Player
     * @param player current Player
     */
    private void applySwitchWorker(Player player) {
        if(currWorker.equals(player.getWorker1()))
            currWorker = player.getWorker2();
        else
            currWorker = player.getWorker1();
    }

    /**
     * Special Phase: POWER ACTIVATION
     * handle power activation asking user if needed
     * @param player current Player
     */
    private void activePower(Player player) {
        if(player.getGodCard().isActionable(board, currWorker)) {
                virtualView.requestActivatePow();
                player.getGodCard().setTriggered(uim.isPower());
        }
        player.getGodCard().addPhase();
    }


}
