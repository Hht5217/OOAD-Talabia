public class Piece {
    private int xPos;
    private int yPos;
    private int color;
    private boolean moving;

    public Piece(int xPos, int yPos, int color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
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
}
