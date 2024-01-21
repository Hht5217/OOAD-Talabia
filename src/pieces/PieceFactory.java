package pieces;

import model.*;

public class PieceFactory {
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
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }

    // Overloaded version, for Point piece creation if direction is passed
    public Piece createPiece(String type, String id, int yPos, int xPos, PlayerColor color, String direction,
            Board gameBoard) {
        if (!"Point".equals(type)) {
            throw new IllegalArgumentException("Direction should only be provided for Point pieces");
        }
        return new Point(id, yPos, xPos, color, direction, gameBoard);
    }
}
