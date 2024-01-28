/**
 * The Hourglass class extends the Piece class and represents the Hourglass
 * piece in the game.
 */
package pieces;

import java.util.List;
import model.*;

public class Hourglass extends Piece {
    /**
     * Constructor of Hourglass piece. Values are inherited from Piece class.
     * 
     * @author HhT
     */
    public Hourglass(String id, int yPos, int xPos, PlayerColor color, Board pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
    }

    /**
     * Implementation of the move calculation abstract method.
     * 
     * @return the list of available moves
     * @author HhT
     * @author Chai DS
     * @author Lim KZ
     * @author Lam ZF
     */
    @Override
    public List<Move> getAvailableMoves() {
        moveList.clear(); // Clear the list first before calculating moves
        int currentY = getYPos(); // The current y position of the piece
        int currentX = getXPos(); // The current x position of the piece

        /*
         * Define all possible L-shaped moves for the Hourglass piece in pair of y and x
         * positions, then add them to the current position of the piece. Possible
         * movement of Hourglass piece is moving 1 or 2 steps, both horizontally and
         * vertically.
         */
        int[][] offsets = {
                { -2, -1 }, { -2, 1 },
                { -1, -2 }, { -1, 2 },
                { 1, -2 }, { 1, 2 },
                { 2, -1 }, { 2, 1 }
        };

        /*
         * Iterate the offsets and add them to the current position of piece to get the
         * potential move position
         */
        for (int[] offset : offsets) {
            int potentialY = currentY + offset[0];
            int potentialX = currentX + offset[1];

            // Check if the move is within the bounds of the board
            if (pieceBoard.inBoard(potentialY, potentialX)) {
                // Check if the cell is either empty or contains an opponent's piece
                if (pieceBoard.isEmptySpace(potentialY, potentialX) ||
                        !pieceBoard.getPiece(potentialY, potentialX).getColor().equals(this.getColor())) {
                    moveList.add(new Move(potentialY, potentialX)); // Add the move to moves list
                }
            }
        }

        return moveList;
    }
}
