package it.polimi.ingsw.PSP41.utils;

import java.io.Serializable;

public class NameMessage implements Serializable {
    private static final long serialVersionUID = 5279507548737122174L;

    private final String type;
    private final String name;

    public NameMessage(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
