package model;

import java.util.List;

public class ThePoint extends Piece {

    public ThePoint(String pieceName, int yPos, int xPos, Color color, Board pieceBoard) {
        super(pieceName, yPos, xPos, color, pieceBoard);
    }

    // Check if piece is allowed to move, add if allowed and do nothing if null
    @Override
    public List<Move> getAvailableMoves() {
        if (!movePos.isEmpty()) {
            movePos.clear();
        }
        int initialY = getYPos();
        int initialX = getXPos();

        if (this.getColor() == Color.YELLOW) {
            Move move1 = new Move(initialY - 1, initialX);
            Move move2 = new Move(initialY - 2, initialX);
            movePos.add(move1);
            movePos.add(move2);
        } else if (this.getColor() == Color.BLUE) {
            Move move1 = new Move(initialY + 1, initialX);
            Move move2 = new Move(initialY + 2, initialX);
            movePos.add(move1);
            movePos.add(move2);
        }
        // add if close to and reached border, reverse direction
        return movePos;
    }
}
