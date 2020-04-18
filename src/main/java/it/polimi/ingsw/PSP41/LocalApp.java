package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.controller.TurnHandler;
import it.polimi.ingsw.PSP41.model.Board;

public class LocalApp {
    public static void main(String[] args) {
        //View theView = new View();
        Board boardModel = new Board();
        TurnHandler turnController = new TurnHandler(boardModel);
        //theView.addObservers(turnController);
        //boardModel.addObservers(theView);

        //theView.run();
    }

}
