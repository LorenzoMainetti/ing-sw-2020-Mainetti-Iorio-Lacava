package it.polimi.ingsw.PSP41.observer;

import it.polimi.ingsw.PSP41.controller.UserInputManager;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.server.ClientHandler;
import it.polimi.ingsw.PSP41.server.VirtualView;

import java.util.List;

public interface ConnectionObserver {

    void updateServer(UserInputManager userInputManager, VirtualView virtualView, List<Player> players);

    void updateDisconnection(ClientHandler clientHandler);

}
