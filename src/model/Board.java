package model;

import controller.GameObserver;
import pieces.*;

public class Board {
    private Piece[][] pieces;
    private static final int BOARD_ROW = 6;
    private static final int BOARD_COL = 7;

    public Board() {
        this.pieces = new Piece[BOARD_ROW][BOARD_COL];
    }

    /* Add observers to Points piece */
    public void boardAddPointsObserver(GameObserver observer) {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                Piece piece = pieces[r][c];
                if (piece instanceof Point) {
                    Point point = (Point) piece;
                    point.addPointObserver(observer);
                }
            }
        }
    }

    /* Remove observers from Points piece */
    public void boardRemovePointsObserver(GameObserver observer) {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                Piece piece = pieces[r][c];
                if (piece instanceof Point) {
                    Point point = (Point) piece;
                    point.removePointObserver(observer);
                }
            }
        }
    }

    /* Add chess piece to board */
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
     * @author HhT, LKZ
     */
    public boolean setPiece(Piece piece, Move move) {
        int oldYPos = piece.getYPos();
        int oldXPos = piece.getXPos();
        int newYPos = move.getMoveRow();
        int newXPos = move.getMoveColumn();

        if (!isEmptySpace(newYPos, newXPos) && pieces[newYPos][newXPos].getType().equals("Sun")) {
            return true; // return true if position is a piece and it is Sun
        }

        pieces[newYPos][newXPos] = piece; // Place the piece at the new position
        piece.setPos(newYPos, newXPos); // Update the value of y and x of piece
        pieces[oldYPos][oldXPos] = null; // Remove piece from old position
        return false;
    }

    /*
     * Clear the board before setting board from loaded game. If the piece is a
     * Point, remove all the observers
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
     * Pieces transformation. From iterating the pieces array, if a piece type is
     * either Plus or Time, then it will transform, and the newly transformed piece
     * will be set to the board.
     */
    public void transformPieces() {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                if (!isEmptySpace(r, c)) {
                    Piece original = pieces[r][c];
                    if (original.getType().equals("Plus") || original.getType().equals("Time")) {
                        Piece toTransform = original.transform();
                        pieces[r][c] = toTransform;
                    }
                }
            }
        }
    }

    // Check if specific location is empty
    public boolean isEmptySpace(int yPos, int xPos) {
        if (pieces[yPos][xPos] == null) {
            return true;
        }
        return false;
    }

    // Check if the piece is within the board range
    public boolean inBoard(int yPos, int xPos) {
        return xPos >= 0 && xPos < getBoardColumn() && yPos >= 0 && yPos < getBoardRow();
    }

    // Get length Y (column) of board
    public int getBoardRow() {
        return BOARD_ROW;
    }

    // Get length X (row) of board
    public int getBoardColumn() {
        return BOARD_COL;
    }

    // Return piece at specific location
    public Piece getPiece(int yPos, int xPos) {
        return pieces[yPos][xPos];
    }

    // Return the whole 2D array that store pieces
    public Piece[][] getAllPieces() {
        return pieces;
    }
    /* -------------------------------------------------------------------------- */

    /* ------------------------------- Test method ------------------------------ */
    /*
     * Test purpose, to check if pieces are moving in correct direction or are
     * located at correct positions
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
