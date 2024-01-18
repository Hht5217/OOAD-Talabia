import controller.*;
import model.*;
import view.*;

public class Main {
    public static void main(String[] args) {
        Game g = new Game(); // One of model
        View v = new View(); // View display the model in GUI
        GameController controller = new GameController(g, v); // updates view after getting data from model
        controller.initController();
        MenuController menuController = new MenuController(g, v);
    }
}