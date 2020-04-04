package it.polimi.ingsw.PSP41;

import java.util.List;

public class UserInputManager {

    private boolean chosenWorker;
    private boolean power;
    private int chosenRow;
    private int chosenColumn;

    public boolean isChosenWorker() {
        return chosenWorker;
    }

    // Setter del worker scelto dal giocatore
    public void readChosenWorker() {
        //this.chosenWorker = scelta del giocatore del worker da usare
    }

    public boolean isPower() {
        return power;
    }

    // Normale setter del potere del god
    public void setPower(boolean power) {
        this.power = power;
    }

    // Setter del potere del god a seconda della scelta del giocatore
    public void readPower() {
        //this.power = scelta del giocatore di attivare o no il potere del god
    }

    public int getChosenRow() {
        return chosenRow;
    }

    public int getChosenColumn() {
        return chosenColumn;
    }

    // Setta riga e colonna della cella scelta dal giocatore
    public void readChosenCell(List<Cell> cellList) {
        //this.chosenRow = riga della cella scelta dal giocatore
        //this.chosenColumn = colonna della cella scelta dal giocatore
    }

}
