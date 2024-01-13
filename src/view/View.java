package view;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import model.*;
import model.Color;

public class View extends JFrame {
    // private JFrame talabiaFrame; // old
    private static final int ROWS = 6;
    private static final int COLUMN = 7;
    // private JPanel menuScreen; // to be implement
    private JPanel gameScreen;
    private JLabel playerLabel;
    private JLabel moveCountLabel;
    private JButton[][] chessButtons = new JButton[ROWS][COLUMN];

    public View() {
        // talabiaFrame = new JFrame("Talabia"); // old
        super("Talabia");
        // talabiaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // old
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // talabiaFrame.setSize(700, 600); // old
        setSize(new Dimension(700, 600));

        gameScreen = new JPanel();
        gameScreen.setLayout(new BorderLayout());

        /**
         * currently using borderlayout for statPanel, if more labels will be added then
         * consider using flowlayout
         */
        JPanel statPanel = new JPanel(new BorderLayout());

        playerLabel = new JLabel("Current player:");
        moveCountLabel = new JLabel("Move Count:");
        statPanel.add(playerLabel, BorderLayout.WEST);
        statPanel.add(moveCountLabel, BorderLayout.EAST);

        gameScreen.add(statPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 7));
        gameScreen.add(boardPanel, BorderLayout.CENTER);

        // Adds empty button for the chess board
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMN; c++) {
                String buttonName = ("r" + r + "c" + c);
                JButton chessButton = new JButton(buttonName); // Set button text
                chessButton.setName(buttonName);
                Dimension buttonSize = new Dimension(100, 100);
                chessButton.setSize(buttonSize);
                // chessButton.setText(null); // test, remember to uncomment
                chessButton.setBackground(java.awt.Color.WHITE);
                chessButtons[r][c] = chessButton;
                boardPanel.add(chessButton);
            }
        }

        // old
        // talabiaFrame.add(boardPanel);
        // talabiaFrame.setLocationRelativeTo(null);
        // // talabiaFrame.pack();
        // talabiaFrame.setVisible(true);

        // maybe can call an update statPanel method here after everyhting else is done
        add(gameScreen);
        setLocationRelativeTo(null);
        setVisible(true);
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

    // Highlight button selected
    public void selectButton(JButton buttonSelected) {
        buttonSelected.setBackground(java.awt.Color.GREEN);
    }

    // Remove highlight from previously selected button
    public void deselectButton(JButton buttonDeselect) {
        buttonDeselect.setBackground(java.awt.Color.WHITE);
    }

    // Highlight available moves and the buttons related
    public void showAvailableMoves(java.util.List<Move> availableMoves) {
        for (Move moves : availableMoves) {
            // System.out.println("Move:" + moves.toString()); // test
            chessButtons[moves.getMoveRow()][moves.getMoveColumn()].setBackground(java.awt.Color.BLUE);
        }
    }

    // Remove highlight from available moves buttons
    public void hideAvailableMoves(java.util.List<Move> availableMoves) {
        for (Move moves : availableMoves) {
            // System.out.println("Move:" + moves.toString()); // test
            chessButtons[moves.getMoveRow()][moves.getMoveColumn()].setBackground(java.awt.Color.WHITE);
        }
    }

    // Move images from one button to another
    public void moveButton(JButton originalButton, JButton movingButton) {
        Icon icon = originalButton.getIcon();
        movingButton.setIcon(icon);
        originalButton.setIcon(null);
    }

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
    public void setStatLabels(model.Color player, int moveCount) {
        /**
         * space at the end to prevent text sticking to window
         */
        moveCountLabel.setText("Move Count: " + Integer.toString(moveCount) + " ");
        if (moveCount == 0) {
            String currentPlayer = (player == Color.YELLOW) ? "YELLOW" : "BLUE";
            playerLabel.setText("Current player: " + currentPlayer);
        } else {
            String currentPlayer = (player == Color.YELLOW) ? "BLUE" : "YELLOW";
            playerLabel.setText("Current player: " + currentPlayer);
        }
    }

    // Get chess buttons array
    public JButton[][] getChessButtons() {
        return chessButtons;
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
    public void displayGameOver() {
        JOptionPane.showMessageDialog(gameScreen, "Game Over!", "Talabia", JOptionPane.INFORMATION_MESSAGE);
    }

    // add a change Point direction method if possible
}
