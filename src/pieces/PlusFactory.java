/**
 * Concrete implementation of the PieceFactory interface to create instances of Plus.
 */
package pieces;

import model.PlayerColor;
import model.Board;

public class PlusFactory implements PieceFactory {
    @Override
    public Piece createPiece(String id, int yPos, int xPos, PlayerColor color, Board gameBoard, String direction) {
        // The direction parameter is not used for Plus pieces
        return new Plus(id, yPos, xPos, color, gameBoard);
    }
}