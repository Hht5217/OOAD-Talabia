/**
 * The Time class extends the Piece class and represents the Time
 * piece in the game.
 */
package pieces;

import java.util.List;
import model.*;

public class Time extends Piece {
    /**
     * Constructor of Time piece. Values are inherited from Piece class.
     * 
     * @author HhT
     */
    public Time(String id, int yPos, int xPos, PlayerColor color, Board pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
    }

    /**
     * Implementation of the move calculation abstract method. Movement calculation
     * of Time is similar to Plus except for the movement direction offsets.
     * 
     * @return the list of available moves
     * @author HhT
     * @author Lim KZ
     */
    @Override
    public List<Move> getAvailableMoves() {
        moveList.clear(); // Clear the list first before calculating moves
        int currentY = getYPos(); // The current y position of the piece
        int currentX = getXPos(); // The current x position of the piece

        // Check moves in the four diagonal directions
        int[][] directions = {
                { 1, 1 }, // Down-Right
                { 1, -1 }, // Down-Left
                { -1, 1 }, // Up-Right
                { -1, -1 } // Up-Left
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
                } else { // If cell is not empty,
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
     * Transformation of Time piece into Plus piece.
     * 
     * @author HhT
     */
    @Override
    public Piece transform() {
        // Pass the same properties
        return new Plus(id, yPos, xPos, color, pieceBoard);
    }

}
