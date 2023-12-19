import java.awt.*;
import java.util.*;
import javax.swing.*;

public class View {
    private JButton[] chessButtons = new JButton[42];
    private int chessButtonsIndex = 0;

    public View(Model m) {
        JFrame talabiaFrame = new JFrame("Talabia");
        talabiaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        talabiaFrame.setSize(700, 600);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 7));
        for (int r = 1; r <= 6; r++) {
            for (int c = 1; c <= 7; c++) {
                JButton chessButton = new JButton("r" + r + "c" + c);
                Dimension buttonSize = new Dimension(100, 100);
                chessButton.setSize(buttonSize);
                chessButton.setText(null);
                // if (chessButtonsIndex % 2 == 0) {
                // chessButton.setBackground(Color.WHITE);
                // } else {
                // chessButton.setBackground(Color.BLACK);
                // }
                chessButton.setBackground(Color.WHITE);
                chessButtons[chessButtonsIndex] = chessButton;
                chessButtonsIndex++;
                boardPanel.add(chessButton);
            }
        }

        addPieceImage(m);
        talabiaFrame.add(boardPanel);
        talabiaFrame.setLocationRelativeTo(null);
        // talabiaFrame.pack();
        talabiaFrame.setVisible(true);
    }

    public void addPieceImage(Model model) {
        Board talabiaChessBoard = model.getTalabiaChessBoard();

        for (int r = 0; r < talabiaChessBoard.getY(); r++) {
            for (int c = 0; c < talabiaChessBoard.getX(); c++) {
                Piece piece = talabiaChessBoard.getPiece(r, c);
                if (piece != null) {
                    int buttonNumber = (r * talabiaChessBoard.getX()) + c;
                    int buttonWidth = chessButtons[buttonNumber].getWidth();
                    int buttonHeight = chessButtons[buttonNumber].getHeight();
                    chessButtons[buttonNumber].setIcon(piece.getPieceImage(buttonWidth, buttonHeight));
                }
            }
        }
    }
}
