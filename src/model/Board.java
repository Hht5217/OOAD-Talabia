package model;

public class Board implements BoardCallback {
    private Piece[][] pieces;
    private static final int BOARD_ROW = 6;
    private static final int BOARD_COL = 7;

    public Board() {
        this.pieces = new Piece[BOARD_ROW][BOARD_COL];
    }

    // Check if specific location is empty (callback)
    @Override
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

    /**
     * Update chess pieces on board, and also check if captured piece is Sun
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

    // transform
    public void transformPieces() {
        int transformCount = 0; // test
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                if (!isEmptySpace(r, c)) {
                    Piece original = pieces[r][c];
                    if (original.getType().equals("Plus") || original.getType().equals("Time")) {
                        // System.out.println(original.getType()); // test
                        Piece toTransform = original.transform();
                        pieces[r][c] = toTransform;
                        System.out.println(toTransform.getType() + "|" + toTransform.toString()); // test
                        transformCount++; // test
                    }
                }
            }
        }
        System.out.println("Transformed: " + transformCount); // test
    }

    // Check if the piece is within the board range (callback)
    @Override
    public boolean inBoard(int yPos, int xPos) {
        return xPos >= 0 && xPos < getBoardColumn() && yPos >= 0 && yPos < getBoardRow();
    }

    // Get length Y (column) of board
    @Override // testing to see if can solve circular reference
    public int getBoardRow() {
        return BOARD_ROW;
    }

    // Get length X (row) of board
    @Override // testing to see if can solve circular reference
    public int getBoardColumn() {
        return BOARD_COL;
    }

    // Return piece at specific location (callback)
    @Override
    public Piece getPiece(int yPos, int xPos) {
        return pieces[yPos][xPos];
    }

    // Return piece array
    public Piece[][] getBoard() {
        return pieces;
    }

    // Test purpose, might use to save game later
    public void printBoard() {
        for (int r = 0; r < BOARD_ROW; r++) {
            for (int c = 0; c < BOARD_COL; c++) {
                if (isEmptySpace(r, c)) {
                    // System.out.print(String.format("%12s", "null"));
                    System.out.print(" ");
                } else {
                    // System.out.print(String.format("%12s", pieces[i][j].toString()));
                    if (pieces[r][c].getColor() == Color.YELLOW) {
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
}
