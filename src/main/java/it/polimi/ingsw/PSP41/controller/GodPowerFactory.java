package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;

// Alternative: this is an interface where a concreteFactory is made for each God
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

            case "Hestia":
                return new Hestia(owner, uim);

            case "Zeus":
                return new Zeus(owner, uim);

            case "Triton":
                return new Triton(owner, uim);

            case "Poseidon":
                return new Poseidon(owner, uim);

            case "Ares":
                return new Ares(owner, uim);

            default:
                return null;
        }
    }
}
