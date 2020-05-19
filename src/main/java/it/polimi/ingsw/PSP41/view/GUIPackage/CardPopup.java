package it.polimi.ingsw.PSP41.view.GUIPackage;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.List;


public abstract class CardPopup {
    protected Pane root;
    protected ImageView closeButton;
    protected ImageView selectButton;
    protected Label messageLabel;
    protected Label godName;
    protected Text closeText;
    protected Text selectText;
    protected Node eventSource;
    protected int counter = 0;
    protected List<Boolean> selectedCards;


    public abstract void display(String title, String message);
}
