package it.polimi.ingsw.PSP41.utils;

public class PlayerMessage {
    private String type;
    private String player;

    public PlayerMessage(String type, String player) {
        this.type = type;
        this.player = player;
    }

    public String getType() {
        return type;
    }

    public String getPlayer() {
        return player;
    }
}
