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
        return movePos;
    }
}
