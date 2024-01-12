package controller;

import model.*;
import view.*;

//import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller implements GameObserver {
    private Game talabiaGame;
    private View talabiaView;
    private Board talabiaBoard;
    private JButton lastSelectButton = null;
    private Piece lastSelectPiece = null;
    private boolean isGameOver = false;

    public Controller(Game tGame, View tView) {
        this.talabiaGame = tGame;
        this.talabiaView = tView;
        this.talabiaBoard = tGame.getGameBoard();
        talabiaGame.addObserver(this);
    }

    // Initialize controller
    public void initController() {
        JButton[][] initButtons = talabiaView.getChessButtons();

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                JButton button = initButtons[r][c];
                Piece piece = talabiaBoard.getPiece(r, c);
                if (piece != null) {
                    talabiaView.setPieceImage(button, piece.toString());
                }
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        mouseAction(button, talabiaBoard.getBoard());
                    }
                });
            }
        }
    }

    // Almost all action are done by mouse action
    private void mouseAction(JButton selectedButton, Piece[][] gameBoard) {
        if (isGameOver) { // if game over is true the method stops at here, as it returns nothing
            return;
        }
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
            talabiaView.hideAvailableMoves(lastSelectPiece.getAvailableMoves());
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
        talabiaView.showAvailableMoves(piece.getAvailableMoves());
        lastSelectPiece = piece;
        lastSelectButton = button;
    }

    // Deselect piece action
    private void deselectPiece(JButton button, Piece piece) {
        piece.setSelected(false);
        talabiaView.deselectButton(button);
        talabiaView.hideAvailableMoves(piece.getAvailableMoves());
        lastSelectPiece = null;
        lastSelectButton = null;
    }

    // Move after piece's available move is clicked
    private void moveSelected(JButton oriButton, JButton movingButton, Piece movingPiece) {
        talabiaView.moveButton(oriButton, movingButton);
        Move movePos = talabiaView.getButtonPosition(movingButton);
        talabiaGame.movePiece(movingPiece, movePos);
        talabiaGame.setMoveCount();
        if (talabiaGame.checkTransformation()) {
            talabiaGame.allowTransformation();
            talabiaView.transformImage();
        }
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

    @Override
    public void onGameOver() {
        isGameOver = true; // change state to game over true
        talabiaView.displayGameOver();
    }
}
