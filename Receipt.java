package Grocery.Management.System;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Receipt extends JFrame {
    private JTextArea receiptArea;

    public Receipt(String productName, int quantity, double price, double total) {
        setTitle("Receipt");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI(productName, quantity, price, total);
    }

    private void initUI(String productName, int quantity, double price, double total) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Calculate tax and total with tax
        double taxRate = 0.13; // 13%
        double tax = total * taxRate;
        double totalWithTax = total + tax;

        // Generate the receipt content
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("  Grocery Store Receipt\n");
        receiptContent.append("  ---------------------\n");
        receiptContent.append("  Date: ").append(new Date()).append("\n\n");
        receiptContent.append("  Product: ").append(productName).append("\n");
        receiptContent.append("  Quantity: ").append(quantity).append("\n");
        receiptContent.append("  Unit Price: NPR ").append(String.format("%.2f", price)).append("\n");
        receiptContent.append("  ---------------------\n");
        receiptContent.append("  Subtotal: NPR ").append(String.format("%.2f", total)).append("\n");
        receiptContent.append("  Tax (13%): NPR ").append(String.format("%.2f", tax)).append("\n");
        receiptContent.append("  ---------------------\n");
        receiptContent.append("  Total: NPR ").append(String.format("%.2f", totalWithTax)).append("\n\n");
        receiptContent.append("  Thank you for shopping!\n");

        receiptArea.setText(receiptContent.toString());

        panel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
