public class plus plus extends piece{
    public plus(board board){
        super(board)
    }
    
    public boolean moveTo(int xPlace, yPlace){
        if(moveSquare(xPlace, yPlace)){
            return plusMovement(xPlace, yPlace);
        }
        return false;
    }
    /*
	 * Specifies the rules for how a plus can move.
	 * plus can move in straight lines,
	 * as long as no unit is in the way.
	 * 
	 * xPlace - The x direction the plus wants to move
	 * yPlace - the y direction the plus wants to move
	 * @return - True if the location is a valid spot to move.
	 */

    private boolean plusMovement(int xPlace, int yPlace){
        return movingStraight(xPlace, yPlace);
    }
}

