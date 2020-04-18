package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Position;

import java.util.List;

/**
 * Controller class that check and set the user inputs to manage a player's turn
 */
public class UserInputManager implements ViewObserver {

    private boolean chosenWorker;
    private boolean power;
    private int chosenRow;
    private int chosenColumn;
    private String direction;
    private String nickname;
    //NB: aggiunta tmp per i test:
    //per settare una possibile seconda azione
    private int chosenRow2;
    private int chosenColumn2;


    public UserInputManager(boolean chooseWorker, boolean chooseActivation, int chooseRow, int chooseColumn) {
        this.chosenWorker = chooseWorker;
        this.power = chooseActivation;
        this.chosenRow = chooseRow;
        this.chosenColumn = chooseColumn;
    }

    public boolean isChosenWorker() {
        return chosenWorker;
    }

    // Setter del worker scelto dal giocatore
    public void readChosenWorker() {
        //seleziona view: theView.askWorker();
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
        //seleziona view: power = theView.askPowerActivation();
        //this.power = scelta del giocatore di attivare o no il potere del god
    }

    public int getChosenRow() {
        return chosenRow;
    }

    public int getChosenColumn() {
        return chosenColumn;
    }

    // Setta riga e colonna della cella scelta dal giocatore
    public void readChosenCell(List<Position> positions) {
        //seleziona view: displayValidMoves(cellList);
        //seleziona view: this.chosenRow.askRow();
        //seleziona view: this.chosenColumn.askColumn();
        //this.chosenRow = riga della cella scelta dal giocatore
        //this.chosenColumn = colonna della cella scelta dal giocatore
    }

    /*// Setta riga e colonna della cella scelta dal giocatore
    //public void readChosenDirection(List<Position> positions, int row, int column) {
        //seleziona view: displayValidMoves(positions, row, column);
        //seleziona view: theView.askDirection();
        switch(direction) {
        case "N":
            this.chosenRow = row - 1;
            this.chosenColumn = column;
            break;
        case "NE":
            this.chosenRow = row - 1;
            this.chosenColumn = column + 1;
            break;
        case "E":
            this.chosenRow = row;
            this.chosenColumn = column + 1;
            break;
        case "SE":
            this.chosenRow = row + 1;
            this.chosenColumn = column + 1;
            break;
        case "S":
            this.chosenRow = row + 1;
            this.chosenColumn = column;
            break;
        case "SO":
            this.chosenRow = row + 1;
            this.chosenColumn = column - 1;
            break;
        case "O":
            this.chosenRow = row;
            this.chosenColumn = column - 1;
            break;
        case "NO":
            this.chosenRow = row - 1;
            this.chosenColumn = column - 1;
            break;
        default:
            //possibili input errati (oppure faccio il check direttamente nella view
        }
        //this.chosenRow = riga della cella scelta dal giocatore
        //this.chosenColumn = colonna della cella scelta dal giocatore
     } */



    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePosition(int row, int column) {
        chosenRow = row;
        chosenColumn = column;
    }

    public void updateWorker(boolean chosenWorker) {
        this.chosenWorker = chosenWorker;
    }

    public void updatePower(boolean power) {
        this.power = power;
    }

    public void updateDirection(String direction) {
        this.direction = direction;
    }







    //metodi tmp per il testing
    public void setAdditionalPos(int row, int column) {
       this.chosenRow2 = row;
       this.chosenColumn2 = column;
    }

    public int getChosenRow2() { return chosenRow2; }

    public int getChosenColumn2() { return chosenColumn2; }


}
