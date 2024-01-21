package pieces;

import java.util.ArrayList;
import java.util.List;
import model.*;

public abstract class Piece {
    protected String id;
    protected String pieceName;
    protected int xPos;
    protected int yPos;
    protected PlayerColor color;
    protected Board pieceBoard;
    protected boolean selected = false;
    protected List<Move> movePos = new ArrayList<>();

    public Piece(String id, int yPos, int xPos, PlayerColor color, Board pieceBoard) {
        this.id = id;
        if (color == PlayerColor.YELLOW) {
            this.pieceName = "y" + getType() + id;
        } else {
            this.pieceName = "b" + getType() + id;
        }
        this.yPos = yPos;
        this.xPos = xPos;
        this.color = color;
        this.pieceBoard = pieceBoard;
    }

    // Get id
    public String getId() {
        return id;
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
    public PlayerColor getColor() {
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
    public String getPieceName() {
        return pieceName;
    }

    // Get type (class name) of piece
    public String getType() {
        return getClass().getSimpleName();
    }

    // abstract method since every sub-pieces will have different calculations
    public abstract List<Move> getAvailableMoves();

    // Transform the pieces, but only two type will use this
    public Piece transform() {
        return this; // Default behavior is to not transform
    }
}
