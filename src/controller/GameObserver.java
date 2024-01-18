package controller;

public interface GameObserver {
    void onGameOver();

    void onNewGame();

    void onLoadGame();
}
