package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.controller.Controller;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.GodPowerFactory;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.observer.ConnectionObserver;
import it.polimi.ingsw.PSP41.observer.LobbyObservable;
import it.polimi.ingsw.PSP41.utils.ChooseGodMessage;
import it.polimi.ingsw.PSP41.utils.NameMessage;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;

import java.util.*;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

/**
 * Single room for a match: contains clients linked to the match and manages the match creation
 */
public class Lobby extends LobbyObservable implements ConnectionObserver  {
    private final VirtualView virtualView = new VirtualView();
    private final UserInputManager userInputManager = new UserInputManager(virtualView);
    private Map<String, ClientHandler> clientNames = new HashMap<>();
    private Map<String, String> playerGodCard = new HashMap<>();
    private List<ClientHandler> clients = new ArrayList<>();
    private List<String> playersName = new ArrayList<>();
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private final List<String> chosenGods = new ArrayList<>();
    private List<Player> activePlayers = new ArrayList<>();
    private final Object lock = new Object();
    private String challenger;
    private int playersNumber = -1;
    private boolean ready = false;

    /**
     * Removes the current client from the list of connected clients
     * @param client current client
     */
    public synchronized void deregisterConnection(ClientHandler client) {
        System.out.println("[SERVER] Unregistering client...");
        clients.remove(client);
        System.out.println("[SERVER] Done!");
    }

    /**
     * Manages disconnection: if the client disconnected is active, all the clients will be disconnected;
     * else the client disconnected is removed from the server clients log
     * @param client disconnected client
     */
    @Override
    public void updateDisconnection(ClientHandler client) {
        if (client.isActive()) {
            if (client.isConnected()) {
                if (ready) {
                    for (ClientHandler ch : clients) {
                        ch.closeConnection();
                    }
                } else {
                    client.closeConnection();
                    deregisterConnection(client);
                    if (playersNumber == -1) {
                        notifyPlayersNumber(this);
                    }
                }
            }
        }
        else {
            deregisterConnection(client);
        }
    }

    /**
     * Clients addition manager: adds a client in the lobby list and if needed asks playersNumber
     * @param client client added
     */
    public void addClient(ClientHandler client) {
        Thread t = new Thread( () -> {
            client.addObserver(this);
            clients.add(client);

            if (clients.size() != playersNumber) {
                if (clients.size() == 1 && playersNumber == -1) {
                    setPlayersNumber(client);
                } else {
                    client.send(playersNumber);
                    client.send(waitMessage);
                    notifyPlayersNumber(this);
                }
            } else {
                client.send(playersNumber);
                client.send(waitMessage);
            }

            synchronized (lock) {
                lock.notifyAll();
            }
        });
        t.start();
    }

    /**
     * Asks and sets the number of players to the first connected user
     * @param client current client
     */
    private void setPlayersNumber(ClientHandler client) {
        client.send(startTurnMessage);
        virtualView.requestPlayersNum(client);
        playersNumber = userInputManager.getPlayersNumber();
        System.out.println("[SERVER] The game will have " + playersNumber + " players");
        client.send(Integer.valueOf(playersNumber));
        client.send(waitMessage);
        client.send(endTurnMessage);

        notifyPlayersNumber(this);

        synchronized (lock) {
            while (clients.size() != playersNumber) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        ready = true;
        notifyLobbyIsReady();
        setPlayers();
    }

    /**
     * Asks nickname to the players, then starts the creation of the match
     */
    private void setPlayers() {
        for (ClientHandler client : clients) {
            setNickname(client);
        }
        setGodLike();
    }

    /**
     * Asks and sets the current client's nickname
     * @param client current client
     */
    private void setNickname(ClientHandler client) {

        client.send(startTurnMessage);

        virtualView.requestNickname(client);
        String nickname = userInputManager.getNickname();

        while (playersName.contains(nickname)) {
            virtualView.errorTakenNickname(client);
            nickname = userInputManager.getNickname();
        }

        //add user to the list of connected users
        playersName.add(nickname);
        clientNames.put(nickname, client);
        virtualView.addClient(nickname, client);

        System.out.println("[SERVER] " + nickname + " registered!");

        //tells client that it's been registered
        client.send(new NameMessage(acceptedMessage, nickname));

        client.send(waitMessage);
        client.send(endTurnMessage);

    }

    /**
     * Random choice of the challenger from the connected clients
     */
    private void setGodLike() {
        //if block executed only once
        if (chosenGods.size() == 0 && playersNumber == playersName.size()) {
            Collections.shuffle(playersName);

            Map<String, ClientHandler> shuffleMap = new LinkedHashMap<>();
            for (String key : playersName) {
                shuffleMap.put(key, clientNames.get(key));
            }

            setChallenger(shuffleMap.get(playersName.get(0)), playersName.get(0));

            for (String key : shuffleMap.keySet()) {
                setGodCard(shuffleMap.get(key));
            }
        }
    }

    /**
     * Asks the challenger to choose the godCards that will be used in the game
     * @param client challenger's client
     * @param name challenger's nickname
     */
    private void setChallenger(ClientHandler client, String name) {

        client.send(startTurnMessage);
        challenger = name;

        for (ClientHandler clientHandler : clients) {
            if(!clientHandler.equals(client))
                clientHandler.send(waitMessage);

            clientHandler.send(new NameMessage(godLikeMessage, challenger));
        }

        boolean valid = true;

        outside:
        do {
            client.send(new ChooseGodMessage(gameGodsMessage, gameGods));

            String s = client.read();
            String[] selectedGods = s.split("/");

            chosenGods.addAll(Arrays.asList(selectedGods));

            if (gameGods.containsAll(chosenGods)) {
                for (String god : chosenGods) {
                    if (Collections.frequency(chosenGods, god) > 1) {
                        valid = false;
                        chosenGods.clear();
                    }
                    else {
                        break outside;
                    }
                }
            }
            else {
                valid = false;
                chosenGods.clear();
            }
        } while(!valid);

        for (String chosenGod : chosenGods) {
            System.out.println("[SERVER] " + chosenGod + " chosen");
        }

        client.send(endTurnMessage);
    }

    /**
     * Assigns a godCard to the current client (the challenger gets the remaining card)
     * @param client current client
     */
    private void setGodCard(ClientHandler client) {
        if(!client.equals(clientNames.get(challenger))) {
            client.send(startTurnMessage);
            assignGod(client);
            client.send(endTurnMessage);
        }
        else client.send(waitMessage);
    }

    /**
     * Asks and sets the chosen godCard
     * @param client current client
     */
    private void assignGod(ClientHandler client) {

        client.send(new ChooseGodMessage(yourGodMessage, chosenGods));
        String godName = client.read().toUpperCase();
        while (!chosenGods.contains(godName)) {
            client.send(new ChooseGodMessage(yourGodMessage, chosenGods));
            godName = client.read().toUpperCase();
        }
        chosenGods.remove(godName);

        String name = null;
        for (String s : clientNames.keySet()) {
            if (clientNames.get(s).equals(client))
                name = s;
        }

        playerGodCard.put(name, godName);

        client.send(waitMessage);

        if(chosenGods.size()==1) {
            playerGodCard.put(challenger, chosenGods.get(0));
            chosenGods.clear();
            //the challenger creates the game
            createGame(clientNames.get(challenger));
        }
    }

    /**
     * Setups game creating model and controller, then notifies the server that will start the match
     * @param client current client
     */
    private void createGame(ClientHandler client) {

        GodPowerFactory godFactory = new GodPowerFactory();
        Color color = Color.RED;

        for (String name : playerGodCard.keySet()) {
            activePlayers.add(godFactory.createGodPower(name, color, playerGodCard.get(name)));

            virtualView.sendPlayersInfo(new PlayersInfoMessage(name, color, playerGodCard.get(name)));
            color = color.next();
        }

        for (Player player : activePlayers) {
            player.addObserver(virtualView);
            player.getWorker1().addObserver(virtualView);
            player.getWorker2().addObserver(virtualView);
        }

        //the challenger chooses who starts playing
        client.send(startTurnMessage);

        for(String name : clientNames.keySet()) {
            clientNames.get(name).send(new NameMessage(chooseStarterMessage, name));
        }

        String starter = client.read();
        while (!playersName.contains(starter)) {
            client.send(new NameMessage(chooseStarterMessage, challenger));
            starter = client.read();
        }

        client.send(endTurnMessage);
        System.out.println("[SERVER] " + starter + " is the first to play");

        List<Player> sortedPlayers = new ArrayList<>();

        if(activePlayers.get(0).getNickname().equals(starter))
            sortedPlayers = activePlayers;

        else {
            for (Player player : activePlayers) {
                if (player.getNickname().equals(starter))
                    sortedPlayers.add(player);
            }
            for (Player player : activePlayers) {
                if (!player.getNickname().equals(starter))
                    sortedPlayers.add(player);
            }
        }

        startGame(sortedPlayers);
    }

    /**
     * Creates Board and Controller and starts a new match
     * @param sortedPlayers match players list (and associated gods)
     */
    private void startGame(List<Player> sortedPlayers) {
        System.out.println("[SERVER] game starts");
        Board board = new Board();
        Controller controller = new Controller(board, userInputManager, virtualView, sortedPlayers);
        //first the players choose the initial position for their worker then the game starts
        controller.setWorkers();
        controller.play();
    }

}
