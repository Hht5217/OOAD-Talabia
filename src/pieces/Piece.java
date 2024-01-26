/**
 * The Piece class is an abstract class that represents a generic chess piece.
 * It contains common attributes and methods that all specific
 * types of chess pieces will have. This class serves as a blueprint from which
 * other specific chess piece classes are derived.
 */
package src.pieces;

import java.util.ArrayList;
import java.util.List;
import model.*;

public abstract class Piece {
     protected String id; // The unique identifier for pieces
     protected String pieceName; // Name of the piece
     protected int yPos; // Y position of the piece on the game board
     protected int xPos; // X position of the piece on the game board
     protected PlayerColor color; // Which color (player) the piece belongs to
     protected Board pieceBoard; // The board where piece is on
     protected boolean selected = false; // State of select (by user)
     protected List<Move> moveList = new ArrayList<>(); // List of available moves

     /**
      * Constructor for Piece object
      * 
      * @param id         the id assigned to pieces
      * @param yPos       the y position
      * @param xPos       the x position
      * @param color      the color
      * @param pieceBoard the board piece is on
      * @author HhT
      */
     public Piece(String id, int yPos, int xPos, PlayerColor color, Board pieceBoard) {
          this.id = id;
          // Combine color, type and id to become piece name
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

     /* ----------------------------- Getter methods ----------------------------- */
     /**
      * Get the id of the piece.
      * 
      * @return the id in String
      * @author HhT
      */
     public String getId() {
          return id;
     }

     /**
      * Get the y position of the piece on the board.
      * 
      * @return the y position in int
      * @author HhT
      */
     public int getYPos() {
          return yPos;
     }

     /**
      * Get the x position of the piece on the board.
      * 
      * @return the x position in int
      * @author HhT
      */
     public int getXPos() {
          return xPos;
     }

     /**
      * Get which player the piece belongs to.
      * 
      * @return the player color
      * @author HhT
      */
     public PlayerColor getColor() {
          return color;
     }

     /**
      * Get the state of whether the piece is selected by user.
      * 
      * @return true if selected
      * @author HhT
      */
     public boolean getSelected() {
          return selected;
     }

     /**
      * Get the name of the piece.
      * 
      * @return the piece's full name in String
      * @author HhT
      */
     public String getPieceName() {
          return pieceName;
     }

     /**
      * Get the type (class) of the piece.
      * 
      * @return the type of the piece in String
      * @author HhT
      */
     public String getType() {
          return getClass().getSimpleName();
     }
     /* -------------------------------------------------------------------------- */

     /* ----------------------------- Setter methods ----------------------------- */
     /**
      * Set the location of the piece after moving on the board.
      * 
      * @param newYPos new y position
      * @param newXPos new x position
      * @author HhT
      */
     public void setPosition(int newYPos, int newXPos) {
          this.yPos = newYPos;
          this.xPos = newXPos;
     }

     /**
      * Set state when piece is selected or deselected.
      * 
      * @param select true when selected and false when deselected
      * @author HhT
      */
     public void setSelected(boolean select) {
          this.selected = select;
     }
     /* -------------------------------------------------------------------------- */

     /* ------------------------ Movement related methods ------------------------ */
     /**
      * The calculation of moves for every type of pieces. Implemented as abstract
      * method to ensure every type of pieces will implement it since they all have
      * different move calculations.
      * 
      * @return the list of available moves, in Move object form
      * @author HhT
      */
     public abstract List<Move> getAvailableMoves();

     /**
      * Transformation method. Default behaviour is to return the same piece
      * instance, meaning that nothing will change if the piece type is not required
      * to transform. If it does, such as Plus and Time piece, they will override
      * this method and implement their own transformation method.
      * 
      * @return the piece instance
      * @author HhT
      */
     public Piece transform() {
          return this;
     }
     /* ----------------------------------- End ---------------------------------- */
}
