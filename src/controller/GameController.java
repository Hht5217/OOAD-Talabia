/**
 * The GameController class is responsible for handling the game logic and
 * reacting to user interactions. This class is using Observer pattern by
 * implementing the GameObserver interface. It can receive updates about 
 * changes in the game state.
 */
package controller;

import model.*;
import pieces.*;
import view.*;

import java.awt.Color;
import javax.swing.JButton;

public class GameController implements GameObserver {
    private Game game; // Model class, handles the state of the game
    private View view; // View class, handles the gui
    private Board board; // Model class, contains and manage the movement of the pieces
    private JButton lastSelectButton = null; // The last button selected
    private Piece lastSelectPiece = null; // The last piece selected

    /**
     * The constructor of GameController class.
     * 
     * @param talabiaGame the game instance
     * @param talabiaView the view instance
     * @author HhT
     */
    public GameController(Game talabiaGame, View talabiaView) {
        this.game = talabiaGame;
        this.view = talabiaView;
        this.board = game.getGameBoard(); // Get the board initialized in the game instance

        // Initialize the labels with the initial game state values
        view.setStateLabels(game.getPlayer(), game.getMoveCount());
        game.addObserver(this); // Add observer to game instance
    }

    /* ----------------------------- Initialization ----------------------------- */
    /**
     * Initialize the buttons by adding action listener to them.
     * 
     * @author HhT
     */
    public void initController() {
        for (int r = 0; r < View.getRowConstant(); r++) {
            for (int c = 0; c < View.getColumnConstant(); c++) {
                JButton button = view.getButton(r, c);
                button.addActionListener(e -> buttonAction(button));
            }
        }
    }
    /* -------------------------------------------------------------------------- */

    /* ----------------------------- Button actions ----------------------------- */
    /**
     * The if else statements of actions to perform when a button is clicked.
     * 
     * @param selectedButton the button clicked
     * @author HhT
     */
    private void buttonAction(JButton selectedButton) {
        if (game.checkGameOver()) { // If game over is true then whatever action will return nothing
            return;
        }

        // Get the corresponding piece using the button position.
        Piece selectedPiece = matchPieceButton(selectedButton);

        /*
         * If there is a selected piece and it belongs to the current player (the piece
         * and player are the same color)
         */
        if (selectedPiece != null && selectedPiece.getColor() == game.getPlayer()) {
            /*
             * If the current clicked button is same as last clicked button, simply means
             * the button is clicked twice in a row
             */
            if (selectedButton == lastSelectButton) {
                // Deselect the button and the piece
                deselectPiece(lastSelectButton, lastSelectPiece);
            }
            /*
             * If there is no button selected before, or the button selected is not a move
             * of the last selected piece
             */
            else if (lastSelectButton == null || !isMoveButton(selectedButton, lastSelectPiece)) {
                /*
                 * If there is a button selected before, this is reached if it is true for the
                 * button selected being not a move button, meaning a moveable piece button is
                 * selected but the user decides to click another button
                 */
                if (lastSelectButton != null) {
                    deselectPiece(lastSelectButton, lastSelectPiece);
                }
                // Select the current clicked button and the piece
                selectPiece(selectedButton, selectedPiece);
            }
        }
        /*
         * If there is a last selected piece and the button clicked is a move of the
         * last selected piece
         */
        else if (lastSelectPiece != null && isMoveButton(selectedButton, lastSelectPiece)) {
            // Hide all the move buttons of the piece after moving
            view.setAvailableMovesColor(lastSelectPiece.getAvailableMoves(), Color.WHITE);
            // Perform the action when a move button is selected
            moveSelected(lastSelectButton, selectedButton, lastSelectPiece);
            // Deselect the button and piece after move is made
            deselectPiece(lastSelectButton, lastSelectPiece);
        }
    }

    /**
     * Select a button and piece.
     * 
     * @param button the button to select
     * @param piece  the piece to select
     */
    private void selectPiece(JButton button, Piece piece) {
        piece.setSelected(true); // Set the selected state to true
        view.setButtonBackgroundColor(button, Color.CYAN); // Highlight the selected button
        view.setAvailableMovesColor(piece.getAvailableMoves(), Color.GREEN); // Highlight the moves button
        lastSelectPiece = piece; // Set the selected piece as last selected and wait for next action
        lastSelectButton = button; // Set the selected button as last selected button
    }

    /**
     * Deselet a button and piece.
     * 
     * @param button the button to deselect
     * @param piece  the piece to deselect
     * @author HhT
     */
    private void deselectPiece(JButton button, Piece piece) {
        piece.setSelected(false); // Set the selected state to false
        view.setButtonBackgroundColor(button, Color.WHITE); // Unhighlight the button after deselected
        view.setAvailableMovesColor(piece.getAvailableMoves(), Color.WHITE); // Unhighlight the moves button
        lastSelectPiece = null; // Set as null after piece is deselected
        lastSelectButton = null; // Set as null
    }

    /**
     * Make a move when a move button is selected.
     * 
     * @param originalButton the button where the piece is at before moving
     * @param moveToButton   the move button selected
     * @param movingPiece    the piece to be moved
     * @author HhT
     */
    private void moveSelected(JButton originalButton, JButton moveToButton, Piece movingPiece) {
        view.moveButtonImage(originalButton, moveToButton); // Move image of button
        Move movePos = view.getButtonPosition(moveToButton); // Get the position of the move button clicked
        game.movePiece(movingPiece, movePos); // Move the piece
        game.setPlayer(); // Change to next player turn
        game.setMoveCount(); // Set the move count
        if (game.checkTransformation() && !game.checkGameOver()) { // If game is over then no need to transform
            game.allowTransformation(); // Perform the transformation
            view.transformImage(); // Modify the image of the transformed pieces
        }
        view.setStateLabels(game.getPlayer(), game.getMoveCount()); // Set the labels of the game states
    }

    /**
     * Check whether the selected button is a move button of the selected piece
     * 
     * @param button the button selected
     * @param piece  the current piece selected that can move
     * @return false if there is no piece selected
     * @return true if button selected is a move of the piece
     * @author HhT
     */
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

    /**
     * Get the piece corresponding to the button. Can return null if there is no
     * piece at the position of the button selected
     * 
     * @param buttonSelected the button selected
     * @return the piece at the position of the button
     * @author HhT
     */
    public Piece matchPieceButton(JButton buttonSelected) {
        String buttonName = buttonSelected.getName(); // Get the name (position) of the button
        String[] namePos = buttonName.split("r|c"); // Split the string on 'r' and 'c'
        int r = Integer.parseInt(namePos[1]); // The row where the piece is at
        int c = Integer.parseInt(namePos[2]); // The column where the piece is at
        return board.getPiece(r, c);
    }
    /* -------------------------------------------------------------------------- */

    /* ---------------------------- Observer methods ---------------------------- */
    /**
     * Set game over when notified and display a game over pop up window.
     * 
     * @author HhT
     */
    @Override
    public void onGameOver() {
        game.setGameOver(true);
        view.displayGameOver(game.getPlayer().toString()); // Pass the winner of the game
    }

    /**
     * Reset the game and readd observers to new Point pieces of the new game.
     * 
     * @author HhT
     */
    @Override
    public void onNewGame() {
        game.setGameOver(false); // Reset game over to false
        game.addPointsObserver(this); // Readd observer
    }

    /**
     * Add observer to loaded Point pieces after game is loaded.
     * 
     * @author HhT
     */
    @Override
    public void onLoadGame() {
        game.addPointsObserver(this); // Add observer
    }

    /**
     * Modify the image of the button of Point pieces when Point pieces has changed
     * moving direction.
     * 
     * @param yPos      the y position of the Point piece
     * @param xPos      the x position of the Point piece
     * @param pieceName the name of the Point piece, to check color of piece
     * @param direction the moving direction of Point piece
     * @author HhT
     */
    @Override
    public void onDirectionChange(int yPos, int xPos, String pieceName, String direction) {
        JButton button = view.getButton(yPos, xPos);
        view.setPointImage(button, pieceName, direction);
    }
    /* ----------------------------------- End ---------------------------------- */
}
