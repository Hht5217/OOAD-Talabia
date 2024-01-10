package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected String pieceName;
    protected int xPos;
    protected int yPos;
    protected Color color;
    protected Board pieceBoard;
    protected boolean selected = false;
    protected List<Move> movePos = new ArrayList<>();

    public Piece(String pieceName, int yPos, int xPos, Color color, Board pieceBoard) {
        this.pieceName = pieceName;
        this.yPos = yPos;
        this.xPos = xPos;
        this.color = color;
        this.pieceBoard = pieceBoard;
    }

    // Get the image file name from piece name
    public String getImageName() {
        String pieceName = toString().substring(0, toString().length() - 1);
        String imageName = pieceName + ".png";
        return imageName;
    }

    // Get y position of piece
    public int getYPos() {
        return yPos;
    }

    // Get X position of piece
    public int getXPos() {
        return xPos;
    }

    // Set location of piece
    public void setPos(int newYPos, int newXPos) {
        this.yPos = newYPos;
        this.xPos = newXPos;
    }

    // Get color of piece
    public Color getColor() {
        return color;
    }

    // Check if piece selected
    public boolean getSelected() {
        return selected;
    }

    // Set state of select
    public void setSelected(boolean select) {
        this.selected = select;
    }

    // Get chess piece name
    public String toString() {
        return pieceName;
    }

    // abstract method since every sub-pieces will have different calculations
    public abstract List<Move> getAvailableMoves();
}
