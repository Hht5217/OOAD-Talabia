package src.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;

public abstract class Piece {
    private String pieceName;
    private int xPos;
    private int yPos;
    private Color color;
    private Board pieceBoard;
    private boolean selected = false;
    protected List<Move> movePos = new ArrayList<>();

    public Piece(String pieceName, int yPos, int xPos, Color color, Board pieceBoard) {
        this.pieceName = pieceName;
        this.yPos = yPos;
        this.xPos = xPos;
        this.color = color;
        this.pieceBoard = pieceBoard;
    }

    // Get the image of the chess piece with given name
    public ImageIcon getPieceImage(int buttonWidth, int buttonHeight) {
        String imageName = "src/resources/images/" + toString() + ".png";
        URL imageUrl = getClass().getClassLoader().getResource(imageName);

        if (imageUrl != null) {
            Image image = new ImageIcon(imageUrl).getImage();
            Image scaledImage = image.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.out.println("Image not found: " + imageName);
            return null;
        }
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
