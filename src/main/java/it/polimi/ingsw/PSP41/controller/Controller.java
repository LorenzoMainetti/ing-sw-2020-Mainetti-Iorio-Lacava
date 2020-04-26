package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.ViewObserver;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Position;
import it.polimi.ingsw.PSP41.server.VirtualView;

import java.io.IOException;
import java.util.List;

/**
 * Controller class that handles the game logic
 */
public class Controller implements ViewObserver {
    private final Board board;
    private VirtualView virtualView;
    List<GodPower> activeGodList;

    public Controller(Board board, VirtualView virtualView, List<GodPower> activeGodList) {
        this.board = board;
        this.virtualView = virtualView;
        this.activeGodList = activeGodList;
    }

    public void setWorkers() throws IOException {
        for (GodPower godPower : activeGodList) {
            //da fare con UserInputManager
            // Posiziono worker 1
            Position position = virtualView.socketAskInitialPosition(godPower.getPlayer().getNickname(), 1, board);
            godPower.getPlayer().getWorker1().setPosition(board, position.getPosRow(), position.getPosColumn());

            // Posiziono worker 2
            position = virtualView.socketAskInitialPosition(godPower.getPlayer().getNickname(), 2, board);
            godPower.getPlayer().getWorker2().setPosition(board, position.getPosRow(), position.getPosColumn());
        }
    }

    /**
     * setup game and handle turn shifts
     */
    public void play() throws IOException {

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
    private void performTurn(GodPower godPower, Board board) throws IOException {
        virtualView.startTurn(godPower.getPlayer().getNickname());
        godPower.activeWorkers(board/*, virtualView*/);
        if(godPower.getPlayer().isStuck()) {
            // Rimuove (dalla virtual view) sia il client dalla lista di quelli in gioco, sia (nel controller) il god power dalla lista di quelli attivi
            virtualView.loser(godPower.getPlayer().getNickname());
            activeGodList.removeIf(god -> god.getPlayer().getNickname().equals(godPower.getPlayer().getNickname()));
            if (activeGodList.size() == 1)
                virtualView.endGame(activeGodList.get(0).getPlayer().getNickname());
        }
        else {
            virtualView.MovePhase();
            godPower.moveBehaviour(board);
            if(godPower.getPlayer().isWinner())
                virtualView.endGame(godPower.getPlayer().getNickname());
            else {
                virtualView.BuildPhase();
                godPower.buildBehaviour(board);
                virtualView.endTurn(godPower.getPlayer().getNickname());
               }
            }
    }

    //sicuri che servano?
    @Override
    public void updateWorker(boolean chosenWorker) {

    }

    @Override
    public void updatePower(boolean power) {

    }

    @Override
    public void updateDirection(String direction) {

    }

}
