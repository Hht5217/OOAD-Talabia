package src.model;

import java.util.ArrayList;

// Model class
public class Game {
    private ArrayList<Piece> player1Pieces;
    private ArrayList<Piece> player2Pieces;
    private Board talabiaChessBoard;
    private int currentTurn = 1;
    private Color currentPlayer = Color.YELLOW;

    public Game() {
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

        // Test purpose
        talabiaChessBoard.printBoard();
    }

    // number of piece
    public void addYPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new ThePoint("yPoint", 4, i, Color.YELLOW, talabiaChessBoard);
            talabiaChessBoard.addPiece(points);
            player2Pieces.add(points);
        }
    }

    public void addBPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new ThePoint("bPoint", 1, i, Color.BLUE, talabiaChessBoard);
            talabiaChessBoard.addPiece(points);
            player1Pieces.add(points);
        }
    }

    public void addYPlus() {
        // int row = 5;
        // int col1 = 0;
        // int col2 = 6;
        Piece points = new Plus("yPlus", 5, 0, Color.YELLOW, talabiaChessBoard);
        Piece points2 = new Plus("yPlus", 5, 6, Color.YELLOW, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBPlus() {
        // int row = 0;
        // int col1 = 0;
        // int col2 = 6;
        Piece points = new Plus("bPlus", 0, 0, Color.BLUE, talabiaChessBoard);
        Piece points2 = new Plus("bPlus", 0, 6, Color.BLUE, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYHourglass() {
        // int row = 5;
        // int col1 = 1;
        // int col2 = 5;
        Piece points = new HourGlass("yHourglass", 5, 1, Color.YELLOW, talabiaChessBoard);
        Piece points2 = new HourGlass("yHourglass", 5, 5, Color.YELLOW, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBHourglass() {
        // int row = 0;
        // int col1 = 1;
        // int col2 = 5;
        Piece points = new HourGlass("bHourglass", 0, 1, Color.BLUE, talabiaChessBoard);
        Piece points2 = new HourGlass("bHourglass", 0, 5, Color.BLUE, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYTime() {
        // int row = 5;
        // int col1 = 2;
        // int col2 = 4;
        Piece points = new Time("yTime", 5, 2, Color.YELLOW, talabiaChessBoard);
        Piece points2 = new Time("yTime", 5, 4, Color.YELLOW, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBTime() {
        // int row = 0;
        // int col1 = 2;
        // int col2 = 4;
        Piece points = new Time("bTime", 0, 2, Color.BLUE, talabiaChessBoard);
        Piece points2 = new Time("bTime", 0, 4, Color.BLUE, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        talabiaChessBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYSun() {
        // int row = 5;
        // int col = 3;
        Piece points = new Sun("ySun", 5, 3, Color.YELLOW, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        player2Pieces.add(points);
    }

    public void addBSun() {
        // int row = 0;
        // int col = 3;
        Piece points = new Sun("bSun", 0, 3, Color.BLUE, talabiaChessBoard);
        talabiaChessBoard.addPiece(points);
        player1Pieces.add(points);
    }

    ///////////////////// Getter Setter////////////////////////////

    // Get number of current turn
    public int getTurn() {
        return currentTurn;
    }

    // Get current player
    public Color getPlayer() {
        return currentPlayer;
    }

    // Set current turn

    // Set current player
    public void setPlayer() {
        if (currentPlayer == Color.YELLOW) {
            currentPlayer = Color.BLUE;
        } else {
            currentPlayer = Color.YELLOW;
        }
    }

    // Get the board that contains the pieces
    public Board getTalabiaChessBoard() {
        return talabiaChessBoard;
    }
}
