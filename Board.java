import java.util.*;

//  comment
public class Board {
    private Piece[][] pieces;

    public Board() {
        this.pieces = new Piece[6][7];
    }

    public boolean isEmptySpace(int xPos, int yPos) {
        if (pieces[xPos][yPos] == null) {
            return true;
        }
        return false;
    }

    public void addPiece(Piece pieceToAdd) {
        int addX = pieceToAdd.getXPos();
        int addY = pieceToAdd.getYPos();
        pieces[addX][addY] = pieceToAdd;
    }

    // Check if the piece is within the board
    public boolean inBoard(int xPos, int yPos){
		return xPos >= 0 && xPos < getX() && yPos >= 0 && yPos < getY();
	}

    // Check status of specific location on board
    public Piece pieceLocation(int xPos, int yPos){
		if (inBoard(xPos, yPos)){
			return pieces[xPos][yPos];
		}
		return null;
	}

    public int getX() {
        return pieces[0].length;
    }

    public int getY() {
        return pieces.length;
    }

    public Piece getPiece(int pieceX, int pieceY) {
        return pieces[pieceX][pieceY];
    }
}
