package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.controller.Controller;
import it.polimi.ingsw.PSP41.model.GodPowerFactory;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Board;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;


import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

// Se mai volessimo fare multipartita, bisogna togliere gli static e creare un'istanza di lobby nel server, assegnandola direttamente ai clienthandler
public class Lobby {
    private static final VirtualView virtualView = new VirtualView();
    private static final UserInputManager userInputManager = new UserInputManager(virtualView);
    private static Map<String, ClientHandler> clientNames = new HashMap<>();
    private static Map<String, String> playerGodCard = new HashMap<>();
    private static List<ClientHandler> clients = new ArrayList<>();
    private static List<String> playersName = new ArrayList<>();
    private final List<String> gameGods = Arrays.asList("APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "DEMETER", "HEPHAESTUS", "MINOTAUR", "PAN", "PROMETHEUS", "HESTIA", "ZEUS", "TRITON", "POSEIDON", "ARES");
    private static final List<String> chosenGods = new ArrayList<>();
    private static List<Player> activePlayers = new ArrayList<>();
    private static String challenger;
    private static int playersNumber = 0;

    /**
     * ask and set the number of players to the first connected user
     * @param client current client
     * @throws IOException if the write on socket did not work
     */
    public synchronized void setPlayersNumber(ClientHandler client) throws IOException {

        client.getSocketOut().writeObject(startTurnMessage);
        virtualView.requestPlayersNum(client);
        playersNumber = userInputManager.getPlayersNumber();
        notifyAll();
        System.out.println("[SERVER] The game will have " + playersNumber + " players");
        client.getSocketOut().writeObject(endTurnMessage);

    }

    /**
     * wait until the first connected user has not choose the playersNumber
     * @param client current client
     * @throws IOException if the write on socket did not work
     */
    public synchronized void waitPlayersNumber(ClientHandler client) throws IOException {

        while (playersNumber == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (client.getPosition() > playersNumber) {
            client.getSocketOut().writeObject(fullLobby);
            client.closeConnection();
        }

        else if (client.getPosition() != 1) {
            client.getSocketOut().writeObject("The game will have " + playersNumber + " players");
        }
    }

    /**
     * ask and set the current client's nickname
     * @param client current client
     * @throws IOException if the write on socket did not work
     */
    public synchronized void setNickname(ClientHandler client) throws IOException {

        client.getSocketOut().writeObject(startTurnMessage);

        virtualView.requestNickname(client);
        String nickname = userInputManager.getNickname();

        while (playersName.contains(nickname)) {
            virtualView.errorTakenNickname(client);
            nickname = userInputManager.getNickname();
        }

        //add user to the list of connected users
        playersName.add(nickname);
        clients.add(client);
        clientNames.put(nickname, client);
        virtualView.addClient(nickname, client);

        System.out.println("[SERVER] " + nickname + " registered!");

        if(playersName.size() == playersNumber) {
            notifyAll();
        }

        client.getSocketOut().writeObject(endTurnMessage);

    }

    /**
     * random choice of the challenger from the connected clients
     * @throws IOException if the write on socket did not work
     */
    public synchronized void setGodLike() throws IOException {

        //wait for missing players
        while (playersName.size() != playersNumber) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //subpart executed only once
        if (chosenGods.size() == 0) {
            for (ClientHandler clientHandler : clients) {
                clientHandler.getSocketOut().writeObject("The lobby is full, the game will start soon...");
            }

            Collections.shuffle(playersName);

            Map<String, ClientHandler> shuffleMap = new LinkedHashMap<>();
            for (String key : playersName) {
                shuffleMap.put(key, clientNames.get(key));
            }
            setChallenger(shuffleMap.get(playersName.get(0)), playersName.get(0));
        }
    }

    /**
     * ask the challenger to choose the godCards that will be used in the game
     * @param client challenger's client
     * @param name challenger's nickname
     * @throws IOException if the write on socket did not work
     */
    private void setChallenger(ClientHandler client, String name) throws IOException {

        client.getSocketOut().writeObject(startTurnMessage);
        challenger = name;

        for (ClientHandler clientHandler : clients) {
            clientHandler.getSocketOut().writeObject(name + " is the most godlike! " + name + " is the challenger!");
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
        notifyAll();
        client.getSocketOut().writeObject(endTurnMessage);

    }

    /**
     * assign a godCard to the current client (the challenger gets the remaining card)
     * @param client current client
     * @throws IOException if the write on socket did not work
     */
    public synchronized void setGodCard(ClientHandler client) throws IOException {

        if(!client.equals(clientNames.get(challenger))) {
            client.getSocketOut().writeObject(startTurnMessage);
            assignGod(client);
            client.getSocketOut().writeObject(endTurnMessage);
        }

        else {
            while (chosenGods.size() > 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            client.getSocketOut().writeObject(startTurnMessage);
            playerGodCard.put(challenger, chosenGods.get(0));
            chosenGods.clear();
            //the challenger creates the game
            createGame(client);
        }
    }

    /**
     * ask and set the chosen godCard
     * @param client current client
     * @throws IOException if the write on socket did not work
     */
    private void assignGod(ClientHandler client) throws IOException {

        client.getSocketOut().writeObject("Choose a god power from the ones chosen by the challenger:");
        for (String chosenGod : chosenGods) {
            client.getSocketOut().writeObject(chosenGod);
        }
        virtualView.requestInfo(client);
        String godName = client.getSocketIn().readLine().toUpperCase();

        while (!chosenGods.contains(godName)) {
            client.getSocketOut().writeObject(godName + " is not valid, please select a different god power from this list:");
            for (String chosenGod : chosenGods) {
                client.getSocketOut().writeObject(chosenGod);
            }
            virtualView.requestInfo(client);
            godName = client.getSocketIn().readLine().toUpperCase();
        }
        chosenGods.remove(godName);

        String name = null;
        for(String s : clientNames.keySet())
            if(clientNames.get(s).equals(client))
                name = s;

        playerGodCard.put(name, godName);

        if(chosenGods.size()==1)
            notifyAll();

    }


    /**
     * setup game creating model and controller
     * @param client current client
     * @throws IOException if the write on socket did not work
     */
    private void createGame(ClientHandler client) throws IOException {

        GodPowerFactory godFactory = new GodPowerFactory();
        Color color = Color.RED;

        for (String name : playerGodCard.keySet()) {
            activePlayers.add(godFactory.createGodPower(name, color, playerGodCard.get(name)));

            virtualView.sendPlayersInfo(new PlayersInfoMessage(name, color, playerGodCard.get(name)));
            color = color.next();
        }

        for(Player player : activePlayers) {
            player.addObserver(virtualView);
            player.getWorker1().addObserver(virtualView);
            player.getWorker2().addObserver(virtualView);
        }

        //the challenger chooses who starts playing
        client.getSocketOut().writeObject("Challenger, choose who starts playing!");
        virtualView.requestInfo(client);
        String starter = client.getSocketIn().readLine();
        while (!playersName.contains(starter)) {
            client.getSocketOut().writeObject("Invalid player, try again");
            virtualView.requestInfo(client);
            starter = client.getSocketIn().readLine();
        }
        client.getSocketOut().writeObject(endTurnMessage);
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

        Board board = new Board();
        Controller controller = new Controller(board, userInputManager, virtualView, sortedPlayers);
        controller.setWorkers();
        controller.play();
    }


}
