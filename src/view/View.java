package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;

import model.*;

public class View extends JFrame {
    private static final int ROWS = 6;
    private static final int COLUMN = 7;
    private Map<String, JMenuItem> menuItems = new HashMap<>();
    // private JPanel menuScreen; // to be implement
    private JPanel gameScreen;
    private JLabel playerLabel;
    private JLabel moveCountLabel;
    private JButton[][] chessButtons = new JButton[ROWS][COLUMN];

    public View() {
        super("Talabia");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                askSaveGame();
                System.exit(0);
            }
        });
        setSize(new Dimension(700, 600));

        /*
         * These components are initialized here first so that it is not returned null
         * when controller class is initialized
         */
        playerLabel = new JLabel("Current player:");
        moveCountLabel = new JLabel("Move Count:");
        // Adds empty button for the chess board
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMN; c++) {
                String buttonName = ("r" + r + "c" + c);
                JButton chessButton = new JButton(buttonName); // Set button text
                chessButton.setName(buttonName);
                Dimension buttonSize = new Dimension(100, 100);
                chessButton.setSize(buttonSize);
                chessButton.setText(null); // comment out if need to test
                chessButton.setBackground(Color.WHITE);
                chessButtons[r][c] = chessButton;
            }
        }

        // add explanation
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
                setLocationRelativeTo(null);
                setVisible(true);
            }
        });
    }

    // Method to create GUI components
    private void createGUI() {
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

        add(gameScreen);
        JMenuBar gameMenuBar = createMenu();
        setJMenuBar(gameMenuBar);
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
        menuItems.put("New", newGameItem);
        menuItems.put("Load", loadGameItem);
        menuItems.put("Save", saveGameItem);
        menuItems.put("Main Menu", mainMenuItem);
        menuItems.put("Guide", guideItem);
        menuItems.put("About", aboutItem);
        menuItems.put("Exit", exitItem);

        return menuBar;
    }

    // Get specific menu item from the map using the key (the name)
    public JMenuItem getMenuItem(String key) {
        return menuItems.get(key);
    }

    // (alternative to above)
    public void setPieceImage(JButton buttonToSet, String pieceName) {
        String imageName = (pieceName.replaceAll("[0-9]", "")) + ".png";
        URL imageUrl = getClass().getClassLoader().getResource(imageName);

        int buttonWidth = buttonToSet.getWidth();
        int buttonHeight = buttonToSet.getHeight();
        if (imageUrl != null) {
            Image image = new ImageIcon(imageUrl).getImage();
            ImageIcon icon = new ImageIcon(image.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH));
            icon.setDescription(imageName);
            // System.out.println(icon.getDescription()); // test
            buttonToSet.setIcon(icon);
        } else {
            System.out.println("Image not found: " + pieceName);
        }
    }

    // Set background color of a button
    public void setButtonBackgroundColor(JButton button, Color color) {
        button.setBackground(color);
    }

    // Highlight or hide available moves and the buttons related
    public void setAvailableMovesColor(java.util.List<Move> availableMoves, Color color) {
        for (Move moves : availableMoves) {
            // System.out.println("Move:" + moves.toString()); // test
            chessButtons[moves.getMoveRow()][moves.getMoveColumn()].setBackground(color);
        }
    }

    // Move images from one button to another
    public void moveButton(JButton originalButton, JButton movingButton) {
        Icon icon = originalButton.getIcon();
        movingButton.setIcon(icon);
        originalButton.setIcon(null);
    }

    // Get image from original button and update the new button with it
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
        System.out.println("Transform image"); // test
    }

    // Update labels
    public void setStatLabels(PlayerColor player, int moveCount) {
        /**
         * space at the end to prevent text sticking to window
         */
        moveCountLabel.setText("Move Count: " + Integer.toString(moveCount) + " ");

        String currentPlayer = (player == PlayerColor.YELLOW) ? "YELLOW" : "BLUE";
        playerLabel.setText("Current player: " + currentPlayer);
    }

    // Get a specific button from their position
    public JButton getButton(int row, int col) {
        return chessButtons[row][col];
    }

    // Get the button
    public Move getButtonPosition(JButton button) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMN; c++) {
                if (chessButtons[r][c] == button) {
                    return new Move(r, c); // return the position as move object
                }
            }
        }

        return null;
    }

    // Display game over message (add parameters to show who won)
    public void displayGameOver(String winner) {
        JOptionPane.showMessageDialog(this, ("Game Over! " + winner + " has won!"), "Talabia",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Display pop up to confirm whether to save game
    public void askSaveGame() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to save the current game?", "Save Game",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.out.println("Save game: Yes"); // test
            // Save the game here
        } else if (option == JOptionPane.NO_OPTION) {
            System.out.println("Save game: No"); // test
            // Don't save the game
        }
    }

    // Display window that shows how to play the game
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
        dialog.setVisible(true);
    }

    // Display pop window that includes simple description
    public void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Talabia Chess, created by group Nauru\nTan Hong Han\nLim Kian Zee\nLam Zi Foong\nChai Di Sheng\nTan Yi Kai\nVersion 1.0");
    }
}
