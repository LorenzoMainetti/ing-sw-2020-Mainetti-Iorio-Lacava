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

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

// Se mai volessimo fare multipartita, bisogna togliere gli static e creare un'istanza di lobby nel server, assegnandola direttamente ai clienthandler
public class Lobby {
    private Board board = new Board();
    private static VirtualView virtualView = new VirtualView();
    private static UserInputManager userInputManager = new UserInputManager(virtualView);
    private static Map<Player, ClientHandler> clients = new HashMap<>();
    private static int playersNumber = 0;
    private static List<String> playersName = new ArrayList<>();
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private static List<String> chosenGods = new ArrayList<>();
    private static Player godLike;
    private static List<GodPower> gods = new ArrayList<>();
    private GodPowerFactory godFactory = new GodPowerFactory();

    public synchronized void setPlayersNumber(ClientHandler client) throws IOException {

        client.getSocketOut().writeObject(startTurnMessage);
        virtualView.requestPlayersNum(client);
        playersNumber = userInputManager.getPlayersNumber();
        notifyAll();
        System.out.println("[SERVER] The game will have " + playersNumber + " players");
        client.getSocketOut().writeObject(endTurnMessage);

    }

    public synchronized void waitPlayersNumber(ClientHandler client) throws InterruptedException, IOException {

        // Mentre il primo giocatore non ha scelto il numero di giocatori, aspetto
        while (playersNumber == 0) {
            wait();
        }

        if (client.getPosition() > playersNumber) {
            client.getSocketOut().writeObject(fullLobby);
            client.closeConnection();
        }

        else if (client.getPosition() != 1) {
            client.getSocketOut().writeObject("The game will have " + playersNumber + " players");
        }
    }

    public void askNickname(ClientHandler client) throws IOException {

        client.getSocketOut().writeObject(startTurnMessage);

        virtualView.requestNickname(client);
        String nickname = userInputManager.getNickname();

        while (playersName.contains(nickname)) {
            virtualView.errorTakenNickname(client);
            nickname = userInputManager.getNickname();
        }

        // Assegno colore
        Color color;
        if (clients.size() == 0) {
            color = Color.RED;
            System.out.println("[SERVER] " + nickname + " is RED");
            /*for (ClientHandler clientHandler : clients.values()) {
                clientHandler.getSocketOut().writeObject(nickname + " is RED");
            }*/
        } else if (clients.size() == 1) {
            color = Color.BLUE;
            System.out.println("[SERVER] " + nickname + " is BLUE");
            /*for (ClientHandler clientHandler : clients.values()) {
                clientHandler.getSocketOut().writeObject(nickname + " is BLUE");
            }*/
        } else {
            color = Color.YELLOW;
            System.out.println("[SERVER] " + nickname + " is YELLOW");
            /*for (ClientHandler clientHandler : clients.values()) {
                clientHandler.getSocketOut().writeObject(nickname + " is YELLOW");
            }*/
        }

        playersName.add(nickname);
        // Se il nome è valido, il client viene aggiunto alla lista di giocatori connessi (nella virtual view)
        Player player = new Player(nickname, color);
        // Aggiungo il player alla lista di giocatori in partita
        clients.put(player, client);
        virtualView.addClient(nickname, client);
        System.out.println("[SERVER] " + nickname + " registered!");
        // Manderebbe il messaggio solo a chi è già connesso
        /*for (ClientHandler handler : clients.values()) {
            handler.getSocketOut().writeObject(nickname + " joins the lobby");
        }*/

        // Virtual view osserva player e rispettivi worker (model)
        player.addObserver(virtualView);
        player.getWorker1().addObserver(virtualView);
        player.getWorker2().addObserver(virtualView);

        client.getSocketOut().writeObject(endTurnMessage);

    }

    public synchronized void setGodLike(ClientHandler client) throws IOException, InterruptedException {

        // Se non si è collegato il numero di giocatori stabilito, aspetto
        if (clients.size() < playersNumber) {
            client.getSocketOut().writeObject("Wait for the players to join...");
            // TODO timeout
        }

        if (clients.size() == playersNumber) {
            for (ClientHandler clientHandler : clients.values()) {
                for (Player player : clients.keySet()) {
                    clientHandler.getSocketOut().writeObject(player.getNickname() + " is " + player.getColor());
                }
                clientHandler.getSocketOut().writeObject("The lobby is full, the game will start soon...");
            }

            Thread.sleep(3000); // Busy waiting
            List<Player> list = new ArrayList<>(clients.keySet());
            Collections.shuffle(list);

            Map<Player, ClientHandler> shuffleMap = new LinkedHashMap<>();
            for (Player key: list) {
                shuffleMap.put(key, clients.get(key));
            }
            for (Player ch : shuffleMap.keySet()) {
                addPlayer(shuffleMap.get(ch), ch);
            }
        }

    }

    //Players adder
    public synchronized void addPlayer(ClientHandler client, Player player) throws IOException {

        client.getSocketOut().writeObject(startTurnMessage);

        if (chosenGods.size() == 0) {
            godLike = player;
            for (ClientHandler clientHandler : clients.values()) {
                clientHandler.getSocketOut().writeObject(player.getNickname() + " is the most godlike! " + player.getNickname() + " is the challenger!");
            }
            client.getSocketOut().writeObject("Choose " + playersNumber + " gods from the ones available");
            client.getSocketOut().writeObject(godDeckMessage);

            String selectedGod;
            for (int i = 1; i <= playersNumber; i++) {
                client.getSocketOut().writeObject("God #" + i + ": ");
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

        // Selezione god power da quelli scelti dal challenger (il challenger sceglie per primo)
        else {
            client.getSocketOut().writeObject("Choose a god power from the ones chosen by the challenger:");
            for (String chosenGod : chosenGods) {
                client.getSocketOut().writeObject(chosenGod);
            }
            virtualView.requestInfo(client);
            String godName = client.getSocketIn().readLine().toUpperCase();
            if (godName.equals("OLIMPIA")) {
                for (ClientHandler clientHandler : clients.values()) {
                    clientHandler.getSocketOut().writeObject(player.getNickname() + " has very good taste in women and wins the game!");
                    // Chiudo connessione coi client
                    clientHandler.closeConnection();
                }
            } else {
                while (!chosenGods.contains(godName)) {
                    client.getSocketOut().writeObject(godName + " is not valid, please select a different god power from this list:");
                    for (String chosenGod : chosenGods) {
                        client.getSocketOut().writeObject(chosenGod);
                    }
                    virtualView.requestInfo(client);
                    godName = client.getSocketIn().readLine().toUpperCase();
                }

                // Aggiungo god (e rispettivo player) alla lista di giocatori da mandare al controller
                gods.add(godFactory.createGodPower(godName, player, userInputManager));
                chosenGods.remove(godName);
                System.out.println("[SERVER] " + player.getNickname() + " will use " + godName);
                for (ClientHandler clientHandler : clients.values()) {
                    clientHandler.getSocketOut().writeObject(player.getNickname() + " has chosen " + godName);
                }

                if(chosenGods.size() == 1) {
                    gods.add(godFactory.createGodPower(chosenGods.get(0), godLike, userInputManager));
                    System.out.println("[SERVER] " + playersName.get(0) + " will use " + chosenGods.get(0));
                    for (ClientHandler clientHandler : clients.values()) {
                        clientHandler.getSocketOut().writeObject(playersName.get(0) + " gets " + chosenGods.get(0));
                    }
                }

                client.getSocketOut().writeObject(endTurnMessage);
            }
        }

        if (chosenGods.size() == 1) {
            // TODO il challenger sceglie chi inizia a giocare (anche se stesso)
            Collections.shuffle(gods);
            Controller controller = new Controller(board, userInputManager, virtualView, gods);
            // Inizio la partita: prima faccio scegliere la posizione iniziale dei worker ad ogni giocatore, poi inizio lo svolgimento dei turni
            controller.setWorkers();
            controller.play();
            //Manca altro?
        }

    }
}
