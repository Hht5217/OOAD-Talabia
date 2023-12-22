import java.util.ArrayList;

public class Model {
    private ArrayList<Piece> player1Pieces;
    private ArrayList<Piece> player2Pieces;
    private Board talabiaChessBoard;

    public Model() {
        talabiaChessBoard = new Board();
        player1Pieces = new ArrayList<Piece>();
        player2Pieces = new ArrayList<Piece>();
        addYPoint();
        addBPoint();
        addYPlus();
        addBPlus();
        addYHourglass();
        addBHourglass();
        addYTime();
        addBTime();
        addYSun();
        addBSun();
    }

    // number of piece
    public void addYPoint() {
        for (int i = 0; i < 7; i++) {
            int row = 4;
            Piece points = new ThePoint("yPoint", row, i, 2, talabiaChessBoard);
            talabiaChessBoard.addPiece(points);
            player2Pieces.add(points);
        }
    }

    public void addBPoint() {
        for (int i = 0; i < 7; i++) {
            int row = 1;
            Piece points = new ThePoint("bPoint", row, i, 1, talabiaChessBoard);
            talabiaChessBoard.addPiece(points);
            player1Pieces.add(points);
        }
    }

    public void addYPlus() {
        int row = 5;
        int col1 = 0;
        int col2 = 6;
        Piece points = new Plus("yPlus", row, col1, 2, talabiaChessBoard);
        Piece points2 = new Plus("yPlus", row, col2, 2, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBPlus() {
        int row = 0;
        int col1 = 0;
        int col2 = 6;
        Piece points = new Plus("bPlus", row, col1, 1, talabiaChessBoard);
        Piece points2 = new Plus("bPlus", row, col2, 1, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYHourglass() {
        int row = 5;
        int col1 = 1;
        int col2 = 5;
        Piece points = new HourGlass("yHourglass", row, col1, 2, talabiaChessBoard);
        Piece points2 = new HourGlass("yHourglass", row, col2, 2, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBHourglass() {
        int row = 0;
        int col1 = 1;
        int col2 = 5;
        Piece points = new HourGlass("bHourglass", row, col1, 1, talabiaChessBoard);
        Piece points2 = new HourGlass("bHourglass", row, col2, 1, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYTime() {
        int row = 5;
        int col1 = 2;
        int col2 = 4;
        Piece points = new Time("yTime", row, col1, 2, talabiaChessBoard);
        Piece points2 = new Time("yTime", row, col2, 2, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBTime() {
        int row = 0;
        int col1 = 2;
        int col2 = 4;
        Piece points = new Time("bTime", row, col1, 1, talabiaChessBoard);
        Piece points2 = new Time("bTime", row, col2, 1, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYSun() {
        int row = 5;
        int col = 3;
        Piece points = new Sun("ySun", row, col, 2, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        player2Pieces.add(points);
    }

    public void addBSun() {
        int row = 0;
        int col = 3;
        Piece points = new Sun("bSun", row, col, 1, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        player1Pieces.add(points);
    }

    public Board getTalabiaChessBoard() {
        return talabiaChessBoard;
    }
}
