package it.polimi.ingsw.PSP41.utils;

import it.polimi.ingsw.PSP41.model.Position;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PositionMessage implements Serializable {

    private static final long serialVersionUID = 3772134586452877455L;
    private final List<Position> validPos;

    public PositionMessage(List<Position> validPos) {
        this.validPos = validPos;
    }

    public List<Position> getValidPos() {
        return Collections.unmodifiableList(validPos);
    }

}
