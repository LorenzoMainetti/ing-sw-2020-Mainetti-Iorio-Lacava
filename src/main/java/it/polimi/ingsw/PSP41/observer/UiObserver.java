package it.polimi.ingsw.PSP41.observer;

/**
 * Observer interface used by NetworkHandler
 */
public interface UiObserver {

    void update(String string);

    void updateConnection(String ip, String port);

}
