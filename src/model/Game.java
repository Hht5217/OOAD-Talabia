package model;

//import java.util.ArrayList; (list might not be needed in this class)

// Model class
public class Game {
    // private ArrayList<Piece> player1Pieces;
    // private ArrayList<Piece> player2Pieces;
    private Board gameBoard;
    private int moveCount = 0;
    private Color currentPlayer = Color.YELLOW;

    public Game() {
        gameBoard = new Board();
        // player1Pieces = new ArrayList<Piece>();
        // player2Pieces = new ArrayList<Piece>();
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

    public void addYPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new Point(Integer.toString(i + 1), 4, i, Color.YELLOW, "NORTH", gameBoard);
            gameBoard.addPiece(points);
            // player2Pieces.add(points);
        }
    }

    public void addBPoint() {
        for (int i = 0; i < 7; i++) {
            Piece points = new Point(Integer.toString(i + 1), 1, i, Color.BLUE, "SOUTH", gameBoard);
            gameBoard.addPiece(points);
            // player1Pieces.add(points);
        }
    }

    public void addYPlus() {
        // int row = 5;
        // int col1 = 0;
        // int col2 = 6;
        Piece plus1 = new Plus("8", 5, 0, Color.YELLOW, gameBoard);
        Piece plus2 = new Plus("9", 5, 6, Color.YELLOW, gameBoard);
        gameBoard.addPiece(plus1);
        gameBoard.addPiece(plus2);
        // player2Pieces.add(plus1);
    }

    public void addBPlus() {
        // int row = 0;
        // int col1 = 0;
        // int col2 = 6;
        Piece plus1 = new Plus("8", 0, 0, Color.BLUE, gameBoard);
        Piece plus2 = new Plus("9", 0, 6, Color.BLUE, gameBoard);
        gameBoard.addPiece(plus1);
        gameBoard.addPiece(plus2);
        // player1Pieces.add(points);
    }

    public void addYHourglass() {
        // int row = 5;
        // int col1 = 1;
        // int col2 = 5;
        Piece hourglass1 = new HourGlass("10", 5, 1, Color.YELLOW, gameBoard);
        Piece hourglass2 = new HourGlass("11", 5, 5, Color.YELLOW, gameBoard);
        gameBoard.addPiece(hourglass1);
        gameBoard.addPiece(hourglass2);
        // player2Pieces.add(points);
    }

    public void addBHourglass() {
        // int row = 0;
        // int col1 = 1;
        // int col2 = 5;
        Piece hourglass1 = new HourGlass("10", 0, 1, Color.BLUE, gameBoard);
        Piece hourglass2 = new HourGlass("11", 0, 5, Color.BLUE, gameBoard);
        gameBoard.addPiece(hourglass1);
        gameBoard.addPiece(hourglass2);
        // player1Pieces.add(points);
    }

    public void addYTime() {
        // int row = 5;
        // int col1 = 2;
        // int col2 = 4;
        Piece time1 = new Time("12", 5, 2, Color.YELLOW, gameBoard);
        Piece time2 = new Time("13", 5, 4, Color.YELLOW, gameBoard);
        gameBoard.addPiece(time1);
        gameBoard.addPiece(time2);
        // player2Pieces.add(points);
    }

    public void addBTime() {
        // int row = 0;
        // int col1 = 2;
        // int col2 = 4;
        Piece time1 = new Time("12", 0, 2, Color.BLUE, gameBoard);
        Piece time2 = new Time("13", 0, 4, Color.BLUE, gameBoard);
        gameBoard.addPiece(time1);
        gameBoard.addPiece(time2);
        // player1Pieces.add(points);
    }

    public void addYSun() {
        // int row = 5;
        // int col = 3;
        Piece sun = new Sun("14", 5, 3, Color.YELLOW, gameBoard);
        gameBoard.addPiece(sun);
        // player2Pieces.add(points);
    }

    public void addBSun() {
        // int row = 0;
        // int col = 3;
        Piece sun = new Sun("14", 0, 3, Color.BLUE, gameBoard);
        gameBoard.addPiece(sun);
        // player1Pieces.add(points);
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
}