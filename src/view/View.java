/**
 * The View class represents the graphical user interface (GUI) for a chess
 * game. It extends the JFrame class and contains methods for creating the menu
 * screen, game screen, and essential GUI components.
 */
package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;

import model.*;

public class View extends JFrame {
    // Constants defining the number of rows and columns in the chessboard
    private static final int ROW = 6;
    private static final int COLUMN = 7;

    // Maps of components that needs to add action listeners
    private Map<String, JButton> menuButtons = new HashMap<>(); // Store the buttons on menu screen
    private Map<String, JMenuItem> menuBarItems = new HashMap<>(); // Store menu bar components

    // Screens/view of the program
    private JPanel menuScreen; // The first screen when program starts
    private JPanel gameScreen; // The screen when game is running
    private boolean isGameScreen = false; // To check whether to switch to game screen

    // Essential components for the program
    private JMenuBar gameMenuBar; // The navigation bar
    private JLabel playerLabel; // The text below the navigation bar
    private JLabel moveCountLabel; // Same as above
    private JButton[][] chessButtons = new JButton[ROW][COLUMN]; // The 2d array to store buttons
    private CardLayout cardLayout = new CardLayout(); // The cardlayout, used to switch screens
    private JPanel screens = new JPanel(cardLayout); // A holder for the screens that uses cardlayout

    // The buttons dimension that will remain same no matter the window size
    private int buttonWidth;
    private int buttonHeight;

    /**
     * Constructor of View object. Initializes the GUI components and creates the
     * initial screen.
     * 
     * @author HhT
     * @author Lim KZ
     */
    public View() {
        super("Talabia"); // Title of the program
        setSize(new Dimension(700, 600)); // The size when program starts
        setMinimumSize(new java.awt.Dimension(700, 600)); // Set minimums size for window

        /*
         * These components are initialized here first so that it is not returned null
         * when controller class is initialized
         */
        playerLabel = new JLabel("Current player:");
        moveCountLabel = new JLabel("Move Count:");

        // Add empty buttons to the 2D array
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                String buttonName = ("r" + r + "c" + c); // Create name String for button
                JButton chessButton = new JButton(); // Create new button
                chessButton.setName(buttonName); // Set name of button
                // chessButton.setText(buttonName); // Set text using name, for testing
                Dimension buttonSize = new Dimension(100, 100); // Define button size
                chessButton.setSize(buttonSize); // Set button size
                chessButton.setBackground(Color.WHITE); // Set background of button to WHITE
                chessButton.setFocusable(false); // To hide the border when button is clicked
                chessButtons[r][c] = chessButton; // Set the button to specific location in the 2d array
            }
        }

        // Initialize the button width and height
        buttonWidth = chessButtons[0][0].getWidth();
        buttonHeight = chessButtons[0][0].getHeight();

        // Initialize the menu bar, but not showing it first
        gameMenuBar = createMenu();

        /*
         * The invokeLater ensures Swing will execute the creation of GUI in an orderly
         * fashion, to prevent changes happening all at once
         */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI(); // Create GUI components
                setLocationRelativeTo(null); // Puts the window in the middle of the screen when it first opens.
                setVisible(true); // Show the GUI
            }
        });
    }

    /* ------------------------- Initialization methods ------------------------- */
    /**
     * The method to create essential GUI components, mostly those that will be used
     * throughout the program.
     * 
     * @author HhT
     */
    private void createGUI() {
        // Add the screens to cardlayout screen holder after creating them
        screens.add(createGameScreen(), "GameScreen");
        screens.add(createMenuScreen(), "MenuScreen");

        // Create menu bar but no need to show first
        gameMenuBar = createMenu();

        // Initially show the menu screen
        cardLayout.show(screens, "MenuScreen");

        // Add screens to the JFrame
        add(screens);
    }

    /**
     * Create the menu screen of the program.
     * 
     * @return the menu screen panel
     * @author HhT
     */
    private JPanel createMenuScreen() {
        menuScreen = new JPanel(); // Initialize the menu screen
        menuScreen.setLayout(new BoxLayout(menuScreen, BoxLayout.Y_AXIS)); // Set the layout of menu screen

        JLabel titleLabel = new JLabel("Talabia Chess"); // Create a title label
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32)); // Set font size to 32
        titleLabel.setAlignmentX(CENTER_ALIGNMENT); // Align vertically at center

        JButton newGameButton = new JButton("New Game"); // Create a button for starting new game
        newGameButton.setBackground(Color.decode("#F7DE8B")); // Set the color, using RGB
        newGameButton.setAlignmentX(CENTER_ALIGNMENT); // Align vertically at center
        menuButtons.put("Menu New", newGameButton); // Put this button into the map of components

        JButton loadGameButton = new JButton("Load Game"); // Create a button for loading game
        loadGameButton.setBackground(Color.decode("#F7DE8B")); // Similar to above
        loadGameButton.setAlignmentX(CENTER_ALIGNMENT);
        menuButtons.put("Menu Load", loadGameButton);

        JButton exitButton = new JButton("Exit"); // Create a button for exit program
        exitButton.setBackground(Color.decode("#F7DE8B")); // Similar to above
        exitButton.setAlignmentX(CENTER_ALIGNMENT);
        menuButtons.put("Menu Exit", exitButton);

        int verticalSpace = 10; // The size of the vertical space between the components

        // Add the components to the panel
        menuScreen.add(Box.createVerticalGlue());
        menuScreen.add(titleLabel);
        menuScreen.add(Box.createVerticalStrut(verticalSpace)); // Add the space between components
        menuScreen.add(newGameButton);
        menuScreen.add(Box.createVerticalStrut(verticalSpace));
        menuScreen.add(loadGameButton);
        menuScreen.add(Box.createVerticalStrut(verticalSpace));
        menuScreen.add(exitButton);
        menuScreen.add(Box.createVerticalGlue());

        return menuScreen;
    }

    /**
     * Create the screen that displays gameplay.
     * 
     * @return the game screen panel
     * @author HhT
     * @author Lim KZ
     */
    private JPanel createGameScreen() {
        gameScreen = new JPanel(); // Initialize the game screen
        gameScreen.setLayout(new BorderLayout()); // Set the layout of game screen
        /*
         * Currently using borderlayout for statePanel, if more labels will be added
         * then
         * consider using flowlayout
         */
        JPanel statePanel = new JPanel(new BorderLayout());

        // Add labels to the game state panel
        statePanel.add(playerLabel, BorderLayout.WEST);
        statePanel.add(moveCountLabel, BorderLayout.EAST);
        // Add the state panel to game screen
        gameScreen.add(statePanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(); // Initialize the panel of the chessboard
        boardPanel.setLayout(new GridLayout(6, 7)); // Set the layout for chessboard
        gameScreen.add(boardPanel, BorderLayout.CENTER); // Add the chessboard to game screen

        // Add empty buttons for the chessboard
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                boardPanel.add(chessButtons[r][c]);
            }
        }

        return gameScreen;
    }

    /**
     * Create menu bar and menu items.
     * 
     * @return the menu bar with menu items in it
     * @author HhT
     */
    private JMenuBar createMenu() {
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create menus (dropdown)
        JMenu menuDropdown = new JMenu("Menu");
        JMenu helpDropdwon = new JMenu("Help");

        // Create menu items
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem mainMenuItem = new JMenuItem("Main menu");
        JMenuItem exitItem = new JMenuItem("Exit");

        JMenuItem guideItem = new JMenuItem("How to play?");
        JMenuItem aboutItem = new JMenuItem("About");

        // Add menu items to the menu
        menuDropdown.add(newGameItem);
        menuDropdown.add(loadGameItem);
        menuDropdown.add(saveGameItem);
        menuDropdown.add(mainMenuItem);
        menuDropdown.add(exitItem);

        helpDropdwon.add(guideItem);
        helpDropdwon.add(aboutItem);

        // // Add the menu to the menu bar
        menuBar.add(menuDropdown);
        menuBar.add(helpDropdwon);

        // Add every menu items to Map for adding listener use
        menuBarItems.put("New", newGameItem);
        menuBarItems.put("Load", loadGameItem);
        menuBarItems.put("Save", saveGameItem);
        menuBarItems.put("Main Menu", mainMenuItem);
        menuBarItems.put("Guide", guideItem);
        menuBarItems.put("About", aboutItem);
        menuBarItems.put("Exit", exitItem);

        return menuBar;
    }

    /**
     * Do nothing when close window is clicked, but implements conditions to do
     * something when clicked, in this case display a window to ask user whether to
     * save game. The Runnable saveGameActon is to let the menu controller class to
     * run the saveGame method.
     * 
     * @author HhT
     */
    public void setUpCloseWindowHandler(Runnable saveGameAction) {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isGameScreen) { // If game screen is displayed
                    int option = askSaveGame(); // Ask the user if they want to save the game

                    if (option == JOptionPane.YES_OPTION) { // If yes
                        saveGameAction.run(); // Perform save game action
                        System.exit(0); // Exit the program
                    } else if (option == JOptionPane.NO_OPTION) { // If no
                        System.exit(0); // Exit the program without saving
                    }
                    // If the user selects 'Cancel' or closes the dialog, do nothing (do not exit)
                } else { // If game screen is not displayed
                    System.exit(0); // Exit the program if the game screen is not displayed
                }
                System.exit(0); // Exit the program
            }
        });
    }

    /**
     * Set the labels of game states.
     * 
     * @author HhT
     */
    public void setStateLabels(PlayerColor player, int moveCount) {
        // Space at the end to prevent text sticking to window
        moveCountLabel.setText("Move Count: " + Integer.toString(moveCount) + " ");

        // If player is yellow then set yellow, if not then set blue
        String currentPlayer = (player == PlayerColor.YELLOW) ? "YELLOW" : "BLUE";
        playerLabel.setText("Current player: " + currentPlayer);
    }
    /* -------------------------------------------------------------------------- */

    /* ------------------------- Screen related methods ------------------------- */
    /**
     * Switch to menu screen from the screens holder using cardlayout.
     * 
     * @author HhT
     */
    public void switchToMenuScreen() {
        cardLayout.show(screens, "MenuScreen");
        isGameScreen = false;
        // Hide the menu bar when switching to the menu screen
        setJMenuBar(null);
    }

    /**
     * Switch to game screen from the screens holder using cardlayout.
     * 
     * @author HhT
     */
    public void switchToGameScreen() {
        cardLayout.show(screens, "GameScreen");
        isGameScreen = true;
        // Show the menu bar when switching to the game screen
        setJMenuBar(gameMenuBar);
    }

    /**
     * Check if game screen is being displayed.
     * 
     * @return true if game screen is displayed
     * @author HhT
     * @author Lim KZ
     */
    public boolean isGameScreenDisplayed() {
        return isGameScreen;
    }
    /* -------------------------------------------------------------------------- */

    /* ------------------------- Menu components methods ------------------------ */
    /**
     * Get specific main menu button from the map using the key (the name).
     * 
     * @param key the key of the button
     * @return the main menu button
     * @author HhT
     */
    public JButton getMainMenuButton(String key) {
        return menuButtons.get(key);
    }

    /**
     * Get specific menu item from the map using the key (the name).
     * 
     * @param key the key of the button
     * @return the menu item
     * @author HhT
     */
    public JMenuItem getMenuBarItem(String key) {
        return menuBarItems.get(key);
    }
    /* -------------------------------------------------------------------------- */

    /* ------------------------- Button related methods ------------------------- */
    /**
     * Get a specific button from their position.
     * 
     * @param row the row where the button is at
     * @param col the column where the button is at
     * @return the button
     * @author HhT
     */
    public JButton getButton(int row, int col) {
        return chessButtons[row][col];
    }

    /*
     * ^- These two are different, one is get button from position and one is get
     * position from button -v
     */

    /**
     * Get the button's position.
     * 
     * @return button's position as move object
     * @author HhT
     * @author Lim KZ
     */
    public Move getButtonPosition(JButton button) {
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                if (chessButtons[r][c] == button) {
                    return new Move(r, c); // return the position as move object (pair of integers)
                }
            }
        }

        return null;
    }

    /**
     * Get images to buttons according to pieces' name and set the image.
     * 
     * @param buttonToSet the button to set the image on
     * @param pieceName   the name of the piece, to get the image file
     * @author HhT
     * @author Lim KZ
     */
    public void setPieceImage(JButton buttonToSet, String pieceName) {
        String imageName = (pieceName.replaceAll("\\d", "")) + ".png"; // Remove all digits from pieceName
        setImage(buttonToSet, imageName); // Set the image
    }

    /**
     * Get image for Point pieces and set the image.
     * 
     * @param buttonToSet the button to set the image on
     * @param pieceName   the name of the piece, specifically Point piece
     * @param direction   the direction of the piece, to get the image with the
     *                    correct direction
     * @author HhT
     */
    public void setPointImage(JButton buttonToSet, String pieceName, String direction) {
        String nameWithoutID = pieceName.replaceAll("\\d", "");
        String imageName = (direction.equals("NORTH")) ? nameWithoutID + "N.png" : nameWithoutID + "S.png";
        setImage(buttonToSet, imageName); // Set the image
    }

    /**
     * Set image for pieces.
     * 
     * @param button    the button to set the image on
     * @param imageName the name of the image to be set
     * @author HhT
     * @author Lim KZ
     */
    private void setImage(JButton button, String imageName) {
        URL imageUrl = getClass().getClassLoader().getResource(imageName); // Get image file with image name
        if (imageUrl != null) { // If image file is found
            Image image = new ImageIcon(imageUrl).getImage(); // Create image object
            // Create icon object using the image
            ImageIcon icon = new ImageIcon(
                    image.getScaledInstance((buttonWidth * 8 / 10), (buttonHeight * 8 / 10), Image.SCALE_SMOOTH));
            icon.setDescription(imageName); // Set description using image name
            button.setIcon(icon); // Set the icon for button
        } else { // If image file is not found
            // Print error message
            System.out.println("Image not found: " + imageName + ". Check if image is missing.");
        }
    }

    /**
     * Clear all buttons' images before setting them again.
     * 
     * @author HhT
     * @author Lim KZ
     */
    public void clearButtonsImages() {
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                chessButtons[r][c].setIcon(null);
            }
        }
    }

    /**
     * Set background color of a button.
     * 
     * @author HhT
     * @author Lim KZ
     */
    public void setButtonBackgroundColor(JButton button, Color color) {
        button.setBackground(color);
    }

    /**
     * Highlight or hide buttons according to moves position.
     * 
     * @author HhT
     * @author Lim KZ
     */
    public void setAvailableMovesColor(java.util.List<Move> availableMoves, Color color) {
        for (Move moves : availableMoves) {
            chessButtons[moves.getMoveRow()][moves.getMoveColumn()].setBackground(color);
        }
    }

    /**
     * Move image from one button to another.
     * 
     * @param originalButton the button to get the image
     * @param moveToButton   the button to set the image
     */
    public void moveButtonImage(JButton originalButton, JButton moveToButton) {
        Icon icon = originalButton.getIcon();
        moveToButton.setIcon(icon);
        originalButton.setIcon(null);
    }

    /**
     * Modify the images of transformed pieces.
     * 
     * @author HhT
     */
    public void transformImage() {
        // Iterate through the chess buttons array
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                // Check if the button has any icon
                Icon originalIcon = chessButtons[r][c].getIcon();
                if (originalIcon != null && originalIcon instanceof ImageIcon) { // If yes
                    // Get the name of the image from previously set description
                    String originalIconName = ((ImageIcon) originalIcon).getDescription();
                    // Initialize a String to set the image name for setting the image later
                    String newImageName = null;
                    // Check which image to set if image name is one of the following
                    if (originalIconName.equals("yPlus.png")) {
                        newImageName = "yTime";
                    } else if (originalIconName.equals("yTime.png")) {
                        newImageName = "yPlus";
                    } else if (originalIconName.equals("bPlus.png")) {
                        newImageName = "bTime";
                    } else if (originalIconName.equals("bTime.png")) {
                        newImageName = "bPlus";
                    }
                    // If there is an image name, meaning there is an image to be set
                    if (newImageName != null) {
                        setPieceImage(chessButtons[r][c], newImageName); // Set the image to button
                    }
                }
                // If no icon do nothing
            }
        }
    }
    /* -------------------------------------------------------------------------- */

    /* ------------------------- Pop up windows methods ------------------------- */
    /**
     * Display game over message and show the winner of the game.
     * 
     * @param winner the winner of the game
     * @author HhT
     */
    public void displayGameOver(String winner) {
        JOptionPane.showMessageDialog(this, ("Game Over! " + winner + " has won!"), "Talabia",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Display pop up to confirm whether to save game.
     * 
     * @return the option choosen by the user, including cancel
     * @author HhT
     */
    public int askSaveGame() {
        return JOptionPane.showConfirmDialog(this, "Do you want to save the current game?", "Save Game",
                JOptionPane.YES_NO_OPTION);
    }

    /**
     * Display pop up after game saved and the directory saved.
     * 
     * @author HhT
     */
    public void gameSavedPopup(String fileName, String directory) {
        JOptionPane.showMessageDialog(null, "\"" + fileName + "\" saved to '" + directory + "'");
    }

    /**
     * Display window that shows how to play the game.
     * 
     * @author HhT
     */
    public void showGuide() {
        // Create a dialog (a pop up window)
        JDialog dialog = new JDialog(this, "How to Play", true);

        // The *very very very* long message that will be shown
        String guideMessage = "<html>"
                + "<body>"
                + "<p>Talabia Chess is an engaging game played by 2 players on a 7x6 board, with interactive<br>gameplay involving clicking on the chess pieces displayed on your screen.</p>"
                + "<br>"
                + "<p>The game involves several unique pieces, each with distinct movement capabilities:</p>"
                + "<table border=\"1\">"
                + "<tr>"
                + "<td><b>The Point piece:</b> This piece can advance one or two steps forward. <br>Once it reaches the board's end, it reverses direction. </td>"
                + "</tr>"
                + "<tr>"
                + "<td><b>The Hourglass piece:</b> This piece moves in an L shape, spanning three steps<br>forward and two steps to the side in any direction.</td>"
                + "</tr>"
                + "<tr>"
                + "<td><b>The Time piece:</b> This piece can move diagonally across any number of squares.</td>"
                + "</tr>"
                + "<tr>"
                + "<td><b>The Plus piece:</b> This piece can traverse any distance horizontally or vertically.</td>"
                + "</tr>"
                + "<tr>"
                + "<td><b>The Sun piece:</b> This piece can move one step in any direction.</td>"
                + "</tr>"
                + "</table>"
                + "<p>All the pieces are not allowed to skip over other pieces except for The Hourglass."
                + "<br><br>"
                + "<p>Another intriguing aspect of Talabia Chess is the transformation of pieces.<br>After every two turns (considering a turn as one move by each player), all Time pieces<br>transform into Plus pieces, and all Plus pieces become Time pieces.<br>This dynamic element adds an extra layer of strategy to the game.</p>"
                + "<br>"
                + "<p>The game ends when one of The Sun is captured by opponent.</p>"
                + "<br>"
                + "<p>Good luck and have fun!</p>"
                + "</body>"
                + "</html>";

        JLabel label = new JLabel(guideMessage); // Put the long message into a label
        // Align the label
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        // Create a scrollable pop up and add the label to it
        JScrollPane scrollPane = new JScrollPane(label);

        dialog.getContentPane().add(scrollPane); // add the scrollpane to the dialog
        dialog.setSize(new Dimension(550, 450)); // Set the size of the dialog
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true); // Display the pop up
    }

    /**
     * Display pop window that includes simple description.
     * 
     * @author HhT
     */
    public void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Talabia Chess, created by group Nauru\nTan Hong Han\nLim Kian Zee\nLam Zi Foong\nChai Di Sheng\nTan Yi Kai\nVersion 1.1");
    }
    /* -------------------------------------------------------------------------- */

    /* ----------------------------- Class constants ---------------------------- */
    /**
     * @return the board row constant
     * @author HhT
     * @author Lim KZ
     */
    public static int getRowConstant() {
        return ROW;
    }

    /**
     * @return the board column constant
     * @author HhT
     * @author Lim KZ
     */
    public static int getColumnConstant() {
        return COLUMN;
    }
    /* ----------------------------------- End ---------------------------------- */
}