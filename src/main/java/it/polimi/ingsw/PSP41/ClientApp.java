package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.client.NetworkHandler;
import it.polimi.ingsw.PSP41.view.CLI;

import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("IP address of server?");
        String ip = scanner.nextLine();
        System.out.println("Port?");
        String port = scanner.nextLine();

        CLI cli = new CLI();
        new Thread(cli).start();

        //Open a connection to the server
        NetworkHandler networkHandler = new NetworkHandler(ip, port, cli);

        /*System.out.println("CLI or GUI?");
        String ui = scanner.nextLine().toUpperCase();
        while (!ui.equals("CLI") && !ui.equals("GUI")) {
            System.out.println("Invalid interface, CLI or GUI?");
            ui = scanner.nextLine().toUpperCase();
        }
        if (ui.equals("CLI")) {
            CLI cli = new CLI();
            networkHandler.setCli(cli);
            new Thread(cli).start();
            cli.addObserver(networkHandler);
        }
        //else setto la GUI
        */

        new Thread(networkHandler).start();

    }
}