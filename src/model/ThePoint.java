package src.model;

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
        int moveY = getYPos();
        int moveX = getXPos();

        if (getColor() == Color.YELLOW) {
            Move move1 = new Move(moveY - 1, moveX);
            Move move2 = new Move(moveY - 2, moveX);
            movePos.add(move1);
            movePos.add(move2);
        } else if (getColor() == Color.BLUE) {
            Move move1 = new Move(moveY + 1, moveX);
            Move move2 = new Move(moveY + 2, moveX);
            movePos.add(move1);
            movePos.add(move2);
        }
        // add if close to and reached border, reverse direction
        return movePos;
    }
}
