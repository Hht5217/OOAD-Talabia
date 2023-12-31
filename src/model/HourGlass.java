package src.model;

import java.util.List;

public class HourGlass extends Piece {
    public HourGlass(String pieceName, int yPos, int xPos, Color color, Board pieceBoard) {
        super(pieceName, yPos, xPos, color, pieceBoard);
    }

    // public boolean Movement(int yPos, int xPos) {
    // if (availableMove(yPos, xPos)) {
    // return hourGlassMovement(yPos, xPos);
    // }
    // return false;
    // }

    // private boolean hourGlassMovement(int yPos, int xPos) {
    // int deltaY = Math.abs(this.getYPos() - yPos);
    // int deltaX = Math.abs(this.getXPos() - xPos);
    // return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    // }

    // Check if piece is allowed to move, add if allowed and do nothing if null
    @Override
    // public void availableMoves() {
    public List<Move> getAvailableMoves() {
        // move logic
        return movePos;
    }
}
