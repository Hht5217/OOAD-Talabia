package model;

import controller.GameObserver;
import pieces.*;

import java.util.List;
import java.util.ArrayList;

// Model class
public class Game {
    // private ArrayList<Piece> player1Pieces;
    // private ArrayList<Piece> player2Pieces;
    private Board gameBoard;
    private int moveCount = 0;
    private PlayerColor currentPlayer = PlayerColor.YELLOW;
    private List<GameObserver> observers = new ArrayList<>();

    public Game() {
        gameBoard = new Board();
        addPieces();
        // Test purpose
        gameBoard.printBoard(); // test
    }

    private void addPieces() { // can be reused if start new game
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

    ///////////////////// Add pieces /////////////////////
    private void addYPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new Point(Integer.toString(i + 1), 4, i, PlayerColor.YELLOW, "NORTH", gameBoard);
            gameBoard.boardAddPiece(points);
        }
    }

    private void addBPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new Point(Integer.toString(i + 1), 1, i, PlayerColor.BLUE, "SOUTH", gameBoard);
            gameBoard.boardAddPiece(points);
        }
    }

    private void addYPlus() {
        Piece plus1 = new Plus("8", 5, 0, PlayerColor.YELLOW, gameBoard);
        Piece plus2 = new Plus("9", 5, 6, PlayerColor.YELLOW, gameBoard);
        gameBoard.boardAddPiece(plus1);
        gameBoard.boardAddPiece(plus2);
    }

    private void addBPlus() {
        Piece plus1 = new Plus("8", 0, 0, PlayerColor.BLUE, gameBoard);
        Piece plus2 = new Plus("9", 0, 6, PlayerColor.BLUE, gameBoard);
        gameBoard.boardAddPiece(plus1);
        gameBoard.boardAddPiece(plus2);
    }

    private void addYHourglass() {
        Piece hourglass1 = new HourGlass("10", 5, 1, PlayerColor.YELLOW, gameBoard);
        Piece hourglass2 = new HourGlass("11", 5, 5, PlayerColor.YELLOW, gameBoard);
        gameBoard.boardAddPiece(hourglass1);
        gameBoard.boardAddPiece(hourglass2);
    }

    private void addBHourglass() {
        Piece hourglass1 = new HourGlass("10", 0, 1, PlayerColor.BLUE, gameBoard);
        Piece hourglass2 = new HourGlass("11", 0, 5, PlayerColor.BLUE, gameBoard);
        gameBoard.boardAddPiece(hourglass1);
        gameBoard.boardAddPiece(hourglass2);
    }

    private void addYTime() {
        Piece time1 = new Time("12", 5, 2, PlayerColor.YELLOW, gameBoard);
        Piece time2 = new Time("13", 5, 4, PlayerColor.YELLOW, gameBoard);
        gameBoard.boardAddPiece(time1);
        gameBoard.boardAddPiece(time2);
    }

    private void addBTime() {
        Piece time1 = new Time("12", 0, 2, PlayerColor.BLUE, gameBoard);
        Piece time2 = new Time("13", 0, 4, PlayerColor.BLUE, gameBoard);
        gameBoard.boardAddPiece(time1);
        gameBoard.boardAddPiece(time2);
    }

    private void addYSun() {
        Piece sun = new Sun("14", 5, 3, PlayerColor.YELLOW, gameBoard);
        gameBoard.boardAddPiece(sun);
    }

    private void addBSun() {
        Piece sun = new Sun("14", 0, 3, PlayerColor.BLUE, gameBoard);
        gameBoard.boardAddPiece(sun);
    }

    ///////////////////// Other methods (remember to arrange) /////////////////////

    // Register observer
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    // Remove observer
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    // Notify observers when game is over
    private void notifyGameOver() {
        for (GameObserver observer : observers) {
            observer.onGameOver();
        }
    }

    // Notify observers when a new game is set
    private void notifySetGame() { // implement in menu controller
        for (GameObserver observer : observers) {
            observer.onNewGame();
        }
    }

    // Notify observers when an existing game is loaded
    private void notifyLoadGame() { // implement in menu controller
        for (GameObserver observer : observers) {
            observer.onLoadGame();
        }
    }

    // Get number of current turn
    public int getMoveCount() {
        return moveCount;
    }

    // Set turn by incrementing with 1
    public void setMoveCount() {
        moveCount = moveCount + 1;
    }

    // Get current player
    public PlayerColor getPlayer() {
        return currentPlayer;
    }

    // Set current player
    public void setPlayer() {
        if (currentPlayer == PlayerColor.YELLOW) {
            currentPlayer = PlayerColor.BLUE;
            System.out.println("Set to blue"); // test
        } else {
            currentPlayer = PlayerColor.YELLOW;
            System.out.println("Set to yellow"); // test
        }
    }

    // Move piece to new position, then if sun is captured notify observer
    public void movePiece(Piece piece, Move movePos) {
        if (gameBoard.setPiece(piece, movePos)) {
            notifyGameOver();
            System.out.println("Board: sun captured"); // test
        }

        if (piece instanceof Point) {
            ((Point) piece).updateDirection();
        }
    }

    // Get the board that contains the pieces
    public Board getGameBoard() {
        return gameBoard;
    }

    // Check whether to transform pieces
    public boolean checkTransformation() {
        if (moveCount % 4 == 0) {
            return true;
        }
        return false;
    }

    // Transform pieces
    public void allowTransformation() {
        gameBoard.transformPieces();
    }

    // Test reset game
    public void setNewGame() {
        System.out.println("Game: Set New"); // test
        notifySetGame();
    }

    // Test load game
    public void setLoadGame() {
        System.out.println("Game: Load"); // test
        notifyLoadGame();
    }
}