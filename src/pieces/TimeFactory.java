/**
 * Concrete implementation of the PieceFactory interface to create instances of Time.
 */
package pieces;

import model.PlayerColor;
import model.Board;

public class TimeFactory implements PieceFactory {
    @Override
    public Piece createPiece(String id, int yPos, int xPos, PlayerColor color, Board gameBoard, String direction) {
        // The direction parameter is not used for Time pieces
        return new Time(id, yPos, xPos, color, gameBoard);
    }
}