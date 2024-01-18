package controller;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import model.*;
import view.*;

public class MenuController {
    private Game game;
    private View view;

    public MenuController(Game talabiaGame, View talabiaView) {
        this.game = talabiaGame;
        this.view = talabiaView;
        initMenuButtons();
        initMenuItems();
    }

    private void initMenuButtons() {
        JButton newGameButton = view.getMainMenuButton("Menu New");
        newGameButton.addActionListener(e -> newGame());

        JButton loadGameButton = view.getMainMenuButton("Menu Load");
        loadGameButton.addActionListener(e -> loadGame());

        JButton exiButton = view.getMainMenuButton("Menu Exit");
        exiButton.addActionListener(e -> exit());
    }

    private void initMenuItems() {
        JMenuItem newGameItem = view.getMenuBarItem("New");
        newGameItem.addActionListener(e -> newGame());

        JMenuItem loadGameItem = view.getMenuBarItem("Load");
        loadGameItem.addActionListener(e -> loadGame());

        JMenuItem saveGameItem = view.getMenuBarItem("Save");
        saveGameItem.addActionListener(e -> saveGame());

        JMenuItem mainMenuItem = view.getMenuBarItem("Main Menu");
        mainMenuItem.addActionListener(e -> mainMenu());

        JMenuItem exitItem = view.getMenuBarItem("Exit");
        exitItem.addActionListener(e -> exit());

        JMenuItem guideItem = view.getMenuBarItem("Guide");
        guideItem.addActionListener(e -> guide());

        JMenuItem aboutItem = view.getMenuBarItem("About");
        aboutItem.addActionListener(e -> about());
    }

    // private void menuNewGame() {
    // view.switchToGameScreen();
    // }

    // private void menuLoadGame() {
    // view.switchToGameScreen();
    // // add load game logic and game.setgame
    // }

    private void newGame() {
        if (view.isGameScreenDisplayed()) {
            view.askSaveGame();
            game.setNewGame();
        } else {
            view.switchToGameScreen();
            // probably also add set game in case user back to menu then new game again
        }
    }

    private void loadGame() {
        if (view.isGameScreenDisplayed()) {
            view.askSaveGame();
        }
        game.setLoadGame(); // test
        // jfilechooser
    }

    private void saveGame() {
        System.out.println("Save"); // test
        // jfilechooser
    }

    private void mainMenu() {
        view.askSaveGame();
        // System.out.println("Mainmenu"); // test
        view.switchToMenuScreen();
    }

    private void exit() {
        if (view.isGameScreenDisplayed()) {
            view.askSaveGame();
        }
        System.out.println("Exit"); // test
        // exit game
        System.exit(0);
    }

    private void guide() {
        System.out.println("How to play"); // test
        // add pop up window and display simple rules and pieces movement
        view.showGuide();
    }

    private void about() {
        System.out.println("About"); // test
        // show simple info about program creators
        view.showAbout();
    }
}
