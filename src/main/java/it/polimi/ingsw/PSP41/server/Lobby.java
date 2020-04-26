package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.controller.Controller;
import it.polimi.ingsw.PSP41.controller.GodPower;
import it.polimi.ingsw.PSP41.controller.GodPowerFactory;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;

import java.io.IOException;
import java.util.*;

public class Lobby {
    private Board board = new Board();
    private static VirtualView virtualView = new VirtualView();
    private static List<ClientHandler> clients = new ArrayList<>();
    private static int lobbyPlayers = 0;
    private static int playersNumber = 0;
    private static List<String> playersName = new ArrayList<>();
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS");
    private List<String> chosenGods = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();
    private static List<GodPower> gods = new ArrayList<>();
    private GodPowerFactory godFactory = new GodPowerFactory();
    private static final Object countLock = new Object();

    public static int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(ClientHandler client) throws IOException {

        client.getSocketOut().writeObject("You are the first player in the lobby, choose the number of players");
        String read = client.getSocketIn().readLine();
        System.out.println(read);
        playersNumber = Integer.parseInt(read);
        System.out.println("[SERVER] The game will have " + playersNumber + " players");
        client.getSocketOut().writeObject("End turn");

    }

    public void setGodLike(ClientHandler client) throws IOException, InterruptedException {

        client.getSocketOut().writeObject("The game will have " + playersNumber + " players");
        clients.add(client);

        // Se non si è collegato il numero di giocatori stabilito, aspetto
        if (clients.size() < playersNumber) {
            client.getSocketOut().writeObject("Wait for the players to join...");
            while (clients.size() < playersNumber) {
                Thread.sleep(1000);
            }
            //timeout
        }

        if (client.getPosition() == playersNumber) {
            for (ClientHandler clientHandler : clients) {
                clientHandler.getSocketOut().writeObject("The lobby is full, the game will start soon...");
            }
            Collections.shuffle(clients);
            for (int i = 1; i < clients.size(); i++) {
                clients.get(i).getSocketOut().writeObject("Wait for your turn...");
            }
            for (ClientHandler clientHandler : clients) {
                lobbyPlayers++;
                addPlayer(clientHandler);
            }
        }

    }


    //Players adder
    public void addPlayer(ClientHandler client) throws IOException, InterruptedException {

        if (lobbyPlayers != 1) {
            client.getSocketOut().writeObject("Wait for your turn");
        }

        synchronized (countLock) {

            client.getSocketOut().writeObject("Turn start!");

            client.getSocketOut().writeObject("What is your name?");
            String nickname = client.getSocketIn().readLine();
            while (playersName.contains(nickname)) {
                client.getSocketOut().writeObject(nickname + " is already taken, please select a different name");
                nickname = client.getSocketIn().readLine();
            }

            // Se il nome è valido, il client viene aggiunto alla lista di giocatori connessi (nella virtual view)
            virtualView.getConnectedClients().put(nickname, client);
            playersName.add(nickname);
            System.out.println("[SERVER] " + nickname + " registered!");
            for (int i = 1; i <= clients.size(); i++) {
                clients.get(i).getSocketOut().writeObject(nickname + " joins the lobby");
            }

            // Assegno colore
            Color color;
            if (players.size() == 0) {
                color = Color.RED;
                System.out.println("[SERVER] " + nickname + " is RED");
                for (int i = 1; i <= clients.size(); i++) {
                    clients.get(i).getSocketOut().writeObject(nickname + " is RED");
                }
            } else if (players.size() == 1) {
                color = Color.BLUE;
                System.out.println("[SERVER] " + nickname + " is BLUE");
                for (int i = 1; i <= clients.size(); i++) {
                    clients.get(i).getSocketOut().writeObject(nickname + " is BLUE");
                }
            } else {
                color = Color.YELLOW;
                System.out.println("[SERVER] " + nickname + " is YELLOW");
                for (int i = 1; i <= clients.size(); i++) {
                    clients.get(i).getSocketOut().writeObject(nickname + " is YELLOW");
                }
            }

            // Aggiungo il player alla lista di giocatori in partita
            Player player = new Player(nickname, color);
            players.add(player);

            if (lobbyPlayers == 1) {
                for (int i = 0; i < clients.size(); i++) {
                    clients.get(i).getSocketOut().writeObject(nickname + " is the most godlike! " + nickname + " is the challenger!");
                }
                client.getSocketOut().writeObject("Choose " + playersNumber + " gods from the ones available (god1,god2)");
                client.getSocketOut().writeObject(gameGods);
                String selectedGod = client.getSocketIn().readLine().toUpperCase();
                String[] inputs = selectedGod.split(",");
                boolean valid = true;
                for (int i = 0; i < playersNumber; i++) {
                    if (!gameGods.contains(inputs[i])) {
                        valid = false;
                        break;
                    }
                }
                while (!valid) {
                    client.getSocketOut().writeObject("Invalid god names, choose " + playersNumber + " gods from the ones available (god1,god2)");
                    selectedGod = client.getSocketIn().readLine().toUpperCase();
                    inputs = selectedGod.split(",");
                    valid = true;
                    for (int i = 0; i < playersNumber; i++) {
                        if (!gameGods.contains(inputs[i])) {
                            valid = false;
                            break;
                        }
                    }
                }
                for (int i = 0; i < playersNumber; i++) {
                    chosenGods.add(inputs[i]);
                    System.out.println("[SERVER] " + inputs[i] + " selected");
                    for (int j = 1; j <= clients.size(); j++) {
                        clients.get(j).getSocketOut().writeObject(inputs[i] + " selected");
                    }
                }
            }

            // Selezione god power da quelli scelti dal challenger (il challenger sceglie per primo)
            client.getSocketOut().writeObject("Choose a god power from the ones available:");
            for (String chosenGod : chosenGods) {
                client.getSocketOut().writeObject(chosenGod);
            }
            String godName = client.getSocketIn().readLine().toUpperCase();
            if (godName.equals("OLIMPIA")) {
                for (ClientHandler clientHandler : clients) {
                    clientHandler.getSocketOut().writeObject(nickname + " has very good taste in women and wins the game!");
                    // Chiudo connessione coi client
                    clientHandler.closeConnection();
                }
            }
            else {
                while (!chosenGods.contains(godName)) {
                    client.getSocketOut().writeObject(nickname + " is not valid, please select a different god power from this list:");
                    for (String chosenGod : chosenGods) {
                        client.getSocketOut().writeObject(chosenGod);
                    }
                    godName = client.getSocketIn().readLine().toUpperCase();
                }

                // Aggiungo god (e rispettivo player) alla lista di giocatori da mandare al controller
                gods.add(godFactory.createGodPower(godName, player, new UserInputManager(virtualView)));
                chosenGods.remove(godName);
                System.out.println("[SERVER] " + nickname + " will use " + godName);
                for (ClientHandler clientHandler : clients) {
                    clientHandler.getSocketOut().writeObject(nickname + " has chosen " + godName);
                }

                // Virtual view osserva player e rispettivi worker (model)
                player.addObserver(virtualView);
                player.getWorker1().addObserver(virtualView);
                player.getWorker2().addObserver(virtualView);

                client.getSocketOut().writeObject("End turn");

                if (players.size() == playersNumber) {
                    // Creo controller e lo rendo observer della virtual view
                    Collections.shuffle(gods);
                    Controller controller = new Controller(board, virtualView, gods);
                    virtualView.addObserver(controller);
                    // Inizio la partita: prima faccio scegliere la posizione iniziale dei worker ad ogni giocatore, poi inizio lo svolgimento dei turni
                    controller.setWorkers();
                    controller.play();
                    //Manca altro?
                }
            }
        }
    }
}