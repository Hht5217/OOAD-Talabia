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
        // move logic
        movePos.clear();
        int currentY = getYPos();
        int currentX = getXPos();

        // Explore all directions in a single loop with checks for out-of-bounds and
        // occupied spaces
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int potentialY = currentY + i;
                int potentialX = currentX + j;

                if (pieceBoard.inBoard(potentialY, potentialX) && pieceBoard.isEmptySpace(potentialY, potentialX)) {
                    movePos.add(new Move(potentialY, potentialX));
                }
            }
        }
        return movePos;
    }
}
