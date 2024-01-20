/**
 * (Description)
 */
package model;

import controller.GameObserver;
import pieces.*;

import java.util.List;
import java.util.ArrayList;

public class Game {
    private List<GameObserver> observers = new ArrayList<>();
    private Board gameBoard;
    private int moveCount = 0;
    private PlayerColor currentPlayer = PlayerColor.YELLOW;
    private boolean isGameOver = false;

    public Game() {
        gameBoard = new Board();
        // addPieces();

        gameBoard.printBoard(); // test
    }

    /* ------------------------------- Add pieces ------------------------------- */
    private void addPieces() { /* Group all the methods so they can be reused for starting new game */
        // Add blue pieces
        addBluePoint();
        addBluePlus();
        addBlueHourglass();
        addBlueTime();
        addBlueSun();

        // Add yellow pieces
        addYellowPoint();
        addYellowPlus();
        addYellowHourglass();
        addYellowTime();
        addYellowSun();
    }

    private void addYellowPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new Point(Integer.toString(i + 1), 4, i, PlayerColor.YELLOW, "NORTH", gameBoard);
            gameBoard.boardAddPiece(points);
        }
    }

    private void addBluePoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new Point(Integer.toString(i + 1), 1, i, PlayerColor.BLUE, "SOUTH", gameBoard);
            gameBoard.boardAddPiece(points);
        }
    }

    private void addYellowPlus() {
        Piece plus1 = new Plus("8", 5, 0, PlayerColor.YELLOW, gameBoard);
        Piece plus2 = new Plus("9", 5, 6, PlayerColor.YELLOW, gameBoard);
        gameBoard.boardAddPiece(plus1);
        gameBoard.boardAddPiece(plus2);
    }

    private void addBluePlus() {
        Piece plus1 = new Plus("8", 0, 0, PlayerColor.BLUE, gameBoard);
        Piece plus2 = new Plus("9", 0, 6, PlayerColor.BLUE, gameBoard);
        gameBoard.boardAddPiece(plus1);
        gameBoard.boardAddPiece(plus2);
    }

    private void addYellowHourglass() {
        Piece hourglass1 = new HourGlass("10", 5, 1, PlayerColor.YELLOW, gameBoard);
        Piece hourglass2 = new HourGlass("11", 5, 5, PlayerColor.YELLOW, gameBoard);
        gameBoard.boardAddPiece(hourglass1);
        gameBoard.boardAddPiece(hourglass2);
    }

    private void addBlueHourglass() {
        Piece hourglass1 = new HourGlass("10", 0, 1, PlayerColor.BLUE, gameBoard);
        Piece hourglass2 = new HourGlass("11", 0, 5, PlayerColor.BLUE, gameBoard);
        gameBoard.boardAddPiece(hourglass1);
        gameBoard.boardAddPiece(hourglass2);
    }

    private void addYellowTime() {
        Piece time1 = new Time("12", 5, 2, PlayerColor.YELLOW, gameBoard);
        Piece time2 = new Time("13", 5, 4, PlayerColor.YELLOW, gameBoard);
        gameBoard.boardAddPiece(time1);
        gameBoard.boardAddPiece(time2);
    }

    private void addBlueTime() {
        Piece time1 = new Time("12", 0, 2, PlayerColor.BLUE, gameBoard);
        Piece time2 = new Time("13", 0, 4, PlayerColor.BLUE, gameBoard);
        gameBoard.boardAddPiece(time1);
        gameBoard.boardAddPiece(time2);
    }

    private void addYellowSun() {
        Piece sun = new Sun("14", 5, 3, PlayerColor.YELLOW, gameBoard);
        gameBoard.boardAddPiece(sun);
    }

    private void addBlueSun() {
        Piece sun = new Sun("14", 0, 3, PlayerColor.BLUE, gameBoard);
        gameBoard.boardAddPiece(sun);
    }
    /* -------------------------------------------------------------------------- */

    /* ---------------------------- Observer related ---------------------------- */
    /* Register observer */
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /* Remove observer (not used in current version) */
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    /* Notify observers when game is over */
    private void notifyGameOver() {
        for (GameObserver observer : observers) {
            observer.onGameOver();
        }
    }

    /* Notify observers when a new game is set */
    private void notifyNewGame() { // implement in menu controller
        for (GameObserver observer : observers) {
            observer.onNewGame();
        }
    }

    /* Notify observers when an existing game is loaded */
    private void notifyLoadGame() { // implement in menu controller
        for (GameObserver observer : observers) {
            observer.onLoadGame();
        }
    }

    /* Tell the board to add observers to the Point pieces */
    public void addPointsObserver(GameObserver observer) {
        this.gameBoard.boardAddPointsObserver(observer);
    }
    /* -------------------------------------------------------------------------- */

    /* ---------------------- Manage and modify game state ---------------------- */
    /* Return the board instance of the game that contains the pieces */
    public Board getGameBoard() {
        return gameBoard;
    }

    /* Get number of current turn */
    public int getMoveCount() {
        return moveCount;
    }

    /* Increment turn by 1 */
    public void setMoveCount() {
        moveCount = moveCount + 1;
    }

    /* Get current player */
    public PlayerColor getPlayer() {
        return currentPlayer;
    }

    /* Set current player */
    public void setPlayer() {
        if (currentPlayer == PlayerColor.YELLOW) {
            currentPlayer = PlayerColor.BLUE;
        } else {
            currentPlayer = PlayerColor.YELLOW;
        }
    }

    /* Check if game is over */
    public boolean checkGameOver() {
        return isGameOver;
    }

    /* Set game over state */
    public void setGameOver(boolean state) {
        isGameOver = state;
    }

    /*
     * Move pieces, if sun is captured then game over, and if moved piece is a Point
     * then call the updateDirection method
     */
    public void movePiece(Piece piece, Move movePos) {
        if (gameBoard.setPiece(piece, movePos)) {
            notifyGameOver();
        }

        if (piece instanceof Point) {
            Point point = (Point) piece;
            point.updateDirection();
        }
    }

    /* Check whether transformation is allowed to happen */
    public boolean checkTransformation() {
        if (moveCount % 4 == 0) {
            return true;
        }
        return false;
    }

    /* Tell board to transform the pieces */
    public void allowTransformation() {
        gameBoard.transformPieces();
    }

    /* Set the state of game to initial values */
    public void setNewGame() {
        currentPlayer = PlayerColor.YELLOW;
        moveCount = 0;
        gameBoard.clearBoard();
        addPieces();
        notifyNewGame();
    }

    // Test load game
    public void setLoadGame() {
        System.out.println("Game: Load"); // test
        notifyLoadGame();
    }
    /* ----------------------------------- End ---------------------------------- */
}