package it.polimi.ingsw.PSP41.model.godCards;

import it.polimi.ingsw.PSP41.model.TurnPhase;

/**
 * Default strategies for move and build if there is no godPower card assigned
 */
public class Default extends GodPower {

    public Default() {
        affectPhase = TurnPhase.NONE;
        phases.add(TurnPhase.MOVE);
        phases.add(TurnPhase.BUILD);
    }
}