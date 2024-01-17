package pieces;

import java.util.List;
import model.*;

public class HourGlass extends Piece {
    public HourGlass(String id, int yPos, int xPos, PlayerColor color, BoardCallback pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
    }

    /*
     * Check if piece is allowed to move, add if allowed and do nothing if not an
     * available move
     */
    @Override
    // public void availableMoves() {
    public List<Move> getAvailableMoves() {
        movePos.clear();
        int currentY = getYPos();
        int currentX = getXPos();

        /*
         * Define all possible L-shaped moves for the Hourglass piece in pair of y and x
         * positions, then add them to the current position of the piece.
         */
        int[][] offsets = {
                { -2, -1 }, { -2, 1 },
                { -1, -2 }, { -1, 2 },
                { 1, -2 }, { 1, 2 },
                { 2, -1 }, { 2, 1 }
        };

        for (int[] offset : offsets) {
            int potentialY = currentY + offset[0];
            int potentialX = currentX + offset[1];

            // Check if the move is within the bounds of the board
            if (pieceBoard.inBoard(potentialY, potentialX)) {
                // Check if the square is either empty or contains an opponent's piece
                if (pieceBoard.isEmptySpace(potentialY, potentialX) ||
                        !pieceBoard.getPiece(potentialY, potentialX).getColor().equals(this.getColor())) {
                    movePos.add(new Move(potentialY, potentialX));
                }
            }
        }

        return movePos;
    }

}
