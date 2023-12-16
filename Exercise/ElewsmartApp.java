// Exercise 2 OOAD
// TAN HONG HAN 1211105217
// LIM KIAN ZEE 1211200291
// CHAI DI SHENG 1211101961
// LAM ZI FOONG 1221303175
// TAN YI KAI 1211201274

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ElewsmartApp extends JFrame {

    private DefaultListModel<String> listModel;
    private JList<String> itemList;
    private JTextArea cartTextArea;
    private JLabel totalLabel;
    private double itemPrice[] = {5,10,7.5,3.25};
    private double totalPrice = 0;
    private ArrayList<Double> inCartItem = new ArrayList<>();
    private ArrayList<String> inCartItemString = new ArrayList<>();

    public ElewsmartApp() {
        super("Elewsmart App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500,500));

        // Initialize components
        listModel = new DefaultListModel<>();
        cartTextArea = new JTextArea();
        cartTextArea.setEditable(false);

        // Layout setup
        setLayout(new BorderLayout());

        
        JPanel leftPanel = createLeftPanel();
        JPanel middlePanel = createMiddlePanel();
        JPanel buttonPanel = createButtonPanel();
        add(leftPanel, BorderLayout.WEST);
        add(middlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new AddToCartListener());

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveItemListener());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearCartListener());

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(new PayListener());

        buttonPanel.add(addToCartButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(payButton);

        return buttonPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new FlowLayout());
        String list[] = { "Item 1 (RM5.00)", "Item 2 (RM10.00)", "Item 3 (RM7.50)", "Item 4 (RM3.25)" };
        itemList = new JList<>(list);
        leftPanel.add(itemList);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return leftPanel;
    }

    private JPanel createMiddlePanel() {
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BorderLayout());
    
        JPanel northLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel text = new JLabel("Shopping Cart");
        northLabel.add(text);
        midPanel.add(northLabel, BorderLayout.NORTH);
    
        midPanel.add(new JScrollPane(cartTextArea), BorderLayout.CENTER);
    
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalLabel = new JLabel("Total: RM0.00");
        southPanel.add(totalLabel);
        midPanel.add(southPanel, BorderLayout.SOUTH);

        return midPanel;
    }

    private class AddToCartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedItem = itemList.getSelectedValue();
            int selectedIndex = itemList.getSelectedIndex();
            totalPrice += itemPrice[selectedIndex];
            DecimalFormat dc = new DecimalFormat("0.00");
            String formattedPrice = dc.format(totalPrice);

            if (selectedItem != null) {
                String text = selectedItem + "\n";
                cartTextArea.append(text);
                inCartItemString.add(text);
                totalLabel.setText("Total: RM" + formattedPrice);
                inCartItem.add(itemPrice[selectedIndex]);
            }
        }
    }

    private class RemoveItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!inCartItem.isEmpty()) {
                double selectedItemPrice = inCartItem.get(inCartItem.size()-1); 
                totalPrice -= selectedItemPrice;
                DecimalFormat dc = new DecimalFormat("0.00");
                String formattedPrice = dc.format(totalPrice);
                
                totalLabel.setText("Total: RM" + formattedPrice);
                
                inCartItem.remove(inCartItem.size()-1);
                inCartItemString.remove(inCartItemString.size()-1);

                cartTextArea.setText("");
                for (String item : inCartItemString) {
                    cartTextArea.append(item);
                }
                
            }else{
                cartTextArea.setText("");
            }
  
        }
    }

    private class ClearCartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cartTextArea.setText("");
            totalLabel.setText("Total: RM0.00");
            totalPrice = 0;
        }
    }

    private class PayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DecimalFormat dc = new DecimalFormat("0.00");
            String formattedPrice = dc.format(totalPrice);
            String message = "Payment processed successfully!\nTotal Amount: RM " + formattedPrice;
            JOptionPane.showMessageDialog(ElewsmartApp.this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElewsmartApp().setVisible(true));
    }
}
