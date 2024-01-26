/**
 * The PieceFactory interface defines a method for creating Piece objects
 * with specific attributes such as position, color, and direction, allowing for
 * polymorphic creation of game pieces in a board game. Uses the Factory pattern.
 */
package pieces;

import model.PlayerColor;
import model.Board;

public interface PieceFactory {
    /**
     * Creates a new piece with the specified details.
     *
     * @param id        the unique identifier for the piece
     * @param yPos      the y position of the piece on the board
     * @param xPos      the x position of the piece on the board
     * @param color     the color of player of which the piece belongs to
     * @param gameBoard the board on which the piece will be placed
     * @param direction the moving direction of piece, if needed
     * @return a new Piece object
     * @author HhT
     */
    Piece createPiece(String id, int yPos, int xPos, PlayerColor color, Board gameBoard, String direction);
}
