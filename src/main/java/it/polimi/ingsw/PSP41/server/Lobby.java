package it.polimi.ingsw.PSP41.server;

import it.polimi.ingsw.PSP41.model.GodPowerFactory;
import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.observer.ConnectionObservable;
import it.polimi.ingsw.PSP41.utils.ChooseGodMessage;
import it.polimi.ingsw.PSP41.utils.NameMessage;
import it.polimi.ingsw.PSP41.utils.PlayersInfoMessage;

import java.util.*;

import static it.polimi.ingsw.PSP41.utils.GameMessage.*;

// Se mai volessimo fare multipartita, bisogna togliere gli static e creare un'istanza di lobby nel server, assegnandola direttamente ai clienthandler
public class Lobby extends ConnectionObservable {
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

    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * ask and set the number of players to the first connected user
     * @param client current client
     */
    public void setPlayersNumber(ClientHandler client) {
        synchronized (lock) {
            client.send(startTurnMessage);
            virtualView.requestPlayersNum(client);
            playersNumber = userInputManager.getPlayersNumber();
            lock.notifyAll();
            System.out.println("[SERVER] The game will have " + playersNumber + " players");
            client.send(Integer.valueOf(playersNumber));
            client.send(endTurnMessage);
        }
    }

    /**
     * wait until the first connected user has not choose the playersNumber
     * @param client current client
     */
    public void waitPlayersNumber(ClientHandler client) {
        synchronized (lock) {
            while (playersNumber == -1) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (client.getPosition() > playersNumber) {
                client.send(fullLobby);
                client.setActive(false);
                client.closeConnection();
            } else {
                client.send(Integer.valueOf(playersNumber));
            }
        }
    }

    /**
     * ask and set the current client's nickname
     * @param client current client
     */
    public synchronized void setNickname(ClientHandler client) {

        client.send(startTurnMessage);

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

        client.send(waitMessage);
        client.send(endTurnMessage);

    }

    /**
     * random choice of the challenger from the connected clients
     */
    public synchronized void setGodLike(ClientHandler client) {

        //wait until the right number of players is connected
        if (playersName.size() != playersNumber) {
            //client.send(waitMessage);
            // TODO timeout
        }

        else {
            //if block executed only once
            if (chosenGods.size() == 0) {
                Collections.shuffle(playersName);

                Map<String, ClientHandler> shuffleMap = new LinkedHashMap<>();
                for (String key : playersName) {
                    shuffleMap.put(key, clientNames.get(key));
                }

                setChallenger(shuffleMap.get(playersName.get(0)), playersName.get(0));

                for (String key : shuffleMap.keySet()) {
                    //System.out.println(key);
                    setGodCard(shuffleMap.get(key));
                }
            }
        }
    }

    /**
     * ask the challenger to choose the godCards that will be used in the game
     * @param client challenger's client
     * @param name challenger's nickname
     */
    private synchronized void setChallenger(ClientHandler client, String name) {

        client.send(startTurnMessage);
        challenger = name;

        for (ClientHandler clientHandler : clients) {
            if(!clientHandler.equals(client))
                clientHandler.send(waitMessage);

            clientHandler.send(new NameMessage(godLikeMessage, challenger));
        }

        client.send(new ChooseGodMessage(gameGodsMessage, gameGods));

        String s = client.read();

        String[] selectedGods = s.split("/");

        chosenGods.addAll(Arrays.asList(selectedGods));

        for (String chosenGod : chosenGods) {
            System.out.println("[SERVER] " + chosenGod + " chosen");
        }

        //TODO check server-side (ask if needed)
        /*for(String god : chosenGods) {
            if(!gameGods.contains(god) || Collections.frequency(chosenGods, god) > 1)

        }*/

        client.send(endTurnMessage);
    }

    /**
     * assign a godCard to the current client (the challenger gets the remaining card)
     * @param client current client
     */
    public synchronized void setGodCard(ClientHandler client) {
        if(!client.equals(clientNames.get(challenger))) {
            client.send(startTurnMessage);
            assignGod(client);
            client.send(endTurnMessage);
        }
        else client.send(waitMessage);
    }

    /**
     * ask and set the chosen godCard
     * @param client current client
     */
    private synchronized void assignGod(ClientHandler client) {

        //TODO check server-side (ask if needed)
        client.send(new ChooseGodMessage(yourGodMessage, chosenGods));
        String godName = client.read().toUpperCase();
        chosenGods.remove(godName);

        String name = null;
        for(String s : clientNames.keySet())
            if(clientNames.get(s).equals(client))
                name = s;

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
     * setup game creating model and controller, then notifies the server that will start the match
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

        for(Player player : activePlayers) {
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
        //TODO check server-side (ask if needed)

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

        notifyServer(userInputManager, virtualView, sortedPlayers);
    }


}
