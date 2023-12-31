// For moving pieces and check positions purpose
public class Move {
    private int row;
    private int column;

    public Move(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getMoveRow() {
        return row;
    }

    public int getMoveColumn() {
        return column;
    }
}
