package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;

//alternativa: questa è un interfaccia e faccio una concreteFactory per God
public class GodPowerFactory {

    public GodPower createGodPower(String godName, Player owner, UserInputManager uim) {

        switch (godName) {
            case "Apollo":
                return new Apollo(owner, uim);

            case "Artemis":
                return new Artemis(owner, uim);

            case "Athena":
                return new Athena(owner, uim);

            case "Atlas":
                return new Atlas(owner, uim);

            case "Demeter":
                return new Demeter(owner, uim);

            case "Hephaestus":
                return new Hephaestus(owner, uim);

            case "Minotaur":
                return new Minotaur(owner, uim);

            case "Pan":
                return new Pan(owner, uim);

            case "Prometheus":
                return new Prometheus(owner, uim);

            default:
                return null;
        }
    }
}
