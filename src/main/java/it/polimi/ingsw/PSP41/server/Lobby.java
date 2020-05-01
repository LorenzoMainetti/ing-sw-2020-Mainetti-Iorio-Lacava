package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.controller.Controller;
import it.polimi.ingsw.PSP41.controller.GodPower;
import it.polimi.ingsw.PSP41.controller.GodPowerFactory;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

public class Lobby {
    private Board board = new Board();
    private static VirtualView virtualView = new VirtualView();
    private static UserInputManager userInputManager = new UserInputManager(virtualView);
    private static List<ClientHandler> clients = new ArrayList<>();
    private static int playersNumber = 0;
    private static List<String> playersName = new ArrayList<>();
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARES", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "HESTIA", "MINOTAUR", "PAN", "POSEIDON", "PROMETHEUS", "TRITON", "ZEUS");
    private static List<String> chosenGods = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();
    private static List<GodPower> gods = new ArrayList<>();
    private GodPowerFactory godFactory = new GodPowerFactory();
    private static final Object countLock = new Object();

    public static int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(ClientHandler client) throws IOException {

        client.getSocketOut().writeObject(startTurnMessage);
        virtualView.requestPlayersNum(client);
        playersNumber = userInputManager.getPlayersNumber();
        System.out.println("[SERVER] The game will have " + playersNumber + " players");
        client.getSocketOut().writeObject(endTurnMessage);

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
                addPlayer(clientHandler);
            }
        }

    }


    //Players adder
    private void addPlayer(ClientHandler client) throws IOException {

        synchronized (countLock) {

            client.getSocketOut().writeObject(startTurnMessage);

            virtualView.requestNickname(client);
            String nickname = userInputManager.getNickname();

            while (playersName.contains(nickname)) {
                virtualView.errorTakenNickname(client);
                nickname = userInputManager.getNickname();
            }

            // Se il nome è valido, il client viene aggiunto alla lista di giocatori connessi (nella virtual view)
            virtualView.addClient(nickname, client);
            playersName.add(nickname);
            System.out.println("[SERVER] " + nickname + " registered!");
            for (ClientHandler handler : clients) {
                handler.getSocketOut().writeObject(nickname + " joins the lobby");
            }

            // Assegno colore
            Color color;
            if (players.size() == 0) {
                color = Color.RED;
                System.out.println("[SERVER] " + nickname + " is RED");
                for (ClientHandler clientHandler : clients) {
                    clientHandler.getSocketOut().writeObject(nickname + " is RED");
                }
            } else if (players.size() == 1) {
                color = Color.BLUE;
                System.out.println("[SERVER] " + nickname + " is BLUE");
                for (ClientHandler clientHandler : clients) {
                    clientHandler.getSocketOut().writeObject(nickname + " is BLUE");
                }
            } else {
                color = Color.YELLOW;
                System.out.println("[SERVER] " + nickname + " is YELLOW");
                for (ClientHandler clientHandler : clients) {
                    clientHandler.getSocketOut().writeObject(nickname + " is YELLOW");
                }
            }

            // Aggiungo il player alla lista di giocatori in partita
            Player player = new Player(nickname, color);
            players.add(player);
            // Virtual view osserva player e rispettivi worker (model)
            player.addObserver(virtualView);
            player.getWorker1().addObserver(virtualView);
            player.getWorker2().addObserver(virtualView);


            if (playersName.size() == 1) {
                for (ClientHandler clientHandler : clients) {
                    clientHandler.getSocketOut().writeObject(nickname + " is the most godlike! " + nickname + " is the challenger!");
                }
                client.getSocketOut().writeObject("Choose " + playersNumber + " gods from the ones available");
                client.getSocketOut().writeObject(godDeckMessage);

                String selectedGod;
                for (int i = 0; i < playersNumber; i++) {
                    virtualView.requestInfo(client);
                    selectedGod = client.getSocketIn().readLine().toUpperCase();
                    while (!gameGods.contains(selectedGod) || chosenGods.contains(selectedGod)) {
                        client.getSocketOut().writeObject("Invalid god name, choose gods from the ones available");
                        virtualView.requestInfo(client);
                        selectedGod = client.getSocketIn().readLine().toUpperCase();
                    }
                    System.out.println("[SERVER] " + selectedGod + " chosen");
                    chosenGods.add(selectedGod);
                }
                client.getSocketOut().writeObject("Wait for other players to choose their God");
            }

            // Selezione god power da quelli scelti dal challenger (il challenger sceglie per ultimo)
            if(playersName.size() != 1) {
                client.getSocketOut().writeObject("Choose a god power from the ones chosen by the challenger:");
                for (String chosenGod : chosenGods) {
                    client.getSocketOut().writeObject(chosenGod);
                }
                virtualView.requestInfo(client);
                String godName = client.getSocketIn().readLine().toUpperCase();
                if (godName.equals("OLIMPIA")) {
                    for (ClientHandler clientHandler : clients) {
                        clientHandler.getSocketOut().writeObject(nickname + " has very good taste in women and wins the game!");
                        // Chiudo connessione coi client
                        clientHandler.closeConnection();
                    }
                } else {
                    while (!chosenGods.contains(godName)) {
                        client.getSocketOut().writeObject(nickname + " is not valid, please select a different god power from this list:");
                        for (String chosenGod : chosenGods) {
                            client.getSocketOut().writeObject(chosenGod);
                        }
                        virtualView.requestInfo(client);
                        godName = client.getSocketIn().readLine().toUpperCase();
                    }

                    // Aggiungo god (e rispettivo player) alla lista di giocatori da mandare al controller
                    gods.add(godFactory.createGodPower(godName, player, userInputManager));
                    chosenGods.remove(godName);
                    System.out.println("[SERVER] " + nickname + " will use " + godName);
                    for (ClientHandler clientHandler : clients) {
                        clientHandler.getSocketOut().writeObject(nickname + " has chosen " + godName);
                    }

                    if(playersName.size() == playersNumber) {
                        gods.add(godFactory.createGodPower(chosenGods.get(0), players.get(0), userInputManager));
                        System.out.println("[SERVER] " + playersName.get(0) + " will use " + chosenGods.get(0));
                        for (ClientHandler clientHandler : clients) {
                            clientHandler.getSocketOut().writeObject(playersName.get(0) + " gets " + chosenGods.get(0));
                        }
                    }

                    client.getSocketOut().writeObject(endTurnMessage);
                }
            }

            if (players.size() == playersNumber) {
                //TODO il challenger scegli chi inizia a giocare (anche se stesso)
                Collections.shuffle(gods);
                Controller controller = new Controller(board, userInputManager, virtualView, gods);
                // Inizio la partita: prima faccio scegliere la posizione iniziale dei worker ad ogni giocatore, poi inizio lo svolgimento dei turni
                controller.setWorkers();
                controller.play();
            }

        }
    }
}