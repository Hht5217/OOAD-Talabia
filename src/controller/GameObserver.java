package controller;

public interface GameObserver {
    void onGameOver();

    void onNewGame();

    void onLoadGame();

    void onDirectionChange(int yPos, int xPos, String pieceName, String direction);
}
