package model;

import java.util.List;

public class Sun extends Piece {
    public Sun(String pieceName, int yPos, int xPos, Color color, Board pieceBoard) {
        super(pieceName, yPos, xPos, color, pieceBoard);
    }

    // Check if piece is allowed to move, add if allowed and do nothing if null
    @Override
    // public void availableMoves() {
    public List<Move> getAvailableMoves() {
        movePos.clear();
        int currentY = getYPos();
        int currentX = getXPos();

        // Explore all directions in a single loop with checks for out-of-bounds and
        // occupied spaces
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Skip the iteration where i and j are both 0, as this is the current position
                if (i == 0 && j == 0) {
                    continue;
                }

                int potentialY = currentY + i;
                int potentialX = currentX + j;

                // Check if the potential move is within the board
                if (pieceBoard.inBoard(potentialY, potentialX)) {
                    // Check if the space is empty or occupied by an opponent's piece
                    if (pieceBoard.isEmptySpace(potentialY, potentialX) ||
                            !pieceBoard.getPiece(potentialY, potentialX).getColor().equals(this.getColor())) {
                        movePos.add(new Move(potentialY, potentialX));
                    }
                }
            }
        }
        return movePos;
    }
}
