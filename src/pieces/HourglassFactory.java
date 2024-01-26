/**
 * Concrete implementation of the PieceFactory interface to create instances of Hourglass.
 */
package pieces;

import model.PlayerColor;
import model.Board;

public class HourglassFactory implements PieceFactory {
    @Override
    public Piece createPiece(String id, int yPos, int xPos, PlayerColor color, Board gameBoard, String direction) {
        // The direction parameter is not used for Hourglass pieces
        return new Hourglass(id, yPos, xPos, color, gameBoard);
    }
}