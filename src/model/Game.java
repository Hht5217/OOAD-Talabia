/**
 * (Description)
 */
package model;

import controller.GameObserver;
import pieces.*;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Game {
    private List<GameObserver> observers = new ArrayList<>();
    private Board gameBoard;
    private int moveCount = 0;
    private PlayerColor currentPlayer = PlayerColor.YELLOW;
    private boolean isGameOver = false;

    public Game() {
        gameBoard = new Board(); // Initialize the game board
    }

    /* ------------------------------- Add pieces ------------------------------- */
    /*
     * Group all the methods so they can be reused for starting new game. And since
     * this is for initialization of new game, preset properties such as positions
     * are used.
     */
    private void initPieces() {
        // Add Point pieces
        for (int i = 0; i < 7; i++) {
            addPoint(Integer.toString(i + 1), 4, i, PlayerColor.YELLOW, "NORTH", gameBoard); // Yellow points
            addPoint(Integer.toString(i + 1), 1, i, PlayerColor.BLUE, "SOUTH", gameBoard); // Blue points
        }

        // Add Plus pieces
        addPlus("8", 5, 0, PlayerColor.YELLOW, gameBoard); // First yellow Plus
        addPlus("9", 5, 6, PlayerColor.YELLOW, gameBoard); // Second yellow Plus
        addPlus("8", 0, 0, PlayerColor.BLUE, gameBoard); // First blue Plus
        addPlus("9", 0, 6, PlayerColor.BLUE, gameBoard); // Second blue Plus

        // Add Hourglass pieces
        addHourglass("10", 5, 1, PlayerColor.YELLOW, gameBoard); // First yellow Hourglass
        addHourglass("11", 5, 5, PlayerColor.YELLOW, gameBoard); // Second yellow Hourglass
        addHourglass("10", 0, 1, PlayerColor.BLUE, gameBoard); // First blue Hourglass
        addHourglass("11", 0, 5, PlayerColor.BLUE, gameBoard); // Second blue Hourglass

        // Add Time pieces
        addTime("12", 5, 2, PlayerColor.YELLOW, gameBoard); // First yellow Time
        addTime("13", 5, 4, PlayerColor.YELLOW, gameBoard); // Second yellow Time
        addTime("12", 0, 2, PlayerColor.BLUE, gameBoard); // First blue Time
        addTime("13", 0, 4, PlayerColor.BLUE, gameBoard); // Second blue Time

        // Add Sun pieces
        addSun("14", 5, 3, PlayerColor.YELLOW, gameBoard); // Yellow Sun
        addSun("14", 0, 3, PlayerColor.BLUE, gameBoard); // Blue Sun
    }

    private void addPoint(String id, int yPos, int xPos, PlayerColor color, String direction, Board gameBoard) {
        gameBoard.addPiece(new Point(id, yPos, xPos, color, direction, gameBoard));
    }

    private void addPlus(String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        gameBoard.addPiece(new Plus(id, yPos, xPos, color, gameBoard));
    }

    private void addHourglass(String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        gameBoard.addPiece(new Hourglass(id, yPos, xPos, color, gameBoard));
    }

    private void addTime(String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        gameBoard.addPiece(new Time(id, yPos, xPos, color, gameBoard));
    }

    private void addSun(String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        gameBoard.addPiece(new Sun(id, yPos, xPos, color, gameBoard));
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

    /* Tell the board to remove observers from the Point pieces */
    public void removePointsObserver(GameObserver observer) {
        this.gameBoard.boardRemovePointsObserver(observer);
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
        initPieces();
        notifyNewGame();
    }

    // Test load game
    public void setLoadGame(File loadedFile) throws IOException {
        // Clear the board first before loading pieces to it
        gameBoard.clearBoard();

        try {
            List<String> lines = Files.readAllLines(loadedFile.toPath());
            for (String line : lines) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (!line.startsWith("//")) { // Ignore comments
                    String[] parts = line.split(": ");
                    String key = parts[0];
                    String value = parts[1];

                    switch (key) {
                        case "moveCount":
                            moveCount = Integer.parseInt(value);
                            break;
                        case "currentPlayer":
                            currentPlayer = PlayerColor.valueOf(value);
                            break;
                        case "isGameOver":
                            isGameOver = Boolean.parseBoolean(value);
                            break;
                        case "piece":
                            String[] pieceParts = value.split(", ");
                            String type = pieceParts[0];
                            String id = pieceParts[1];
                            int yPos = Integer.parseInt(pieceParts[2]);
                            int xPos = Integer.parseInt(pieceParts[3]);
                            PlayerColor color = pieceParts[4].equals("YELLOW") ? PlayerColor.YELLOW : PlayerColor.BLUE;
                            Piece pieceToLoad;
                            PieceFactory factory = new PieceFactory();
                            if ("Point".equals(type)) {
                                String direction = pieceParts[5];
                                pieceToLoad = factory.createPiece(type, id, yPos, xPos, color, direction, gameBoard);
                            } else {
                                pieceToLoad = factory.createPiece(type, id, yPos, xPos, color, gameBoard);
                            }
                            gameBoard.addPiece(pieceToLoad);
                            break;

                    }
                }
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            throw new IOException("Invalid save file format", ex);
        }

        notifyLoadGame();
    }

    public void setSaveGame(String filePath) throws IOException {
        // Save the simple game state properties
        List<String> lines = new ArrayList<>();
        lines.add("// The move count made");
        lines.add("moveCount: " + moveCount);
        lines.add("// The player to play a move");
        lines.add("currentPlayer: " + currentPlayer);
        lines.add("// Whether the game is over");
        lines.add("isGameOver: " + isGameOver);

        // Add a comment about the pieces
        lines.add("// Type, ID, y Position, x Position, Color, direction (for Point)");

        // Save the pieces
        for (Piece[] row : gameBoard.getAllPieces()) {
            for (Piece piece : row) {
                if (piece != null) {
                    /*
                     * StringBuilder is a mutable string type that can be modified after created.
                     * Means that if want to add more strings behind no need to create a new string
                     * to concatenate strings, just do append like an array.
                     */
                    StringBuilder line = new StringBuilder("piece: ");
                    line.append(piece.getType()).append(", "); // Use getType() instead of getPieceName()
                    line.append(piece.getId()).append(", "); // Add the id
                    line.append(piece.getYPos()).append(", ");
                    line.append(piece.getXPos()).append(", ");
                    line.append(piece.getColor()); // Add the color
                    if (piece instanceof Point) {
                        line.append(", ").append(((Point) piece).getDirection());
                    }
                    lines.add(line.toString());
                }
            }
        }

        // Write the lines to the file
        Files.write(Paths.get(filePath), lines, StandardCharsets.UTF_8);
    }

    /* ----------------------------------- End ---------------------------------- */
}