/**
 * The game class is responsible for managing the game states such as which
 * player is playing, if game is over, and the move count. Game class also tells
 * the board what to do with the pieces if certain conditions are met, such as
 * the trasnformation of Plus and Time pieces every 4 moves.
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
    private List<GameObserver> observers = new ArrayList<>(); // The list of observers of game object
    private Board gameBoard; // The board that will be used to play
    private int moveCount = 0; // The move count made by players
    private PlayerColor currentPlayer; // The player to make a move
    private boolean isGameOver = false; // If game is over

    /**
     * The constructor of game object. Only need to initialize the board. The method
     * setNewGame will initialize the pieces.
     * 
     * @author HhT
     */
    public Game() {
        gameBoard = new Board(); // Initialize the game board
    }

    /* ------------------------------- Add pieces ------------------------------- */
    /**
     * Group all the methods so they can be reused for starting new game. And since
     * this is for initialization of new game, preset properties such as positions
     * are used.
     * 
     * @author HhT
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

    /**
     * Add Point pieces to the board.
     * 
     * @author HhT
     * @author Lim KZ
     */
    private void addPoint(String id, int yPos, int xPos, PlayerColor color, String direction, Board gameBoard) {
        gameBoard.addPiece(new Point(id, yPos, xPos, color, direction, gameBoard));
    }

    /**
     * Add Plus pieces to the board.
     * 
     * @author HhT
     * @author Lim KZ
     */
    private void addPlus(String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        gameBoard.addPiece(new Plus(id, yPos, xPos, color, gameBoard));
    }

    /**
     * Add Hourglass pieces to the board.
     * 
     * @author HhT
     * @author Lim KZ
     */
    private void addHourglass(String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        gameBoard.addPiece(new Hourglass(id, yPos, xPos, color, gameBoard));
    }

    /**
     * Add Time pieces to the board.
     * 
     * @author HhT
     * @author Lim KZ
     */
    private void addTime(String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        gameBoard.addPiece(new Time(id, yPos, xPos, color, gameBoard));
    }

    /**
     * Add Sun pieces to the board.
     * 
     * @author HhT
     * @author Lim KZ
     */
    private void addSun(String id, int yPos, int xPos, PlayerColor color, Board gameBoard) {
        gameBoard.addPiece(new Sun(id, yPos, xPos, color, gameBoard));
    }
    /* -------------------------------------------------------------------------- */

    /* ---------------------------- Observer related ---------------------------- */
    /**
     * Register observer for game object.
     * 
     * @author HhT
     */
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove observer (not used in current version).
     * 
     * @author HhT
     */
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify observers when game is over.
     * 
     * @author HhT
     */
    private void notifyGameOver() {
        for (GameObserver observer : observers) {
            observer.onGameOver();
        }
    }

    /**
     * Notify observers when a new game is set.
     * 
     * @author HhT
     */
    private void notifyNewGame() { // implement in menu controller
        for (GameObserver observer : observers) {
            observer.onNewGame();
        }
    }

    /**
     * Notify observers when an existing game is loaded.
     * 
     * @author HhT
     */
    private void notifyLoadGame() { // implement in menu controller
        for (GameObserver observer : observers) {
            observer.onLoadGame();
        }
    }

    /**
     * Tell the board to add observers to the Point pieces.
     * 
     * @author HhT
     */
    public void addPointsObserver(GameObserver observer) {
        this.gameBoard.boardAddPointsObserver(observer);
    }

    /**
     * Tell the board to remove observers from the Point pieces.
     * 
     * @author HhT
     */
    public void removePointsObserver(GameObserver observer) {
        this.gameBoard.boardRemovePointsObserver(observer);
    }
    /* -------------------------------------------------------------------------- */

    /* ---------------------- Manage and modify game state ---------------------- */
    /**
     * Get the board instance.
     * 
     * @return the board that contains the pieces
     * @author HhT
     */
    public Board getGameBoard() {
        return gameBoard;
    }

    /**
     * Get the current move count.
     * 
     * @return the move count.
     * @author HhT
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Increment move count by one.
     * 
     * @author HhT
     */
    public void setMoveCount() {
        moveCount = moveCount + 1;
    }

    /**
     * Check the current player to play
     * 
     * @return the current player
     * @author HhT
     */
    public PlayerColor getPlayer() {
        return currentPlayer;
    }

    /**
     * Set current player. No parameter for this setter, since for this game there
     * is currently only two players.
     * 
     * @author HhT
     */
    public void setPlayer() {
        if (currentPlayer == PlayerColor.YELLOW) {
            currentPlayer = PlayerColor.BLUE;
        } else {
            currentPlayer = PlayerColor.YELLOW;
        }
    }

    /**
     * Check if game is over.
     * 
     * @return the boolean of game over state
     * @author HhT
     */
    public boolean checkGameOver() {
        return isGameOver;
    }

    /**
     * Set game over state.
     * 
     * @param state The state of game over to be set
     * @author HhT
     */
    public void setGameOver(boolean state) {
        isGameOver = state;
    }

    /**
     * Move pieces, if sun is captured then game over, and if moved piece is a Point
     * then call the updateDirection method.
     * 
     * @param piece        the piece to be moved
     * @param movePosition the position to move the piece to
     * @author HhT
     */
    public void movePiece(Piece piece, Move movePosition) {
        if (gameBoard.setPiece(piece, movePosition)) { // setPiece returns true if Sun is captured
            notifyGameOver(); // Notify the observer game is over
        }

        if (piece instanceof Point) {
            Point point = (Point) piece;
            /*
             * setDirection will check if Point reaches border, if true then change
             * direction of Point
             */
            point.setDirection();
        }
    }

    /**
     * Check whether transformation is allowed to happen by checking the move count.
     * 
     * @return true if move count is divisible by 4
     * @return false if move count is not divisible by 4
     * @author HhT
     */
    public boolean checkTransformation() {
        if (moveCount % 4 == 0) {
            return true;
        }
        return false;
    }

    /**
     * Tell the board to transform the pieces.
     * 
     * @author HhT
     */
    public void allowTransformation() {
        gameBoard.transformPieces();
    }

    /**
     * Set the state of game to initial values.
     * 
     * @author HhT
     */
    public void setNewGame() {
        currentPlayer = PlayerColor.YELLOW;
        moveCount = 0;
        gameBoard.clearBoard();
        initPieces();
        notifyNewGame();
    }

    /**
     * Load game by reading the game file, line by line. Loaded information includes
     * game states and pieces.
     * 
     * @param loadedFile the file choosen, which is passed by controller class
     * @author HhT
     */
    public void setLoadGame(File loadedFile) throws IOException {
        // Clear the board first before loading pieces to it
        gameBoard.clearBoard();

        try {
            List<String> lines = Files.readAllLines(loadedFile.toPath()); // Get the lines of file and put into list
            for (String line : lines) {
                line = line.trim(); // Removes whitespace from both ends
                if (!line.startsWith("//")) { // Ignore the lines that start with comment symbol
                    String[] parts = line.split(": "); // Splits the line in two parts at the ":""
                    String key = parts[0]; // The type of information to be loaded
                    String value = parts[1]; // The value of the information

                    switch (key) { // Parse the corresponding information to correct data type
                        case "moveCount":
                            moveCount = Integer.parseInt(value);
                            break;
                        case "currentPlayer":
                            currentPlayer = PlayerColor.valueOf(value);
                            break;
                        case "isGameOver":
                            isGameOver = Boolean.parseBoolean(value);
                            break;
                        case "piece": // Load different information of pieces
                            String[] pieceParts = value.split(", "); // Split information at ","
                            String type = pieceParts[0]; // The type of the piece
                            String id = pieceParts[1]; // The unique id assigned to the piece
                            int yPos = Integer.parseInt(pieceParts[2]); // Y position of the piece on the board
                            int xPos = Integer.parseInt(pieceParts[3]); // X position of the piece on the board
                            PlayerColor color = pieceParts[4].equals("YELLOW") ? PlayerColor.YELLOW : PlayerColor.BLUE;
                            Piece pieceToLoad; // The piece to be loaded onto the board
                            PieceFactory factory = new PieceFactory(); // Create pieces using piece factory
                            if ("Point".equals(type)) { // Point piece needs to load direction
                                String direction = pieceParts[5]; // The direction of the Point piece
                                pieceToLoad = factory.createPiece(type, id, yPos, xPos, color, direction, gameBoard);
                            } else {
                                pieceToLoad = factory.createPiece(type, id, yPos, xPos, color, gameBoard);
                            }
                            gameBoard.addPiece(pieceToLoad);
                            break;

                    }
                }
            }
        }
        // Catch exception in case loaded file has problems ie. it has been modified
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            throw new IOException("Invalid save file: Check the content", ex);
        }

        notifyLoadGame(); // Notify the observer that a game has been loaded
    }

    /**
     * Save game into a file. Contains information such as game states and pieces
     * with their information.
     * 
     * @param filePath the path to store the save file, passed by controller class
     * @author HhT
     */
    public void setSaveGame(String filePath) throws IOException {
        List<String> lines = new ArrayList<>(); // Create list of lines to save
        lines.add("// The move count made"); // Line starts with "//" is comment
        lines.add("moveCount: " + moveCount);
        lines.add("// The player to play a move");
        lines.add("currentPlayer: " + currentPlayer);
        lines.add("// Whether the game is over");
        lines.add("isGameOver: " + isGameOver);

        // The comment about pieces values and what they represent
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

        // Write the lines to the file and store at choosen file path
        Files.write(Paths.get(filePath), lines, StandardCharsets.UTF_8);
    }

    /* ----------------------------------- End ---------------------------------- */
}