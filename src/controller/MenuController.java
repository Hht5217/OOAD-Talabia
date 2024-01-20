package controller;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import model.*;
import pieces.*;
import view.*;

public class MenuController {
    private Game game;
    private View view;
    private Board board;

    public MenuController(Game talabiaGame, View talabiaView) {
        this.game = talabiaGame;
        this.board = game.getGameBoard();
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

    // Perform this action when new game button or menu item is clicked
    private void newGame() {
        if (view.isGameScreenDisplayed()) {
            view.askSaveGame();
        }
        game.setNewGame();
        view.setStatLabels(game.getPlayer(), game.getMoveCount());
        view.clearButtonsImages();
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                JButton button = view.getButton(r, c);
                Piece piece = board.getPiece(r, c);
                if (piece != null) {
                    // If piece is Point call the version with direction param
                    if (piece instanceof Point) {
                        // Cast the Piece to a Point to get the direction
                        Point point = (Point) piece;
                        view.setPointImage(button, point.toString(), point.getDirection());
                    } else {
                        // For non-Point pieces, call the other version of setPieceImage
                        view.setPieceImage(button, piece.toString());
                    }
                }
            }
        }
        if (!view.isGameScreenDisplayed()) {
            view.switchToGameScreen();
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
        view.switchToMenuScreen();
    }

    private void exit() {
        if (view.isGameScreenDisplayed()) {
            view.askSaveGame();
        }
        System.exit(0); // Exit the program
    }

    private void guide() {
        view.showGuide();
    }

    private void about() {
        view.showAbout();
    }
}
