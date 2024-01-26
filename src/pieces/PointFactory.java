/**
 * Concrete implementation of the PieceFactory interface to create instances of Point.
 */
package pieces;

import model.PlayerColor;
import model.Board;

public class PointFactory implements PieceFactory {
    @Override
    public Piece createPiece(String id, int yPos, int xPos, PlayerColor color, Board gameBoard, String direction) {
        // Only the Point pieces use the direction parameter
        return new Point(id, yPos, xPos, color, direction, gameBoard);
    }
}