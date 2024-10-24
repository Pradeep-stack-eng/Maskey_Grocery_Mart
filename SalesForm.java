package Grocery.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesForm extends JFrame {
    private JComboBox<String> productComboBox;
    private JTextField quantityField;
    private JLabel totalLabel;
    private JButton processSaleButton;
    private JButton generateReceiptButton;

    private Connection conn;
    private String lastProductName;
    private int lastQuantity;
    private double lastPrice;
    private double lastTotal;

    public SalesForm() {
        conn = Con.connect();
        setTitle("Process Sale");
        setSize(400, 250); // Adjusted size to accommodate new button
        setBackground(Color.darkGray);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI(); // Initialize UI components
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Product:"));
        productComboBox = new JComboBox<>();
        loadProducts();
        panel.add(productComboBox);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Total Price:"));
        totalLabel = new JLabel("0.00");
        panel.add(totalLabel);

        processSaleButton = new JButton("Process Sale");
        processSaleButton.addActionListener(new ProcessSaleActionListener());
        panel.add(processSaleButton);

        generateReceiptButton = new JButton("Generate Receipt");
        generateReceiptButton.setEnabled(false); // Initially disabled
        generateReceiptButton.addActionListener(new GenerateReceiptActionListener());
        panel.add(generateReceiptButton);

        add(panel);
        setVisible(true);
    }

    private void loadProducts() {
        String sql = "SELECT name FROM Products";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                productComboBox.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }

    private class ProcessSaleActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String productName = (String) productComboBox.getSelectedItem();
            if (productName == null) {
                JOptionPane.showMessageDialog(SalesForm.this, "Please select a product.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SalesForm.this, "Invalid quantity entered.");
                return;
            }

            try {
                // Retrieve the product's current details
                String sql = "SELECT price, quantity FROM Products WHERE name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, productName);
                    try (ResultSet rs = pstmt.executeQuery()) {

                        if (rs.next()) {
                            double price = rs.getDouble("price");
                            int currentQuantity = rs.getInt("quantity");

                            if (quantity <= currentQuantity) {
                                double total = price * quantity;
                                totalLabel.setText(String.format("%.2f", total));

                                // Update the product's quantity in the database
                                String updateSql = "UPDATE Products SET quantity = ? WHERE name = ?";
                                try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                                    updatePstmt.setInt(1, currentQuantity - quantity);
                                    updatePstmt.setString(2, productName);
                                    updatePstmt.executeUpdate();
                                }

                                JOptionPane.showMessageDialog(SalesForm.this, "Sale processed successfully!");

                                // Store details for receipt
                                lastProductName = productName;
                                lastQuantity = quantity;
                                lastPrice = price;
                                lastTotal = total;

                                // Enable the generate receipt button
                                generateReceiptButton.setEnabled(true);
                            } else {
                                JOptionPane.showMessageDialog(SalesForm.this, "Insufficient stock for this product.");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SalesForm.this, "Error processing sale: " + ex.getMessage());
            }
        }
    }

    private class GenerateReceiptActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (lastProductName != null && lastQuantity > 0) {
                new Receipt(lastProductName, lastQuantity, lastPrice, lastTotal);
            } else {
                JOptionPane.showMessageDialog(SalesForm.this, "No sale has been processed yet.");
            }
        }
    }
}
