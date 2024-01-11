package model;

public interface BoardCallback {
    boolean isEmptySpace(int yPos, int xPos);

    Piece getPiece(int yPos, int xPos);

    boolean inBoard(int yPos, int xPos);

    int getY();

    int getX();
}
