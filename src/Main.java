import controller.*;
import model.*;
import view.*;

public class Main {
    public static void main(String[] args) {
        Game g = new Game(); // One of model
        View v = new View(); // View display the model in GUI
        Controller controller = new Controller(g, v); // Controller updates model after getting actions from view
        controller.initController();
        MenuController menuController = new MenuController(g, v);
    }
}