package Grocery.Management.System;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Grocery Management System");
        setSize(750, 700);
        setBackground(Color.green);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon((ClassLoader.getSystemResource("icon/Grocery.png")));
        Image i1 = imageIcon.getImage().getScaledInstance(255, 687, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(imageIcon);
        label.setBounds(0, 0, 735, 480);
        add(label);
        initUI();
    }

    private void initUI() {
        JMenuBar menuBar = new JMenuBar();

        JMenu productMenu = new JMenu("Product Management");
        JMenuItem addProduct = new JMenuItem("Add Product");
        productMenu.add(addProduct);

        JMenu salesMenu = new JMenu("Sales Management");
        JMenuItem processSale = new JMenuItem("Process Sale");
        salesMenu.add(processSale);

        menuBar.add(productMenu);
        menuBar.add(salesMenu);

        setJMenuBar(menuBar);

        // Add action listeners for menu items
        addProduct.addActionListener(e -> new AddProductForm());
        processSale.addActionListener(e -> new SalesForm());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

