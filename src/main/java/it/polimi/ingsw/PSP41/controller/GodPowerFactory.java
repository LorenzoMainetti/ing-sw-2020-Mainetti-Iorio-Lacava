package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;

//alternativa: questa è un interfaccia e faccio una concreteFactory per God
public class GodPowerFactory {

    public GodPower createGodPower(String godName, Player owner, ActionManager am, UserInputManager uim) {
        GodPower godPower = null;

        switch (godName) {
            case "Apollo":
                return new Apollo(owner, am, uim);

            case "Artemis":
                return new Artemis(owner, am, uim);

            case "Athena":
                return new Athena(owner, am, uim);

            case "Atlas":
                return new Atlas(owner, am, uim);

            case "Demeter":
                return new Demeter(owner, am, uim);

            case "Hephaestus":
                return new Hephaestus(owner, am, uim);

            case "Minotaur":
                return new Minotaur(owner, am, uim);

            case "Pan":
                return new Pan(owner, am, uim);

            case "Prometheus":
                return new Prometheus(owner, am, uim);

            default:
                return null;
        }
    }
}
