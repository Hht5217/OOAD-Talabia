package controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
        view.setUpCloseWindowHandler(() -> saveGame());
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
        if (view.isGameScreenDisplayed() && view.askSaveGame()) {
            saveGame();
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
                        view.setPointImage(button, point.getPieceName(), point.getDirection());
                    } else {
                        // For non-Point pieces, call the other version of setPieceImage
                        view.setPieceImage(button, piece.getPieceName());
                    }
                }
            }
        }
        if (!view.isGameScreenDisplayed()) {
            view.switchToGameScreen();
        }
    }

    private void loadGame() {
        if (view.isGameScreenDisplayed() && view.askSaveGame()) {
            saveGame();
        }

        File saveDir = new File("saves");
        JFileChooser fileChooser = new JFileChooser(saveDir);
        fileChooser.setDialogTitle("Load game file");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Load the game from the selected file
            try {
                game.setLoadGame(selectedFile);

                // If the game screen is not currently displayed, switch to it
                if (!view.isGameScreenDisplayed()) {
                    view.switchToGameScreen();
                }

                // Update the view to reflect the loaded game state
                updateView();

            } catch (IOException ex) {
                // Handle the exception
                System.err.println("Error loading game: " + ex.getMessage());
            }
        }
    }

    private void updateView() {
        // Clear all buttons' images
        view.clearButtonsImages();

        // Set the images and stats labels
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece != null) {
                    if (piece instanceof Point) {
                        view.setPointImage(view.getButton(r, c), piece.getPieceName(), ((Point) piece).getDirection());
                    } else {
                        view.setPieceImage(view.getButton(r, c), piece.getPieceName());
                    }
                }
            }
        }
        view.setStatLabels(game.getPlayer(), game.getMoveCount());
    }

    private void saveGame() {
        File saveDir = new File("saves");
        JFileChooser fileChooser = new JFileChooser(saveDir);
        fileChooser.setDialogTitle("Save game file");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".txt")) {
                filePath += ".txt";
                fileToSave = new File(filePath); // Update the fileToSave object
            }
            try {
                game.setSaveGame(filePath);
                System.out.println("\"" + fileToSave.getName() + "\" saved to '" + fileToSave.getParent() + "'");
            } catch (IOException ex) {
                System.err.println("An error occurred while saving the game: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void mainMenu() {
        if (view.askSaveGame()) {
            saveGame();
        }
        view.switchToMenuScreen();
    }

    private void exit() {
        if (view.isGameScreenDisplayed() && view.askSaveGame()) {
            saveGame();
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
