package it.polimi.ingsw.PSP41.view.CLIPackage;

public class CellCLI {
    private String string;
    private ColorCLI color;


    /**
     * Initialize the CellCLI setting the attributes to default values
     * */
    public CellCLI() {
        string = "        ";
        color = null;
    }

    public String getString() {
        return string;
    }


    public ColorCLI getColor() {
        return color;
    }


    public void setString(String string) {
        this.string = string;
    }


    public void setColor(ColorCLI color) {
        this.color = color;
    }

}
