package it.polimi.ingsw.PSP41.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Message class used to send a list of gods names
 */
public class ChooseGodMessage implements Serializable {
    private static final long serialVersionUID = 8726352055567562839L;

    private final String type;
    private final List<String> godList;

    public ChooseGodMessage(String type, List<String> gods) {
        this.type = type;
        godList = gods;
    }

    public List<String> getGodList() {
        return Collections.unmodifiableList(godList);
    }

    public String getType() {
        return type;
    }
}
