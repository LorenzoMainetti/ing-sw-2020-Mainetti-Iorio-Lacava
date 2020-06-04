package it.polimi.ingsw.PSP41.observer;

public interface UiObserver {

    void update(String string);

    void updateConnection(String ip, String port);

}
