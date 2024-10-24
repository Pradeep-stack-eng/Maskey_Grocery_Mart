package Grocery.Management.System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductController {
    private Connection conn;
    private Con dbCon;

    public ProductController() {
        dbCon = new Con(); // Initialize the Con class
        this.conn = dbCon.getConnection(); // Get the Connection object from Con
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO Products(name, price, quantity, category) VALUES(?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getCategory());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    // Update, delete, and search methods here

    public void close() {
        if (dbCon != null) {
            dbCon.close(); // Close the database connection
        }
    }

    public static void main(String[] args) {
        new ProductController();
    }
}
