package it.polimi.ingsw.PSP41.controller;

import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.ActionManager;

// Alternative: this is an interface where a concreteFactory is made for each God
public class GodPowerFactory {

    public GodPower createGodPower(String godName, Player owner, UserInputManager uim) {

        switch (godName) {
            case "APOLLO":
                return new Apollo(owner, uim);

            case "ARTEMIS":
                return new Artemis(owner, uim);

            case "ATHENA":
                return new Athena(owner, uim);

            case "ATLAS":
                return new Atlas(owner, uim);

            case "DEMETER":
                return new Demeter(owner, uim);

            case "HEPHAESTUS":
                return new Hephaestus(owner, uim);

            case "MINOTAUR":
                return new Minotaur(owner, uim);

            case "PAN":
                return new Pan(owner, uim);

            case "PROMETHEUS":
                return new Prometheus(owner, uim);

            case "HESTIA":
                return new Hestia(owner, uim);

            case "ZEUS":
                return new Zeus(owner, uim);

            case "TRITON":
                return new Triton(owner, uim);

            case "POSEIDON":
                return new Poseidon(owner, uim);

            case "ARES":
                return new Ares(owner, uim);

            default:
                return null;
        }
    }
}
