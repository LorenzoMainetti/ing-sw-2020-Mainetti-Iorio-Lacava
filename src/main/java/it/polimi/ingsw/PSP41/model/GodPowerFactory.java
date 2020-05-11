package it.polimi.ingsw.PSP41.model;

import it.polimi.ingsw.PSP41.model.godCards.*;

public class GodPowerFactory {

    public Player createGodPower(String nickname, Color color, String godName) {

        switch (godName) {
            case "APOLLO":
                return new Player(nickname, color, new Apollo());

            case "ARTEMIS":
                return new Player(nickname, color, new Artemis());

            case "ATHENA":
                return new Player(nickname, color, new Athena());

            case "ATLAS":
                return new Player(nickname, color, new Atlas());

            case "DEMETER":
                return new Player(nickname, color, new Demeter());

            case "HEPHAESTUS":
                return new Player(nickname, color, new Hephaestus());

            case "MINOTAUR":
                return new Player(nickname, color, new Minotaur());

            case "PAN":
                return new Player(nickname, color, new Pan());

            case "PROMETHEUS":
                return new Player(nickname, color, new Prometheus());

            case "HESTIA":
                return new Player(nickname, color, new Hestia());

            case "ZEUS":
                return new Player(nickname, color, new Zeus());

            case "TRITON":
                return new Player(nickname, color, new Triton());

            case "POSEIDON":
                return new Player(nickname, color, new Poseidon());

            case "ARES":
                return new Player(nickname, color, new Ares());

            default:
                return new Player(nickname, color, new Default());
        }
    }
}
