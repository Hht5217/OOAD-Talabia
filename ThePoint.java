public class ThePoint extends Piece {
    // public ThePoint(String pieceName, int color) {
    // super(pieceName, color);
    // }

    public ThePoint(String pieceName, int xPos, int yPos, int color) {
        super(pieceName, xPos, yPos, color);
    }

    public boolean availableMove() {
        // Check if piece is allowed to move
        return false;
    }
}
