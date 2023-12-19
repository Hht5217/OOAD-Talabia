import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.awt.*;
import javax.swing.*;

public class Piece {
    private String pieceName;
    private int xPos;
    private int yPos;
    private final int YELLOW = 1;
    private final int BLUE = 2;
    private int color;
    // private boolean moving;

    public Piece(String pieceName, int xPos, int yPos, int color) {
        this.pieceName = pieceName;
        this.xPos = xPos;
        this.yPos = yPos;
        if (color == 1) {
            this.color = YELLOW;
        } else {
            this.color = BLUE;
        }
    }

    // public Piece(String pieceName, int color) {
    // this.pieceName = pieceName;
    // this.color = color;
    // }

    // Get the image of the chess piece with given name
    public ImageIcon getPieceImage(int buttonWidth, int buttonHeight) {
        String imageName = "images/" + toString() + ".png";
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

    public void movePos(int moveX, int moveY) {

    }

    // Get X position of piece
    public int getXPos() {
        return xPos;
    }

    // Get y position of piece
    public int getYPos() {
        return yPos;
    }

    // Get color of piece
    public int getColor() {
        return color;
    }

    // Get chess piece name
    public String toString() {
        return pieceName;
    }
}
