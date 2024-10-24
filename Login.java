package Grocery.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    JTextField textField1;
    JPasswordField passwordField1;
    JButton b1, b2;

    Login() {
        JLabel label1 = new JLabel("Username");
        label1.setBounds(40, 20, 100, 30);
        label1.setFont(new Font("Tahoma", Font.BOLD, 16));
        label1.setForeground(Color.WHITE);
        add(label1);

        JLabel label2 = new JLabel("Password");
        label2.setBounds(40, 70, 100, 30);
        label2.setFont(new Font("Tahoma", Font.BOLD, 16));
        label2.setForeground(Color.WHITE);
        add(label2);

        textField1 = new JTextField();
        textField1.setBounds(150, 20, 150, 30);
        textField1.setForeground(Color.BLACK);
        textField1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField1.setBackground(Color.WHITE);
        add(textField1);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(150, 70, 150, 30);
        passwordField1.setForeground(Color.BLACK);
        passwordField1.setBackground(Color.WHITE);
        add(passwordField1);

        b1 = new JButton("Login");
        b1.setBounds(40, 140, 120, 30);
        b1.setFont(new Font("serif", Font.BOLD, 15));
        b1.setBackground(Color.WHITE);
        b1.setForeground(Color.BLACK);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(180, 140, 120, 30);
        b2.setFont(new Font("serif", Font.BOLD, 15));
        b2.setBackground(Color.white);
        b2.setForeground(Color.BLACK);
        b2.addActionListener(this);
        add(b2);

        ImageIcon imageIcon = new ImageIcon((ClassLoader.getSystemResource("icon/Mart.png")));
        Image i1 = imageIcon.getImage().getScaledInstance(255, 687, Image.SCALE_DEFAULT);
        JLabel label = new JLabel(imageIcon);
        label.setBounds(0, 0, 735, 470);
        add(label);


        setUndecorated(true);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        setLocation(470, 270);
        setSize(600, 385);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                Con con = new Con();  // Create connection instance
                String user = textField1.getText();
                String pass = new String(passwordField1.getPassword());

                String q = "SELECT * FROM login WHERE username = ? AND password = ?";
                try (PreparedStatement pstmt = con.getConnection().prepareStatement(q)) {
                    pstmt.setString(1, user);
                    pstmt.setString(2, pass);
                    ResultSet resultSet = pstmt.executeQuery();

                    if (resultSet.next()) {
                        // Successful login, hide login frame and show main frame
                        this.setVisible(false);
                        new MainFrame().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    con.close();  // Close the connection
                }
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else if (e.getSource() == b2) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
