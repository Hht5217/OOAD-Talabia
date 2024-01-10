package model;

import java.util.List;

public class HourGlass extends Piece {
    public HourGlass(String pieceName, int yPos, int xPos, Color color, Board pieceBoard) {
        super(pieceName, yPos, xPos, color, pieceBoard);
    }

    // Check if piece is allowed to move, add if allowed and do nothing if not
    // available move
    @Override
    // public void availableMoves() {
    public List<Move> getAvailableMoves() {
        movePos.clear();
        int currentY = getYPos();
        int currentX = getXPos();

        // Define all possible L-shaped moves for the Hourglass piece
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
