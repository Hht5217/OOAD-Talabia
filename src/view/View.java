package view;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import model.*;

public class View {
    private JButton[][] chessButtons = new JButton[6][7];

    public View() {
        JFrame talabiaFrame = new JFrame("Talabia");
        talabiaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        talabiaFrame.setSize(700, 600);
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 7));

        // Adds empty button for the chess board
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                String buttonName = ("r" + r + "c" + c);
                JButton chessButton = new JButton(buttonName); // Set button text
                chessButton.setName(buttonName);
                Dimension buttonSize = new Dimension(100, 100);
                chessButton.setSize(buttonSize);
                chessButton.setForeground(java.awt.Color.WHITE); // for test, remove later, use below
                // chessButton.setText(null);
                chessButton.setBackground(java.awt.Color.WHITE);
                chessButtons[r][c] = chessButton;
                boardPanel.add(chessButton);
            }
        }

        talabiaFrame.add(boardPanel);
        talabiaFrame.setLocationRelativeTo(null);
        // talabiaFrame.pack();
        talabiaFrame.setVisible(true);
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
    public void moveButton(JButton oriButton, JButton movingButton) {
        Icon icon = oriButton.getIcon();
        movingButton.setIcon(icon);
        oriButton.setIcon(null);
    }

    public void transformImage() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
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

    // Get chess buttons array
    public JButton[][] getChessButtons() {
        return chessButtons;
    }

    // Get the button
    public Move getButtonPosition(JButton button) {
        for (int r = 0; r < chessButtons.length; r++) {
            for (int c = 0; c < chessButtons[r].length; c++) {
                if (chessButtons[r][c] == button) {
                    return new Move(r, c); // return the position as move object
                }
            }
        }

        return null;
    }
}
