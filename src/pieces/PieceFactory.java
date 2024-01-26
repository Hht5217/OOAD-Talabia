/**
 * The PieceFactory interface defines a method for creating Piece objects
 * with specific attributes such as position, color, and direction, allowing for
 * polymorphic creation of game pieces in a board game. Uses the Factory pattern.
 */
package src.pieces;

import model.PlayerColor;
import model.Board;

public interface PieceFactory {
    /**
     * Constructor for piece creation.
     * 
     * @param type      the type of piece to be created
     * @param id        the assigned id
     * @param yPos      the y position
     * @param xPos      the x position
     * @param color     the color of the piece
     * @param gameBoard the board the piece is on
     * @return the created piece
     * @author HhTs
     */
    public Piece createPiece(String type, String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        switch (type) {
            case "Plus":
                return new Plus(id, yPos, xPos, color, gameBoard);
            case "Hourglass":
                return new Hourglass(id, yPos, xPos, color, gameBoard);
            case "Time":
                return new Time(id, yPos, xPos, color, gameBoard);
            case "Sun":
                return new Sun(id, yPos, xPos, color, gameBoard);
            default: // Throw exception if type given is not a valid type
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }

    /**
     * Overloaded constructor, for Point piece creation if direction is passed.
     * 
     * @param direction the moving direction of point pieces
     * @return Point piece
     * @author HhT
     */
    Piece createPiece(String id, int yPos, int xPos, PlayerColor color, Board gameBoard, String direction);
}
