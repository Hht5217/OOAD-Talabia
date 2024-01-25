/**
 * Observer pattern. This is the interface for observers.
 */
package src.controller;

public interface GameObserver {
    // What to do when game is over
    void onGameOver();

    // When a new game is set
    void onNewGame();

    // When a game is loaded
    void onLoadGame();

    // When there is direction change of Point pieces
    void onDirectionChange(int yPos, int xPos, String pieceName, String direction);
}
