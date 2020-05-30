package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.client.NetworkHandler;
import it.polimi.ingsw.PSP41.view.GUIPackage.GUI;
import javafx.application.Application;

public class GUIApp {
//non serve
    public static void main(String[] args) {

        GUI gui = new GUI();
        NetworkHandler networkHandler = new NetworkHandler("127.0.0.1", "9090", gui);

        new Thread(networkHandler).start();
        Application.launch(GUI.class);

        //String ip = gui.askIP();
        //String port = gui.askPort();

        //Open a connection to the server

        //add the observers
        //gui.addObserver(networkHandler);

    }
}
