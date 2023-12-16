import java.awt.*;
import java.util.*;
import javax.swing.*;

public class View {
    private JButton[] chessButtons = new JButton[42];
    private int chessButtonsIndex = 0;

    public View() {
        JFrame talabiaFrame = new JFrame("Talabia");
        talabiaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        talabiaFrame.setSize(700, 600);
        talabiaFrame.setLocationRelativeTo(null);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 7));
        for (int r = 1; r <= 6; r++) {
            for (int c = 1; c <= 7; c++) {
                JButton chessButton = new JButton("r" + r + "c" + c);
                chessButton.setText(null);
                if (chessButtonsIndex % 2 == 0) {
                    chessButton.setBackground(Color.WHITE);
                } else {
                    chessButton.setBackground(Color.BLACK);
                }
                chessButtons[chessButtonsIndex] = chessButton;
                chessButtonsIndex++;
                boardPanel.add(chessButton);
            }
        }
        talabiaFrame.add(boardPanel);
        talabiaFrame.setVisible(true);
    }
}
