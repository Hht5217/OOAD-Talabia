import controller.*;
import model.*;
import view.*;

public class Main {
    public static void main(String[] args) {
        Game g = new Game(); // One of model
        View v = new View(); // View display the model in GUI
        GameController gameController = new GameController(g, v); // updates view after getting data from model
        MenuController menuController = new MenuController(g, v); // used for initializing menu components
        gameController.initController();
        menuController.initController();
    }
}