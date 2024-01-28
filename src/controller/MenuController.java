/**
 * MenuController class is responsible for handling user interactions with the
 * menu of the game, including the main menu and the in-game menu. This class
 * listens for user actions such as starting a new game, loading a game, saving
 * a game, switching to the main menu, and exiting the game, and then performs
 * the appropriate operations in response.
 */
package controller;

import java.io.File;
import java.io.IOException;
import javax.swing.*;

import model.*;
import pieces.*;
import view.*;

public class MenuController {
    private Game game;
    private View view;
    private Board board;

    /**
     * Constructor of MenuController
     */
    public MenuController(Game talabiaGame, View talabiaView) {
        // Initialization
        this.game = talabiaGame;
        this.board = game.getGameBoard();
        this.view = talabiaView;
    }

    /* ------------------------------ Initalization ----------------------------- */
    /**
     * Initalization of components.
     * 
     * @author HhT
     * @author yikai
     */
    public void initController() {
        // Set the action of window close button when the game screen is being displayed
        view.setUpCloseWindowHandler(() -> saveGame());

        // Call the initialization methods
        initMenuButtons();
        initMenuItems();
    }

    /**
     * Add action listeners to the buttons in main menu.
     * 
     * @author HhT
     */
    private void initMenuButtons() {
        // Start new game when clicked
        JButton newGameButton = view.getMainMenuButton("Menu New");
        newGameButton.addActionListener(e -> newGame());

        // Load game when clicked
        JButton loadGameButton = view.getMainMenuButton("Menu Load");
        loadGameButton.addActionListener(e -> loadGame());

        // Exit the program when clicked
        JButton exiButton = view.getMainMenuButton("Menu Exit");
        exiButton.addActionListener(e -> exit());
    }

    /**
     * Add action listeners to the components (menu items) of menu bar.
     * 
     * @author HhT
     */
    private void initMenuItems() {
        // Start new game when clicked
        JMenuItem newGameItem = view.getMenuBarItem("New");
        newGameItem.addActionListener(e -> newGame());

        // Load game when clicked
        JMenuItem loadGameItem = view.getMenuBarItem("Load");
        loadGameItem.addActionListener(e -> loadGame());

        // Save the game when clicked
        JMenuItem saveGameItem = view.getMenuBarItem("Save");
        saveGameItem.addActionListener(e -> saveGame());

        // Return to main menu when clicked
        JMenuItem mainMenuItem = view.getMenuBarItem("Main Menu");
        mainMenuItem.addActionListener(e -> mainMenu());

        // Exit the program when clicked
        JMenuItem exitItem = view.getMenuBarItem("Exit");
        exitItem.addActionListener(e -> exit());

        // Display a window on how to play the game when clicked
        JMenuItem guideItem = view.getMenuBarItem("Guide");
        guideItem.addActionListener(e -> guide());

        // Display a brief information about the program when clicked
        JMenuItem aboutItem = view.getMenuBarItem("About");
        aboutItem.addActionListener(e -> about());
    }
    /* -------------------------------------------------------------------------- */

    /* -------------------------- Game state management ------------------------- */
    /**
     * Action performed when new game button or new game menu item is clicked.
     * 
     * @author HhT
     */
    private void newGame() {
        /*
         * If game screen is being displayed, then ask user whether to save the game, if
         * Yes then only save the game. If user cancels by clicking 'X', abandon the
         * action.
         */
        if (view.isGameScreenDisplayed()) {
            int saveGameOption = view.askSaveGame();
            if (saveGameOption == JOptionPane.YES_OPTION) {
                saveGame();
            } else if (saveGameOption == JOptionPane.CANCEL_OPTION || saveGameOption == JOptionPane.CLOSED_OPTION) {
                return; // Do nothing
            }
        }

        game.setNewGame(); // Tell Game to set a new game by modifying game state values
        updateView(); // Update the view of the game after setting new game

        /*
         * If game screen is not displayed (currently at menu screen) then switch to
         * game screen
         */
        if (!view.isGameScreenDisplayed()) {
            view.switchToGameScreen();
        }
    }

    /**
     * Action performed when load game button or load game menu item is clicked.
     * 
     * @author HhT
     * @author yikai
     */
    private void loadGame() {
        // Same function, refer to newGame()
        if (view.isGameScreenDisplayed()) {
            int saveGameOption = view.askSaveGame();
            if (saveGameOption == JOptionPane.YES_OPTION) {
                saveGame();
            } else if (saveGameOption == JOptionPane.CANCEL_OPTION || saveGameOption == JOptionPane.CLOSED_OPTION) {
                // If the user cancelled or closed the dialog, abort loading the game
                return;
            }
            // If the user chose No, continue with loading the game
        }

        File saveDir = new File("saves"); // Point to the "saves" directory
        JFileChooser fileChooser = new JFileChooser(saveDir); // Open from the "saves" directory
        fileChooser.setDialogTitle("Load game file");
        int option = fileChooser.showOpenDialog(null); // Set a variable representing state of file chooser
        if (option == JFileChooser.APPROVE_OPTION) { // If user chooses an approved option
            File selectedFile = fileChooser.getSelectedFile(); // Get the file selected
            // Load the game from the selected file
            try {
                game.setLoadGame(selectedFile);

                // If the game is loaded from main menu, switch to game screen
                if (!view.isGameScreenDisplayed()) {
                    view.switchToGameScreen();
                }

                // Update the view to reflect the loaded game state
                updateView();

            }
            // Catch IOException in case loaded game file content has problem
            catch (IOException ex) {
                System.err.println("Error loading game: " + ex.getMessage());
            }
        }
    }

    /**
     * Action performed when save game menu item is clicked, or when user selects
     * Yes when asked to save the game before setting new game or loading game.
     * 
     * @author HhT
     * @author yikai
     */
    private void saveGame() {
        File saveDir = new File("saves"); // The directory to save the game file
        JFileChooser fileChooser = new JFileChooser(saveDir); // Open from the "saves" directory
        fileChooser.setDialogTitle("Save game file");
        int option = fileChooser.showSaveDialog(null); // Refer to loadGame()
        if (option == JFileChooser.APPROVE_OPTION) { // Refer to loadGame()
            File fileToSave = fileChooser.getSelectedFile(); // Create the file object
            String filePath = fileToSave.getAbsolutePath(); // Get the path of the created file
            if (!filePath.endsWith(".txt")) { // Add .txt to save file if not added
                filePath += ".txt";
                fileToSave = new File(filePath);
            }
            try {
                game.setSaveGame(filePath); // Save the file
                // Display pop up window after saving game
                view.gameSavedPopup(fileToSave.getName(), fileToSave.getParent());
            }
            // Catch exception in case save game failed
            catch (IOException ex) {
                System.err.println("An error occurred while saving the game: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    /**
     * Update the view of the game after resetting or loading a game.
     * 
     * @author HhT
     */
    private void updateView() {
        // Clear all buttons' images
        view.clearButtonsImages();

        // Set the images to the buttons
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                JButton button = view.getButton(r, c);
                Piece piece = board.getPiece(r, c);
                if (piece != null) {
                    if (piece instanceof Point) { // Set the image of point with its direction
                        view.setPointImage(button, piece.getPieceName(), ((Point) piece).getDirection());
                    } else {
                        view.setPieceImage(button, piece.getPieceName());
                    }
                }
            }
        }
        // Update the game state labels
        view.setStateLabels(game.getPlayer(), game.getMoveCount());
    }
    /* -------------------------------------------------------------------------- */

    /* ------------- Other menu actions (non game state management) ------------- */
    /**
     * Switch to main menu screen.
     * 
     * @author HhT
     */
    private void mainMenu() {
        // If the game screen is displayed, ask the user if they want to save the game
        if (view.isGameScreenDisplayed()) {
            int option = view.askSaveGame();
            if (option == JOptionPane.YES_OPTION) {
                saveGame();
            } else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                // If the user cancelled or closed the dialog, do not switch to the menu screen
                return;
            }
        }
        view.switchToMenuScreen(); // Switch to the menu screen
    }

    /**
     * Exit the program.
     * 
     * @author HhT
     */
    private void exit() {
        // If the game screen is displayed, ask the user if they want to save the game
        if (view.isGameScreenDisplayed()) {
            int option = view.askSaveGame();
            if (option == JOptionPane.YES_OPTION) {
                saveGame();
                System.exit(0); // Exit the program after saving
            } else if (option == JOptionPane.NO_OPTION) {
                System.exit(0); // Exit the program without saving
            }
            // If the user selects 'Cancel' or closes the dialog, do nothing (do not exit)
        } else {
            System.exit(0); // Exit the program if the game screen is not displayed
        }
    }

    /**
     * Display a pop up window on how to play the game.
     * 
     * @author HhT
     */
    private void guide() {
        view.showGuide();
    }

    /**
     * Display brief information about the program.
     * 
     * @author HhT
     */
    private void about() {
        view.showAbout();
    }
    /* ----------------------------------- End ---------------------------------- */
}
