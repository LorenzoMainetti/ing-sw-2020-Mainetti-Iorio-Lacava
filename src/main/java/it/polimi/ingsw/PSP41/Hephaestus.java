package it.polimi.ingsw.PSP41;

public class Hephaestus extends GodPower {

    public Hephaestus(Player player, ActionManager am, UserInputManager uim) {
        this.player = player;
        this.am = am;
        this.uim = uim;
    }

    // Normale gestione worker attivi ereditata da GodPower

    // Normale move ereditata da GodPower

    /**
     * Your Build: Your Worker may build one additional block (not dome) on top of your first block
     * @param board current board state
     */
    @Override
    public void buildBehaviour(Board board) {

        super.buildBehaviour(board);

        // Posso fare la seconda build nella stessa cella solo se non devo costruire una dome
        if (board.getCell(uim.getChosenRow(), uim.getChosenColumn()).getLevel() < 3) {
            uim.readPower();
            if (uim.isPower()) {
                player.build(board, uim.getChosenRow(), uim.getChosenColumn());
            }
        }
    }

}