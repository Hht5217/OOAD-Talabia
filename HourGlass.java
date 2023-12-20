public class HourGlass extends Piece {
    public hourGlass(Board board) {
        super(board);
    }

    public boolean Movement(int xPos, int yPos) {
        if (availableMove(xPos, yPos)) {
            return hourGlassMovement(xPos, yPos);
        }
        return false;
    }

    private boolean hourGlassMovement(int xPos, int yPos) {
        int deltaX = Math.abs(this.getXPos() - xPos);
        int deltaY = Math.abs(this.getYPos() - yPos);

        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }
}
