public class Board {
    private Piece[][] talabiaChessBoard;

    public Board() {
        talabiaChessBoard = new Piece[6][7];
    }

    public boolean isEmptySpace(int xPos, int yPos) {
        if (talabiaChessBoard[xPos][yPos] == null) {
            return true;
        }
        return false;
    }

    public int getX() {
        return talabiaChessBoard[0].length;
    }

    public int getY() {
        return talabiaChessBoard.length;
    }
}