package src.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.Position;

import src.model.Board;
import src.model.Game;
import src.model.Move;
import src.model.Piece;

public class View {
    private Game game;
    private JButton[][] chessButtons = new JButton[6][7];
    // private Dimension chessButtonDimension;

    public View(Game game) {
        this.game = game;
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

        addPieceImage(game);
        talabiaFrame.add(boardPanel);
        talabiaFrame.setLocationRelativeTo(null);
        // talabiaFrame.pack();
        talabiaFrame.setVisible(true);
    }

    public void addPieceImage(Game game) {
        Board talabiaChessBoard = game.getTalabiaChessBoard();
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                Piece piece = talabiaChessBoard.getPiece(r, c);
                if (piece != null) {
                    int buttonWidth = chessButtons[r][c].getWidth();
                    int buttonHeight = chessButtons[r][c].getHeight();
                    chessButtons[r][c].setIcon(piece.getPieceImage(buttonWidth, buttonHeight));
                } else {
                    chessButtons[r][c].setIcon(null);
                }
            }
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
    public void showAvailableMoves(Piece piece) {
        for (Move moves : piece.getAvailableMoves()) {
            // System.out.println("Move:" + moves.toString()); // test
            chessButtons[moves.getMoveRow()][moves.getMoveColumn()].setBackground(java.awt.Color.BLUE);
        }
    }

    // Remove highlight from available moves buttons
    public void hideAvailableMoves(Piece piece) {
        for (Move moves : piece.getAvailableMoves()) {
            // System.out.println("Move:" + moves.toString()); // test
            chessButtons[moves.getMoveRow()][moves.getMoveColumn()].setBackground(java.awt.Color.WHITE);
        }
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
