// Exercise 2 OOAD
// TAN HONG HAN 1211105217
// CHAI DI SHENG 1211101961
// LAM ZI FOONG 1221303175
// TAN YI KAI 1211201274
// LIM KIAN ZEE 1211200291
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Draft
{
    private JFrame f;
    private JPanel mainPanel;
    private JPanel itemPanel;
    private JPanel cartPanel;
    private JPanel buttonPanel;
    // private JButton itemOne;
    // private JButton itemTwo;
    // private JButton itemThree;
    // private JButton itemFour;
    private ArrayList<JButton> itemLists = new ArrayList<JButton>();
    
    private JPanel setItemPanel(){
        itemPanel = new JPanel();
        itemPanel.setBackground(Color.white);
        itemPanel.setLayout(new BoxLayout(itemPanel,BoxLayout.Y_AXIS));
        
        JButton itemOne = new JButton("5.00");
        itemOne.setText("Item 1 (RM5.00)");
        itemLists.add(itemOne);
        
        JButton itemTwo = new JButton("5.00");
        itemTwo.setText("Item 2 (RM10.00)");
        itemLists.add(itemTwo);
        
        JButton itemThree = new JButton("7.50");
        itemThree.setText("Item 3 (RM7.50)");
        itemLists.add(itemThree);
        
        JButton itemFour = new JButton("3.25");
        itemFour.setText("Item 4 (RM3.25)");
        itemLists.add(itemFour);
        
        for(JButton item : itemLists) {
            item.setBorder(null);
            item.setOpaque(false);
            item.setContentAreaFilled(false);
            item.setBorderPainted(false);
            itemPanel.add(item);
        }
        
        return itemPanel;
    }
    
    private JPanel setCartPanel(){
        cartPanel = new JPanel();
        
        return cartPanel;
    }
    
    private JPanel setButtonPanel(){
        buttonPanel = new JPanel();
        
        return buttonPanel;
    }
    
    // private void initPanels() {
        // mainPanel = new JPanel();
        // mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        
        // setItemPanel();
        // setCartPanel();
        // setButtonPanel();
        // mainPanel.add(itemPanel);
        // mainPanel.add(cartPanel);
        // mainPanel.add(buttonPanel);
    // }
    
    private void initApp(String title) {
        f = new JFrame(title);
        f.setVisible(true);
        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS));
        
        f.add(setItemPanel());
        f.add(setCartPanel());
        f.add(setButtonPanel());
        
    }
    
    public Draft(){
        initApp("Elewsmart App");
    }
    
    public static void main(String[] args) {
        new Draft();
    }
}
