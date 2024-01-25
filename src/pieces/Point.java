/**
 * The Point class extends the Piece class and represents the Point piece in the
* game. The Point piece has an additional property, moveDirection, which
* determines the direction in which the piece can move.
*/
package pieces;

import java.util.*;

import controller.GameObserver;
import model.*;

public class Point extends Piece {
    private String moveDirection = ""; // The moving direction, initialize to reduce error
    private List<GameObserver> observers = new ArrayList<>(); // The list of observers

    /**
     * Constructor of Point piece. Values are inherited from Piece class, plus an
     * addition of moveDirection properties.
     * 
     * @author HhT
     */
    public Point(String id, int yPos, int xPos, PlayerColor color, String moveDirection, Board pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
        this.moveDirection = moveDirection;
    }

    /* ---------------------------- Observer methods ---------------------------- */
    /**
     * Add observer to observe Point's state.
     * 
     * @param observer the observer to be added
     * @author HhT
     */
    public void addPointObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove observer from observers list.
     * 
     * @param observer the observer to be removed
     * @author HhT
     */
    public void removePointObserver(GameObserver observer) {
        observers.remove(observer);
    }

    /*
     * The above method is written to follow standard Observer pattern methods
     * implementation. Currently in this program only the below is being used.
     */

    /**
     * Remove all observers from the list.
     * 
     * @author HhT
     */
    public void removeAllPointObserver() {
        observers.clear();
    }

    /**
     * Notify observers when there is a change in moving direction.
     * 
     * @author HhT
     */
    private void notifyDirectionChange() {
        for (GameObserver observer : observers) {
            observer.onDirectionChange(getYPos(), getXPos(), getPieceName(), getDirection());
        }
    }
    /* -------------------------------------------------------------------------- */

    /* ---------------------------- Movement methods ---------------------------- */
    /**
     * Implementation of the move calculation abstract method.
     * 
     * @return the list of available moves
     * @author HhT
     * @author Lim KZ
     */
    @Override
    public List<Move> getAvailableMoves() {
        moveList.clear(); // Clear the list first before calculating moves
        int initialY = getYPos(); // The initial y position of the piece
        int initialX = getXPos(); // The initial x position of the piece

        /*
         * Set the direction multiplier. For example if Point is moving south, the
         * multiplier will be -1, then movement calculation will add -1 and -2 to y
         * position of Point.
         */
        int directionMultiplier = (moveDirection.equals("NORTH")) ? -1 : 1;

        for (int i = 1; i <= 2; i++) { // Loop only twice since Point only moves two steps at most
            int newY = initialY + (i * directionMultiplier); // Add the movement offset

            // Check if the new position is within the board bounds
            if (newY >= 0 && newY < Board.getBoardRow()) {
                Piece potentialPiece = pieceBoard.getPiece(newY, initialX);
                // If the cell is empty, add the move
                if (potentialPiece == null) {
                    moveList.add(new Move(newY, initialX));
                } else {
                    // If the cell contains an opponent's piece, add the move
                    if (!potentialPiece.getColor().equals(this.getColor())) {
                        moveList.add(new Move(newY, initialX));
                    }
                    // Stop checking for further moves as Point cannot skip over pieces
                    break;
                }
            }
        }

        return moveList;
    }

    /**
     * Get the moving direction.
     * 
     * @return the move direction
     * @author HhT
     */
    public String getDirection() {
        return moveDirection;
    }

    /**
     * Update moving direction after piece reaches either side of board.
     * 
     * @author HhT
     */
    public void setDirection() {
        // Check if the piece is at the north border and facing NORTH
        if (getYPos() == 0 && "NORTH".equals(moveDirection)) {
            moveDirection = "SOUTH";
            notifyDirectionChange();
        }
        // Check if the piece is at the south border and facing SOUTH
        else if (getYPos() == Board.getBoardRow() - 1 && "SOUTH".equals(moveDirection)) {
            moveDirection = "NORTH";
            notifyDirectionChange();
        }
    }
    /* ----------------------------------- End ---------------------------------- */
}
