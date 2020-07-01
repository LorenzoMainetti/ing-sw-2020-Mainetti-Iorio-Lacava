package it.polimi.ingsw.PSP41.utils;

import it.polimi.ingsw.PSP41.model.Color;

import java.io.Serializable;

/**
 * Message class used to send a Player's nickname, color and god card's name
 */
public class PlayersInfoMessage implements Serializable {
    private static final long serialVersionUID = 7990721910806348594L;

    private final String nickname;
    private final Color color;
    private final String godName;


    public PlayersInfoMessage(String nickname, Color color, String godName) {
        this.nickname = nickname;
        this.color = color;
        this.godName = godName;
    }

    public String getPlayerName() {
        return nickname;
    }

    public Color getPlayerColor() {
        return color;
    }

    public String getGodName() {
        return godName;
    }

}