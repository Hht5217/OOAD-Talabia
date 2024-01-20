package pieces;

import java.util.List;
import model.*;

public class Plus extends Piece {
    public Plus(String id, int yPos, int xPos, PlayerColor color, Board pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
    }

    // Check if piece is allowed to move, add if allowed and do nothing if not
    // available moves
    @Override
    // public void availableMoves() {
    public List<Move> getAvailableMoves() {
        movePos.clear();
        int currentY = getYPos();
        int currentX = getXPos();

        // Check moves in the vertical and horizontal directions
        int[][] directions = {
                { 1, 0 }, // Down
                { -1, 0 }, // Up
                { 0, 1 }, // Right
                { 0, -1 } // Left
        };

        for (int[] direction : directions) {
            int potentialY = currentY;
            int potentialX = currentX;

            while (true) {
                potentialY += direction[0];
                potentialX += direction[1];

                // Check if the move is within the bounds of the board
                if (!pieceBoard.inBoard(potentialY, potentialX)) {
                    break; // Break out of the loop if we're off the board
                }

                // Check if the square is empty
                if (pieceBoard.isEmptySpace(potentialY, potentialX)) {
                    movePos.add(new Move(potentialY, potentialX));
                } else {
                    // Check if the square contains an opponent's piece
                    if (!pieceBoard.getPiece(potentialY, potentialX).getColor().equals(this.getColor())) {
                        movePos.add(new Move(potentialY, potentialX));
                    }
                    break; // Break out of the loop if we've encountered any piece
                }
            }
        }

        return movePos;
    }

    // Transform into Time
    @Override
    public Piece transform() {
        return new Time(id, yPos, xPos, color, pieceBoard);
    }

}
