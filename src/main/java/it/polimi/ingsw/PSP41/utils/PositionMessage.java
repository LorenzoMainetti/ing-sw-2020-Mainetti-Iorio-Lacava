package it.polimi.ingsw.PSP41.utils;

import it.polimi.ingsw.PSP41.model.Position;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PositionMessage implements Serializable {
    private static final long serialVersionUID = 3772134586452877455L;
    List<Position> validPos;
    Position initialPos;

    public PositionMessage(List<Position> validPos, Position initialPos) {
        this.validPos = validPos;
        this.initialPos = initialPos;
    }

    public List<Position> getValidPos() {
        return Collections.unmodifiableList(validPos);
    }

    public Position getInitialPos() {
        return initialPos;
    }
}
