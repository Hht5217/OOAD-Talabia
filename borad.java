public class borad{
    private piece[][] talabiaChessBoard;

    public board(int xRow, yColumn){
        talabiaChessBoard = new piece[xRow][yColumn];
    }

    /*
	 * Checks that a spot on the board is empty and
	 * is a movable spot
	 * xRow The x position of spot
	 * yyColum The y position of spot
	 * @return true if empty spot.
	 */
    public boolean isEmptySpace(int xSpace, int ySpace){
        if(isEntering(xPlace, ySpace)){
            if(talabiaChessBoard[xRow][yColumn] == null){
                return true;
            }
            return false;
        }
    }
    /*
	 * Displays the current board
	 * 
	 * THIS IS NOT COMPLETE. MERELY FOR PLAYING WITH GAME EARLY ON.
	 */

     public void displaysBoard(){
        for(int xBoard = 0; xBoard < getXRow(); xBoard++){
            for(int yBoard = 0; yBoard < getYColumn(); yBoard++){
                if(talabiaChessBoard[xRow][yColumn] == null){
                    Ststem.out.print(".");
                }
                else{
                    if(talabiaChessBoard[xRow][yColumn] instanceof plus)
                        Sytem.out.print("p")
                    else
                        System.out.print("x")
                }
            }
            System.out.prrintln();
        }
     }

     //Getters/Setters below

     public int getXRow(){
        return talabiaChessBoard[0].length;
     }

     public int getYColumn(){
        return talabiaChessBoard.length;
     }

     public piece[][] getTalabiaChessBoard(){
        return talabiaChessBoard;
     }

     public void outOfBoard(piece removePiece){
        int oldXPlace = removePiece.getXRow();
        int oldYPlace = removePiece.getYColumn();

        talabiaChessBoard[oldXPlace][oldYPlace] = null;

     }

     public void placePiece(piece talabiaChessPiece, int xSpace, int ySpace){
        if(isEntering(xSpace, ySpace)
            talabiaChessBoard[xSpace][ySpace]) = talabiaChessPiece;
     }

}