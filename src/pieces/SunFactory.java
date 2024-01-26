/**
 * Concrete implementation of the PieceFactory interface to create instances of Sun.
 */
package pieces;

import model.PlayerColor;
import model.Board;

public class SunFactory implements PieceFactory {
    @Override
    public Piece createPiece(String id, int yPos, int xPos, PlayerColor color, Board gameBoard, String direction) {
        // The direction parameter is not used for Sun pieces
        return new Sun(id, yPos, xPos, color, gameBoard);
    }
}