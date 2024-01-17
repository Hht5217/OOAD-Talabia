package pieces;

import java.util.List;

import model.*;

public class Point extends Piece {
    private String moveDirection;

    public Point(String id, int yPos, int xPos, PlayerColor color, String moveDirection, BoardCallback pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
        this.moveDirection = moveDirection;
    }

    // Check if piece is allowed to move, add if allowed and do nothing if not
    // available moves
    @Override
    public List<Move> getAvailableMoves() {
        movePos.clear(); // Clear the list of moves

        int initialY = getYPos();
        int initialX = getXPos();
        int directionMultiplier = (moveDirection.equals("NORTH")) ? -1 : 1;

        for (int i = 1; i <= 2; i++) {
            int newY = initialY + (i * directionMultiplier);

            // Check if the new position is within the board bounds
            if (newY >= 0 && newY < pieceBoard.getBoardRow()) {
                Piece potentialPiece = pieceBoard.getPiece(newY, initialX);
                // If the space is empty, add the move
                if (potentialPiece == null) {
                    movePos.add(new Move(newY, initialX));
                } else {
                    // If the space contains an opponent's piece, add the move
                    if (!potentialPiece.getColor().equals(this.getColor())) {
                        movePos.add(new Move(newY, initialX));
                    }
                    // Stop checking for further moves as ThePoint cannot skip over pieces
                    break;
                }
            }
        }

        return movePos;
    }

    // To update thePoint's direction after a move has been made
    public void updateDirection() {
        // Check if the piece is at the north border and facing NORTH
        if (getYPos() == 0 && "NORTH".equals(moveDirection)) {
            moveDirection = "SOUTH";
        }
        // Check if the piece is at the south border and facing SOUTH
        else if (getYPos() == pieceBoard.getBoardRow() - 1 && "SOUTH".equals(moveDirection)) {
            moveDirection = "NORTH";
        }
    }
}
