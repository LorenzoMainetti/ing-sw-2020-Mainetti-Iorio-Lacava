package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.client.NetworkHandler;
import it.polimi.ingsw.PSP41.view.CLI;

import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //metodo cli.askPort
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();
        System.out.println("Port?");
        String port = scanner.nextLine();

        CLI cli = new CLI();
        new Thread(cli).start();

        //Open a connection to the server
        NetworkHandler networkHandler = new NetworkHandler(ip, port, cli);
        cli.addObserver(networkHandler);

        new Thread(networkHandler).start();

    }
}