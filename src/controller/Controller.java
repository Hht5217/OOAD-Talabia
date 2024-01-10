package controller;

import model.*;
import view.*;

//import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller {
    private Game talabiaGame;
    private View talabiaView;
    private Board talabiaBoard;
    private JButton lastSelectButton = null;
    private Piece lastSelectPiece = null;

    public Controller(Game tGame, View tView) {
        this.talabiaGame = tGame;
        this.talabiaView = tView;
        this.talabiaBoard = tGame.getGameBoard();
    }

    // Initialize controller
    public void initController() {
        JButton[][] viewButtons = talabiaView.getChessButtons();

        for (int r = 0; r < viewButtons.length; r++) {
            for (int c = 0; c < viewButtons[r].length; c++) {
                JButton selectedButton = viewButtons[r][c];
                selectedButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        mouseAction(selectedButton, talabiaBoard.getBoard());
                    }
                });
            }
        }
    }

    // Almost all action are done by mouse action
    private void mouseAction(JButton selectedButton, Piece[][] gameBoard) {
        Piece selectedPiece = matchPieceButton(selectedButton, gameBoard);
        System.out.println("Current: " + selectedButton.getName()); // test
        if (selectedPiece != null && selectedPiece.getColor() == talabiaGame.getPlayer()) {
            if (selectedButton == lastSelectButton) {
                deselectPiece(lastSelectButton, lastSelectPiece);
            } else if (lastSelectButton == null || !isMoveButton(selectedButton, lastSelectPiece)) {
                if (lastSelectButton != null) {
                    deselectPiece(lastSelectButton, lastSelectPiece);
                }
                selectPiece(selectedButton, selectedPiece);
            }
        } else if (lastSelectPiece != null && isMoveButton(selectedButton, lastSelectPiece)) {
            talabiaView.hideAvailableMoves(lastSelectPiece);
            moveSelected(lastSelectButton, selectedButton, lastSelectPiece);
            deselectPiece(lastSelectButton, lastSelectPiece);
            System.out.println("Moved\n*******************************"); // test
            talabiaBoard.printBoard();
        }
    }

    // Select piece action
    private void selectPiece(JButton button, Piece piece) {
        piece.setSelected(true);
        talabiaView.selectButton(button);
        talabiaView.showAvailableMoves(piece);
        lastSelectPiece = piece;
        lastSelectButton = button;
    }

    // Deselect piece action
    private void deselectPiece(JButton button, Piece piece) {
        piece.setSelected(false);
        talabiaView.deselectButton(button);
        talabiaView.hideAvailableMoves(piece);
        lastSelectPiece = null;
        lastSelectButton = null;
    }

    // Move after piece's available move is clicked
    private void moveSelected(JButton oriButton, JButton movingButton, Piece movingPiece) {
        talabiaView.moveButton(oriButton, movingButton);
        Move movePos = talabiaView.getButtonPosition(movingButton);
        // talabiaGame.movePiece
        talabiaGame.movePiece(movingPiece, movePos);
        talabiaGame.setTurn();
        talabiaGame.setPlayer();
    }

    // Check if the button is a move button
    private boolean isMoveButton(JButton button, Piece piece) {
        if (piece == null) {
            return false;
        }
        Move buttonPos = talabiaView.getButtonPosition(button);
        for (Move move : piece.getAvailableMoves()) {
            if (move.getMoveRow() == buttonPos.getMoveRow() && move.getMoveColumn() == buttonPos.getMoveColumn()) {
                return true;
            }
        }
        return false;
    }

    // Get piece matching to button
    public Piece matchPieceButton(JButton buttonSelected, Piece[][] gameBoard) {
        String buttonName = buttonSelected.getName();
        String[] namePos = buttonName.split("r|c"); // split the string on 'r' and 'c'
        int r = Integer.parseInt(namePos[1]);
        int c = Integer.parseInt(namePos[2]);
        return gameBoard[r][c];
    }
}
