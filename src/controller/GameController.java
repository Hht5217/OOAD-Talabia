package controller;

import model.*;
import pieces.*;
import view.*;

import java.awt.*;
import javax.swing.*;

public class GameController implements GameObserver {
    private Game game;
    private View view;
    private Board board;
    private JButton lastSelectButton = null;
    private Piece lastSelectPiece = null;

    public GameController(Game talabiaGame, View talabiaView) {
        this.game = talabiaGame;
        this.view = talabiaView;
        view.setStatLabels(game.getPlayer(), game.getMoveCount());
        this.board = game.getGameBoard();
        game.addObserver(this);
    }

    // Initialize controller
    public void initController() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                JButton button = view.getButton(r, c);
                button.addActionListener(e -> mouseAction(button));
            }
        }
    }

    // Almost all action are done by mouse action
    private void mouseAction(JButton selectedButton) {
        if (game.checkGameOver()) { // if game over is true the method stops at here, as it returns nothing
            return;
        }
        Piece selectedPiece = matchPieceButton(selectedButton);
        if (selectedPiece != null && selectedPiece.getColor() == game.getPlayer()) {
            if (selectedButton == lastSelectButton) {
                deselectPiece(lastSelectButton, lastSelectPiece);
            } else if (lastSelectButton == null || !isMoveButton(selectedButton, lastSelectPiece)) {
                if (lastSelectButton != null) {
                    deselectPiece(lastSelectButton, lastSelectPiece);
                }
                selectPiece(selectedButton, selectedPiece);
            }
        } else if (lastSelectPiece != null && isMoveButton(selectedButton, lastSelectPiece)) {
            view.setAvailableMovesColor(lastSelectPiece.getAvailableMoves(), Color.WHITE);
            moveSelected(lastSelectButton, selectedButton, lastSelectPiece);
            deselectPiece(lastSelectButton, lastSelectPiece);
        }
    }

    // Select piece action
    private void selectPiece(JButton button, Piece piece) {
        piece.setSelected(true);
        view.setButtonBackgroundColor(button, Color.CYAN);
        view.setAvailableMovesColor(piece.getAvailableMoves(), Color.GREEN);
        lastSelectPiece = piece;
        lastSelectButton = button;
    }

    // Deselect piece action
    private void deselectPiece(JButton button, Piece piece) {
        piece.setSelected(false);
        view.setButtonBackgroundColor(button, Color.WHITE);
        view.setAvailableMovesColor(piece.getAvailableMoves(), Color.WHITE);
        lastSelectPiece = null;
        lastSelectButton = null;
    }

    // Move after piece's available move is clicked
    private void moveSelected(JButton oriButton, JButton movingButton, Piece movingPiece) {
        view.moveButton(oriButton, movingButton);
        Move movePos = view.getButtonPosition(movingButton);
        game.movePiece(movingPiece, movePos);
        game.setPlayer();
        game.setMoveCount();
        if (game.checkTransformation() && !game.checkGameOver()) { // in case game over then no need to transform
            game.allowTransformation();
            view.transformImage();
        }
        view.setStatLabels(game.getPlayer(), game.getMoveCount());
    }

    // Check if the button is a move button
    private boolean isMoveButton(JButton button, Piece piece) {
        if (piece == null) {
            return false;
        }
        Move buttonPos = view.getButtonPosition(button);
        for (Move move : piece.getAvailableMoves()) {
            if (move.getMoveRow() == buttonPos.getMoveRow() && move.getMoveColumn() == buttonPos.getMoveColumn()) {
                return true;
            }
        }
        return false;
    }

    // Get piece matching to button
    public Piece matchPieceButton(JButton buttonSelected) {
        String buttonName = buttonSelected.getName();
        String[] namePos = buttonName.split("r|c"); // split the string on 'r' and 'c'
        int r = Integer.parseInt(namePos[1]);
        int c = Integer.parseInt(namePos[2]);
        return board.getPiece(r, c);
    }

    /*
     * Set the boolean to true when game over state is observed (notified by game
     * class), and afterwards display a popup window
     */
    @Override
    public void onGameOver() {
        game.setGameOver(true);
        view.displayGameOver(game.getPlayer().toString());
    }

    @Override
    public void onNewGame() {
        game.setGameOver(false); // Reset game over to false
        game.addPointsObserver(this); // Readd observer to newly created pieces
    }

    @Override
    public void onLoadGame() {
        // get the model and update view accordingly
        game.addPointsObserver(this);
        System.out.println("Observer: notified load game"); // test
    }

    @Override
    public void onDirectionChange(int yPos, int xPos, String pieceName, String direction) {
        JButton button = view.getButton(yPos, xPos);
        view.setPointImage(button, pieceName, direction);
    }
}
