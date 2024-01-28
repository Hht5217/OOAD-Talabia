/**
 * The Plus class extends the Piece class and represents the Plus
 * piece in the game.
 */
package pieces;

import java.util.List;
import model.*;

public class Plus extends Piece {
    /**
     * Constructor of Plus piece. Values are inherited from Piece class.
     * 
     * @author HhT
     * @author Lim KZ
     */
    public Plus(String id, int yPos, int xPos, PlayerColor color, Board pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
    }

    /**
     * Implementation of the move calculation abstract method. Movement calculation
     * of Plus is similar to Time except for the movement direction offsets.
     * 
     * @return the list of available moves
     * @author HhT
     * @author Lam ZF
     */
    @Override
    public List<Move> getAvailableMoves() {
        moveList.clear(); // Clear the list first before calculating moves
        int currentY = getYPos(); // The current y position of the piece
        int currentX = getXPos(); // The current x position of the piece

        // Check moves in the vertical and horizontal directions
        int[][] directions = {
                { 1, 0 }, // Down
                { -1, 0 }, // Up
                { 0, 1 }, // Right
                { 0, -1 } // Left
        };

        for (int[] direction : directions) {
            // Initialize the positions first
            int potentialY = currentY;
            int potentialX = currentX;

            while (true) { // Add moves into list until loop breaks
                // Add offsets to positions to get potential move positions
                potentialY += direction[0];
                potentialX += direction[1];

                // Check if the move is within the bounds of the board
                if (!pieceBoard.inBoard(potentialY, potentialX)) {
                    break; // Break out of the loop if move is off the board
                }

                // Check if the cell is empty
                if (pieceBoard.isEmptySpace(potentialY, potentialX)) {
                    moveList.add(new Move(potentialY, potentialX));
                } else { // If the cell is not empty,
                    // Check if it contains an opponent's piece
                    if (!pieceBoard.getPiece(potentialY, potentialX).getColor().equals(this.getColor())) {
                        moveList.add(new Move(potentialY, potentialX));
                    }
                    break; // Break out of the loop if encountered any piece to stop adding moves
                }
            }
        }

        return moveList;
    }

    /**
     * Transformation of Plus piece into Time piece.
     * 
     * @author HhT
     */
    @Override
    public Piece transform() {
        // Pass the same properties
        return new Time(id, yPos, xPos, color, pieceBoard);
    }

}
