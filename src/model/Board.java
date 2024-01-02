package model;

public class Board {
    private Piece[][] pieces;

    public Board() {
        this.pieces = new Piece[6][7];
    }

    // Check if specific location is empty
    public boolean isEmptySpace(int yPos, int xPos) {
        if (pieces[yPos][xPos] == null) {
            return true;
        }
        return false;
    }

    // Add chess piece to board
    public void addPiece(Piece pieceToAdd) {
        int addY = pieceToAdd.getYPos();
        int addX = pieceToAdd.getXPos();
        pieces[addY][addX] = pieceToAdd;
    }

    // Move piece to new position
    public void movePiece(Piece piece, Move movePos) {
        // Get old position of piece
        int oldYPos = piece.getYPos();
        int oldXPos = piece.getXPos();
        int newYPos = movePos.getMoveRow();
        int newXPos = movePos.getMoveColumn();

        pieces[newYPos][newXPos] = piece; // Place the piece at the new position
        piece.setPos(newYPos, newXPos); // Update the value of y and x of piece
        pieces[oldYPos][oldXPos] = null; // Remove piece from old position
    }

    // Check if the piece is within the board
    public boolean inBoard(int yPos, int xPos) {
        return xPos >= 0 && xPos < getX() && yPos >= 0 && yPos < getY();
    }

    // Check status of specific location on board
    public Piece pieceLocation(int yPos, int xPos) {
        if (inBoard(yPos, xPos)) {
            return getPiece(yPos, xPos);
        }
        return null;
    }

    // Get length X (row) of board
    public int getX() {
        return pieces[0].length;
    }

    // Get length Y (column) of board
    public int getY() {
        return pieces.length;
    }

    // Return piece at specific location
    public Piece getPiece(int yPos, int xPos) {
        return pieces[yPos][xPos];
    }

    // Return piece array
    public Piece[][] getBoard() {
        return pieces;
    }

    // Test purpose, might use to save game later
    public void printBoard() {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (isEmptySpace(i, j)) {
                    System.out.print(String.format("%10s", "null"));
                } else {
                    System.out.print(String.format("%10s", pieces[i][j].toString()));
                }
                System.out.print("|");
            }
            System.out.println("\n" + "-".repeat(80));
        }
    }
}
