/**
 * The Board class represents the game board of the game. It maintains a 2D
 * array of Piece objects, representing the state of the game board. The class
 * provides methods for manipulating the game board, such as adding a piece,
 * moving a piece, and checking if a space is empty. It also provides methods
 * for accessing information about the game board, such as getting a piece at a
 * specific location and getting the dimensions of the board.
 */
package src.model;

import controller.GameObserver;
import pieces.*;

public class Board {
    private Piece[][] pieces; // The 2D array of chess pieces
    private static final int BOARD_ROW = 6; // Number of board row constant
    private static final int BOARD_COL = 7; // Number of board column constant

    /**
     * Construcotr of Board that initialize the 2D pieces array.
     * 
     * @author HhT
     */
    public Board() {
        this.pieces = new Piece[BOARD_ROW][BOARD_COL];
    }

    /* ----------------- Board state management and queries ---------------- */
    /**
     * Static method to return the board row constant.
     * 
     * @return the board row constant
     * @author HhT
     */
    public static int getBoardRow() {
        return BOARD_ROW;
    }

    /**
     * Static method to return the board column constant.
     * 
     * @return the board column constant
     * @author HhT
     */
    public static int getBardColumn() {
        return BOARD_COL;
    }

    /**
     * Get the whole 2D array (board) that contains the pieces.
     * 
     * @return the 2D array
     * @author HhT
     */
    public Piece[][] getAllPieces() {
        return pieces;
    }

    /**
     * Clear the board before setting board from loaded game. If the piece is a
     * Point, remove all the observers.
     * 
     * @author HhT
     */
    public void clearBoard() {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                if (pieces[r][c] instanceof Point) {
                    ((Point) pieces[r][c]).removeAllPointObserver();
                }
                pieces[r][c] = null;
            }
        }
    }

    /**
     * Check if the specific location on the board is empty.
     * 
     * @param yPos the y position to check
     * @param xPos the x position to check
     * @return true if the position is empty / no piece
     * @return false if position is occupied
     * @author HhT
     */
    public boolean isEmptySpace(int yPos, int xPos) {
        if (pieces[yPos][xPos] == null) {
            return true;
        }
        return false;
    }

    /**
     * Check if the piece is within the board range.
     * 
     * @param yPos y position of the piece
     * @param xPos x position of the piece
     * @return true if the piece is in the board
     * @author HhT
     * @author Chai DS
     */
    public boolean inBoard(int yPos, int xPos) {
        return xPos >= 0 && xPos < BOARD_COL && yPos >= 0 && yPos < BOARD_ROW;
    }
    /* -------------------------------------------------------------------------- */

    /* ---------------------------- Pieces management --------------------------- */
    /**
     * Get the piece at a specific location on the board.
     * 
     * @param yPos the y position
     * @param xPos the x position
     * @author HhT
     */
    public Piece getPiece(int yPos, int xPos) {
        return pieces[yPos][xPos];
    }

    /**
     * Add pieces to board
     * 
     * @param pieceToAdd the piece to be added to board
     * @author HhT
     */
    public void addPiece(Piece pieceToAdd) {
        int addY = pieceToAdd.getYPos();
        int addX = pieceToAdd.getXPos();
        pieces[addY][addX] = pieceToAdd;
    }

    /**
     * Update chess pieces on board, and also check if captured piece is Sun
     * 
     * @param piece the piece that is moving
     * @param move  the location where the piece is moving to
     * @author HhT
     * @author Lim KZ
     */
    public boolean setPiece(Piece piece, Move move) {
        // The initial position before moving
        int oldYPos = piece.getYPos();
        int oldXPos = piece.getXPos();

        // The position to move to
        int newYPos = move.getMoveRow();
        int newXPos = move.getMoveColumn();

        if (!isEmptySpace(newYPos, newXPos) && pieces[newYPos][newXPos].getType().equals("Sun")) {
            return true; // return true if position is a piece and it is Sun
        }

        pieces[newYPos][newXPos] = piece; // Place the piece at the new position
        piece.setPosition(newYPos, newXPos); // Update the value of y and x of piece
        pieces[oldYPos][oldXPos] = null; // Remove piece from old position

        return false; // return false at the end even to stop Point update direction
    }

    /**
     * Pieces transformation. From iterating the pieces array, if a piece type is
     * either Plus or Time, then it will transform, and the newly transformed piece
     * will be set to the board.
     * 
     * @author HhT
     */
    public void transformPieces() {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                if (!isEmptySpace(r, c)) { // If there is a piece
                    Piece original = pieces[r][c];
                    // If the piece is either Plus or Time pieces
                    if (original.getType().equals("Plus") || original.getType().equals("Time")) {
                        Piece toTransform = original.transform(); // Transform the pieces
                        pieces[r][c] = toTransform; // Set the pieces to board
                    }
                }
            }
        }
    }
    /* -------------------------------------------------------------------------- */

    /* ----------------------------- Observer method ---------------------------- */
    /**
     * Add observers to Point pieces.
     * 
     * @param observer the observer to add
     * @author HhT
     */
    public void boardAddPointsObserver(GameObserver observer) {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                Piece piece = pieces[r][c];
                if (piece instanceof Point) { // Add if point piece, do nothing if not
                    Point point = (Point) piece;
                    point.addPointObserver(observer);
                }
            }
        }
    }

    /**
     * Remove observers from Point pieces. Currently not used but implemeneted
     * following standard Observer pattern implementation.
     * 
     * @author HhT
     */
    public void boardRemovePointsObserver(GameObserver observer) {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                Piece piece = pieces[r][c];
                if (piece instanceof Point) {// Remove if point piece, do nothing if not
                    Point point = (Point) piece;
                    point.removePointObserver(observer);
                }
            }
        }
    }
    /* -------------------------------------------------------------------------- */

    /* ---------------------------------- Test ---------------------------------- */
    /**
     * Test purpose, to check if pieces are moving in correct direction or are
     * located at correct positions.
     * 
     * @author HhT
     */
    public void printBoard() {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                if (isEmptySpace(r, c)) {
                    System.out.print(" ");
                } else {
                    if (pieces[r][c].getColor() == PlayerColor.YELLOW) {
                        System.out.print("y");
                    } else {
                        System.out.print("b");
                    }
                }
                System.out.print("|");
            }
            System.out.println("\n" + "-".repeat(15));
        }
    }
    /* ----------------------------------- End ---------------------------------- */
}
