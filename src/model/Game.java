package model;

import java.util.ArrayList;

// Model class
public class Game {
    private ArrayList<Piece> player1Pieces;
    private ArrayList<Piece> player2Pieces;
    private Board gameBoard;
    private int currentTurn = 0;
    private Color currentPlayer = Color.YELLOW;

    public Game() {
        gameBoard = new Board();
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
        gameBoard.printBoard();
    }

    // number of piece
    public void addYPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new ThePoint(("yPoint" + (i + 1)), 4, i, Color.YELLOW, "NORTH", gameBoard);
            gameBoard.addPiece(points);
            player2Pieces.add(points);
        }
    }

    public void addBPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new ThePoint(("bPoint" + (i + 1)), 1, i, Color.BLUE, "SOUTH", gameBoard);
            gameBoard.addPiece(points);
            player1Pieces.add(points);
        }
    }

    public void addYPlus() {
        // int row = 5;
        // int col1 = 0;
        // int col2 = 6;
        Piece points = new Plus("yPlus1", 5, 0, Color.YELLOW, gameBoard);
        Piece points2 = new Plus("yPlus2", 5, 6, Color.YELLOW, gameBoard);
        gameBoard.addPiece(points);
        gameBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBPlus() {
        // int row = 0;
        // int col1 = 0;
        // int col2 = 6;
        Piece points = new Plus("bPlus1", 0, 0, Color.BLUE, gameBoard);
        Piece points2 = new Plus("bPlus2", 0, 6, Color.BLUE, gameBoard);
        gameBoard.addPiece(points);
        gameBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYHourglass() {
        // int row = 5;
        // int col1 = 1;
        // int col2 = 5;
        Piece points = new HourGlass("yHourglass1", 5, 1, Color.YELLOW, gameBoard);
        Piece points2 = new HourGlass("yHourglass2", 5, 5, Color.YELLOW, gameBoard);
        gameBoard.addPiece(points);
        gameBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBHourglass() {
        // int row = 0;
        // int col1 = 1;
        // int col2 = 5;
        Piece points = new HourGlass("bHourglass1", 0, 1, Color.BLUE, gameBoard);
        Piece points2 = new HourGlass("bHourglass2", 0, 5, Color.BLUE, gameBoard);
        gameBoard.addPiece(points);
        gameBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYTime() {
        // int row = 5;
        // int col1 = 2;
        // int col2 = 4;
        Piece points = new Time("yTime1", 5, 2, Color.YELLOW, gameBoard);
        Piece points2 = new Time("yTime2", 5, 4, Color.YELLOW, gameBoard);
        gameBoard.addPiece(points);
        gameBoard.addPiece(points2);
        player2Pieces.add(points);
    }

    public void addBTime() {
        // int row = 0;
        // int col1 = 2;
        // int col2 = 4;
        Piece points = new Time("bTime1", 0, 2, Color.BLUE, gameBoard);
        Piece points2 = new Time("bTime2", 0, 4, Color.BLUE, gameBoard);
        gameBoard.addPiece(points);
        gameBoard.addPiece(points2);
        player1Pieces.add(points);
    }

    public void addYSun() {
        // int row = 5;
        // int col = 3;
        Piece points = new Sun("ySun1", 5, 3, Color.YELLOW, gameBoard);
        gameBoard.addPiece(points);
        player2Pieces.add(points);
    }

    public void addBSun() {
        // int row = 0;
        // int col = 3;
        Piece points = new Sun("bSun1", 0, 3, Color.BLUE, gameBoard);
        gameBoard.addPiece(points);
        player1Pieces.add(points);
    }

    // Get number of current turn
    public int getTurn() {
        return currentTurn;
    }

    // Set turn by incrementing with 1
    public void setTurn() {
        currentTurn = currentTurn + 1;
    }

    // Get current player
    public Color getPlayer() {
        return currentPlayer;
    }

    // Set current player
    public void setPlayer() {
        if (currentPlayer == Color.YELLOW) {
            currentPlayer = Color.BLUE;
        } else {
            currentPlayer = Color.YELLOW;
        }
    }

    // Move piece to new position
    public void movePiece(Piece piece, Move movePos) {
        gameBoard.setPiece(piece, movePos);
        // gameBoard.removePiece(piece);

        if (piece instanceof ThePoint) {
            ((ThePoint) piece).updateDirection();
        }
    }

    // Get the board that contains the pieces
    public Board getGameBoard() {
        return gameBoard;
    }
}