/**
 * The Sun class extends the Piece class and represents the Sun
 * piece in the game.
 */
package pieces;

import java.util.List;
import model.*;

public class Sun extends Piece {
    /**
     * Constructor of Sun piece. Values are inherited from Piece class.
     * 
     * @author HhT
     */
    public Sun(String id, int yPos, int xPos, PlayerColor color, Board pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
    }

    /**
     * Implementation of the move calculation abstract method.
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

        /*
         * Explore all directions in a single loop with checks for out-of-bounds and
         * occupied spaces
         */
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Skip the iteration where i and j are both 0, as this is the current position
                if (i == 0 && j == 0) {
                    continue;
                }

                // Add the movement offsets to get potential move positions
                int potentialY = currentY + i;
                int potentialX = currentX + j;

                // Check if the potential move is within the board
                if (pieceBoard.inBoard(potentialY, potentialX)) {
                    // Check if the space is empty or occupied by an opponent's piece
                    if (pieceBoard.isEmptySpace(potentialY, potentialX) ||
                            !pieceBoard.getPiece(potentialY, potentialX).getColor().equals(this.getColor())) {
                        moveList.add(new Move(potentialY, potentialX));
                    }
                }
            }
        }
        return moveList;
    }
}
