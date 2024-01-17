package controller;

import javax.swing.JMenuItem;

import model.*;
import view.*;

public class MenuController {
    private Game game;
    private View view;

    public MenuController(Game talabiaGame, View talabiaView) {
        this.game = talabiaGame;
        this.view = talabiaView;
        initMenuController();
    }

    private void initMenuController() {
        JMenuItem newGameItem = view.getMenuItem("New");
        newGameItem.addActionListener(e -> newGame());

        JMenuItem loadGameItem = view.getMenuItem("Load");
        loadGameItem.addActionListener(e -> loadGame());

        JMenuItem saveGameItem = view.getMenuItem("Save");
        saveGameItem.addActionListener(e -> saveGame());

        JMenuItem mainMenuItem = view.getMenuItem("Main Menu");
        mainMenuItem.addActionListener(e -> mainMenu());

        JMenuItem exitItem = view.getMenuItem("Exit");
        exitItem.addActionListener(e -> exit());

        JMenuItem guideItem = view.getMenuItem("Guide");
        guideItem.addActionListener(e -> guide());

        JMenuItem aboutItem = view.getMenuItem("About");
        aboutItem.addActionListener(e -> about());
    }

    private void newGame() {
        view.askSaveGame();
        game.setGame();
    }

    private void loadGame() {
        view.askSaveGame();
        System.out.println("Load"); // test
        // jfilechooser
    }

    private void saveGame() {
        System.out.println("Save"); // test
        // jfilechooser
    }

    private void mainMenu() {
        view.askSaveGame();
        System.out.println("Mainmenu"); // test
        // back to main menu
    }

    private void exit() {
        view.askSaveGame();
        System.out.println("Exit"); // test
        // exit game
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
