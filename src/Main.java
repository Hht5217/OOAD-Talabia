
/**
 * Main class serves as the entry point for the application and is responsible
 * for setting up the Model-View-Controller (MVC) architecture
 */

import controller.*;
import model.Game;
import view.View;

public class Main {
    public static void main(String[] args) {
        Game g = new Game(); // Manages the game state
        View v = new View(); // Manages the GUI

        // Handles the game logic and interactions between the Game model and the View
        GameController gameController = new GameController(g, v);

        // Handles interactions with the menu components in the View
        MenuController menuController = new MenuController(g, v);

        // Call initialization methods to setup and start program
        gameController.initController();
        menuController.initController();
    }
}