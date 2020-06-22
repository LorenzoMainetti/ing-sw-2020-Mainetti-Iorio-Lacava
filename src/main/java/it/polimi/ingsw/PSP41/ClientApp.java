package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.client.NetworkHandler;
import it.polimi.ingsw.PSP41.view.CLIPackage.CLI;
import it.polimi.ingsw.PSP41.view.GUIPackage.GUI;
import javafx.application.Application;


public class ClientApp {

    public static void main(String[] args) {

        if(args.length > 0) {
            //start CLI ClientApp
            CLI cli = new CLI();

            NetworkHandler networkHandler = new NetworkHandler(cli);
            cli.addObserver(networkHandler);

            new Thread(networkHandler).start();
            new Thread(cli).start();

        }
        else {
            //start GUI ClientApp
            Application.launch(GUI.class);
        }

    }
}