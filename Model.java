import java.util.ArrayList;

public class Model {
    private ArrayList<Piece> player1Pieces;
    private ArrayList<Piece> player2Pieces;
    private Board talabiaChessBoard;

    public Model() {
        talabiaChessBoard = new Board();
        player1Pieces = new ArrayList<Piece>();
        player2Pieces = new ArrayList<Piece>();
        addPoint();
    }

    public void addPoint() {
        for (int i = 0; i < 7; i++) {
            int row = 5;
            Piece points = new ThePoint("yPoint", row, i, 1);
            talabiaChessBoard.addPiece(points);
            player1Pieces.add(points);
        }
    }

    public Board getTalabiaChessBoard() {
        return talabiaChessBoard;
    }
}
