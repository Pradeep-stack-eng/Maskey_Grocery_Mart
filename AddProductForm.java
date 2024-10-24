package Grocery.Management.System;


import javax.swing.*;
import java.awt.*;

public class AddProductForm extends JFrame {
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField categoryField;

    private ProductController productController;

    public AddProductForm() {
        productController = new ProductController();
        setTitle("Add Product");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Quantity(kg):"));
        quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        panel.add(categoryField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addProduct());
        panel.add(addButton);

        add(panel);
        setVisible(true);
    }

    private void addProduct() {
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        String category = categoryField.getText();

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);

        productController.addProduct(product);
        JOptionPane.showMessageDialog(this, "Product added successfully!");
        dispose();
   }
}
