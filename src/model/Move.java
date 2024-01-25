/**
 * The Move class is a simple data structure that represents a position on a
 * chess board. It encapsulates a pair of integers (row and column), which are
 * the coordinates of a position on the board. This class is essential in
 * representing and manipulating the positions of various elements such as
 * pieces or buttons on the board.
 */
package src.model;

public class Move {
    private int row; // Represents row or y position
    private int column; // Represents column or x position

    /**
     * Consturctor of Move object.
     * 
     * @param row    the row / y position
     * @param column the column / x position
     * @author HhT & lkz
     */
    public Move(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * @return the row
     * @author HhT & lkz
     */
    public int getMoveRow() {
        return row;
    }

    /**
     * @return the column
     * @author HhT & lkz
     */
    public int getMoveColumn() {
        return column;
    }
}
