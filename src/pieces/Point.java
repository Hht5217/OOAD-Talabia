package pieces;

import java.util.*;

import controller.GameObserver;
import model.*;

public class Point extends Piece {
    private String moveDirection = ""; // Initialize to avoid errors
    private List<GameObserver> observers = new ArrayList<>();

    public Point(String id, int yPos, int xPos, PlayerColor color, String moveDirection, Board pieceBoard) {
        super(id, yPos, xPos, color, pieceBoard);
        this.moveDirection = moveDirection;
    }

    /* Add observer to observe Point's state */
    public void addPointObserver(GameObserver observer) {
        observers.add(observer);
    }

    /* Remove observer from observers list */
    public void removePointObserver(GameObserver observer) {
        observers.remove(observer);
    }

    /* Notify observer when point changes */
    private void notifyDirectionChange() {
        for (GameObserver observer : observers) {
            observer.onDirectionChange(getYPos(), getXPos(), toString(), getDirection());
        }
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
            notifyDirectionChange();
        }
        // Check if the piece is at the south border and facing SOUTH
        else if (getYPos() == pieceBoard.getBoardRow() - 1 && "SOUTH".equals(moveDirection)) {
            moveDirection = "NORTH";
            notifyDirectionChange();
        }
    }

    /* Return the moving direction */
    public String getDirection() {
        return moveDirection;
    }
}
