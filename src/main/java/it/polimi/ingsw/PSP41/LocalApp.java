package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.controller.TurnHandler;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.view.CLI;

public class LocalApp {
    public static void main(String[] args) {
        CLI view = new CLI();
        Board boardModel = new Board();
        TurnHandler turnController = new TurnHandler(boardModel, view);
        
        turnController.play();
    }

}
