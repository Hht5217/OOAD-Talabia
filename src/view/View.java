package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;

import model.*;

public class View extends JFrame {
    // For this program the width / row and height column remains the same
    private static final int ROWS = 6;
    private static final int COLUMN = 7;

    // Maps of components that needs to add action listeners
    private Map<String, JButton> menuButtons = new HashMap<>(); // Store the buttons on first screen
    private Map<String, JMenuItem> menuBarItems = new HashMap<>(); // Store menu bar components

    // Screens/view of the program
    private JPanel menuScreen; // The first screen when program starts
    private JPanel gameScreen; // The screen when game is running
    private boolean isGameScreen = false; // To check whether to switch to game screen

    // Essential components for the program
    private JMenuBar gameMenuBar; // The navigation bar
    private JLabel playerLabel; // The text below the navigation bar
    private JLabel moveCountLabel; // Same as above
    private JButton[][] chessButtons = new JButton[ROWS][COLUMN]; // The 2d array to store buttons
    private CardLayout cardLayout = new CardLayout(); // The cardlayout, used to switch screens
    private JPanel screens = new JPanel(cardLayout); // A holder for the screens that uses cardlayout

    private int buttonWidth;
    private int buttonHeight;

    public View() {
        super("Talabia"); // Title of the program
        setSize(new Dimension(700, 600)); // The size when program starts
        setMinimumSize(new java.awt.Dimension(700, 600)); // set minimumsize for JFrame

        /*
         * These components are initialized here first so that it is not returned null
         * when controller class is initialized
         */
        playerLabel = new JLabel("Current player:");
        moveCountLabel = new JLabel("Move Count:");

        // Add empty buttons to the 2d array
        for (int r = 0; r < ROWS; r++) {
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

    /*
     * Do nothing when close window is clicked, but implements conditions to do
     * something when clicked, in this case display a window to ask user whether to
     * save game. The Runnable saveGameActon is to let the menu controller class to
     * run the saveGame method.
     */
    public void setUpCloseWindowHandler(Runnable saveGameAction) {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isGameScreen) {
                    if (askSaveGame()) {
                        saveGameAction.run();
                    }
                }
                System.exit(0);
            }
        });
    }

    /*
     * The method to create essential GUI components, mostly those that will be used
     * throughout the program
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

    /* Create and return menu screen */
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

    /* Create and return game screen */
    private JPanel createGameScreen() {
        gameScreen = new JPanel();
        gameScreen.setLayout(new BorderLayout());
        /**
         * currently using borderlayout for statPanel, if more labels will be added then
         * consider using flowlayout
         */
        JPanel statPanel = new JPanel(new BorderLayout());

        statPanel.add(playerLabel, BorderLayout.WEST);
        statPanel.add(moveCountLabel, BorderLayout.EAST);

        gameScreen.add(statPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 7));
        gameScreen.add(boardPanel, BorderLayout.CENTER);

        // Adds empty button for the chess board
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMN; c++) {
                boardPanel.add(chessButtons[r][c]);
            }
        }

        return gameScreen;
    }

    /*
     * Separate method since this part is too long, and easy to modify such as add
     * new menu item
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

    /* Switch to menu screen from the screens holder using cardlayout */
    public void switchToMenuScreen() {
        cardLayout.show(screens, "MenuScreen");
        isGameScreen = false;
        // Hide the menu bar when switching to the menu screen
        setJMenuBar(null);
    }

    /* Switch to game screen from the screens holder using cardlayout */
    public void switchToGameScreen() {
        cardLayout.show(screens, "GameScreen");
        isGameScreen = true;
        // Show the menu bar when switching to the game screen
        setJMenuBar(gameMenuBar);
    }

    /* Get the boolean to see if the current screen displayed is game screen */
    public boolean isGameScreenDisplayed() {
        return isGameScreen;
    }

    /* Get specific main menu button from the map using the key (the name) */
    public JButton getMainMenuButton(String key) {
        return menuButtons.get(key);
    }

    /* Get specific menu item from the map using the key (the name) */
    public JMenuItem getMenuBarItem(String key) {
        return menuBarItems.get(key);
    }

    /* Set images to buttons according to pieces' name */
    public void setPieceImage(JButton buttonToSet, String pieceName) {
        String imageName = (pieceName.replaceAll("\\d", "")) + ".png"; // Remove all digits from pieceName
        setImage(buttonToSet, imageName);
    }

    /**
     * Used for setting image for Point pieces
     * 
     * @param buttonToSet the button that image will be updated
     * @param pieceName   the name of the piece, specifically Point piece
     * @param direction   the direction of the piece and also the direction of the
     *                    image to be set
     */
    public void setPointImage(JButton buttonToSet, String pieceName, String direction) {
        String nameWithoutID = pieceName.replaceAll("\\d", "");
        String imageName = (direction.equals("NORTH")) ? nameWithoutID + "N.png" : nameWithoutID + "S.png";
        setImage(buttonToSet, imageName);
    }

    /* The general set image method */
    private void setImage(JButton button, String imageName) {
        URL imageUrl = getClass().getClassLoader().getResource(imageName);
        if (imageUrl != null) {
            Image image = new ImageIcon(imageUrl).getImage();
            ImageIcon icon = new ImageIcon(
                    image.getScaledInstance((buttonWidth * 8 / 10), (buttonHeight * 8 / 10), Image.SCALE_SMOOTH));
            icon.setDescription(imageName);
            button.setIcon(icon);
        } else {
            System.out.println("Image not found: " + imageName);
        }
    }

    /* Clear all buttons' images before setting them again */
    public void clearButtonsImages() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMN; c++) {
                chessButtons[r][c].setIcon(null);
            }
        }
    }

    /* Set background color of a button */
    public void setButtonBackgroundColor(JButton button, Color color) {
        button.setBackground(color);
    }

    /* Highlight or hide available moves and the buttons related */
    public void setAvailableMovesColor(java.util.List<Move> availableMoves, Color color) {
        for (Move moves : availableMoves) {
            chessButtons[moves.getMoveRow()][moves.getMoveColumn()].setBackground(color);
        }
    }

    /* Move images from one button to another */
    public void moveButton(JButton originalButton, JButton movingButton) {
        Icon icon = originalButton.getIcon();
        movingButton.setIcon(icon);
        originalButton.setIcon(null);
    }

    /**
     * First iterate through the chess buttons array to check if there is any icon.
     * If yes then check the description of the icon, in this case description has
     * been set as piece name .png beforehand. If the icon description is equal to
     * the pieces that will transform, then insert new image name (the name of image
     * that will be updated), then use set piece image method to update the icon.
     */
    public void transformImage() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMN; c++) {
                Icon originalIcon = chessButtons[r][c].getIcon();
                if (originalIcon != null && originalIcon instanceof ImageIcon) {
                    String originalIconName = ((ImageIcon) originalIcon).getDescription();
                    String newImageName = null;
                    if (originalIconName.equals("yPlus.png")) {
                        newImageName = "yTime";
                    } else if (originalIconName.equals("yTime.png")) {
                        newImageName = "yPlus";
                    } else if (originalIconName.equals("bPlus.png")) {
                        newImageName = "bTime";
                    } else if (originalIconName.equals("bTime.png")) {
                        newImageName = "bPlus";
                    }
                    if (newImageName != null) {
                        setPieceImage(chessButtons[r][c], newImageName);
                    }
                }
            }
        }
    }

    /* Set the labels of move count and current player */
    public void setStatLabels(PlayerColor player, int moveCount) {
        // Space at the end to prevent text sticking to window
        moveCountLabel.setText("Move Count: " + Integer.toString(moveCount) + " ");

        // If player is yellow then set yellow, if not then blue
        String currentPlayer = (player == PlayerColor.YELLOW) ? "YELLOW" : "BLUE";
        playerLabel.setText("Current player: " + currentPlayer);
    }

    /* Get a specific button from their position */
    public JButton getButton(int row, int col) {
        return chessButtons[row][col];
    }

    /*
     * ^- These two are different, one is get button from position and one is get
     * position from button -v
     */

    /* Get the button's position */
    public Move getButtonPosition(JButton button) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMN; c++) {
                if (chessButtons[r][c] == button) {
                    return new Move(r, c); // return the position as move object (pair of integers)
                }
            }
        }

        return null;
    }

    /* Display game over message (add parameters to show who won) */
    public void displayGameOver(String winner) {
        JOptionPane.showMessageDialog(this, ("Game Over! " + winner + " has won!"), "Talabia",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /* Display pop up to confirm whether to save game */
    public boolean askSaveGame() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to save the current game?", "Save Game",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    /* Display window that shows how to play the game */
    public void showGuide() {
        JDialog dialog = new JDialog(this, "How to Play", true);

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

        JLabel label = new JLabel(guideMessage);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        // Create a JScrollPane and add the JLabel to it
        JScrollPane scrollPane = new JScrollPane(label);

        dialog.getContentPane().add(scrollPane);
        dialog.setSize(new Dimension(550, 450)); // Set the size of the dialog
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true); // Display the pop up
    }

    /* Display pop window that includes simple description */
    public void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Talabia Chess, created by group Nauru\nTan Hong Han\nLim Kian Zee\nLam Zi Foong\nChai Di Sheng\nTan Yi Kai\nVersion 1.0");
    }
}