import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterForm extends JFrame {
    JTextField nameField, emailField, phoneField;
    JPasswordField passwordField;

    public RegisterForm() {
        setTitle("Railway System Management - Register");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(154, 156, 143));

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        nameField = new JTextField(20);
        nameField.setFont(fieldFont);
        nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailField = new JTextField(20);
        emailField.setFont(fieldFont);
        emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        phoneField = new JTextField(20);
        phoneField.setFont(fieldFont);
        phoneField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordField = new JPasswordField(20);
        passwordField.setFont(fieldFont);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(buttonFont);
        registerButton.setBackground(new Color(255, 255, 255));
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Dimension fieldSize = new Dimension(200, 30);
        nameField.setMaximumSize(fieldSize);
        emailField.setMaximumSize(fieldSize);
        phoneField.setMaximumSize(fieldSize);
        passwordField.setMaximumSize(fieldSize);

        Dimension buttonSize = new Dimension(80, 30);
        registerButton.setMaximumSize(buttonSize);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(20));
        add(nameLabel);
        add(nameField);
        add(Box.createVerticalStrut(10));
        add(emailLabel);
        add(emailField);
        add(Box.createVerticalStrut(10));
        add(phoneLabel);
        add(phoneField);
        add(Box.createVerticalStrut(10));
        add(passwordLabel);
        add(passwordField);
        add(Box.createVerticalStrut(10));
        add(registerButton);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = String.valueOf(passwordField.getPassword());
            if (registerUser(name, email, phone, password)) {
                JOptionPane.showMessageDialog(null, "Registration Successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Registration Failed!");
            }
        });

        setVisible(true);
    }

    private boolean registerUser(String name, String email, String phone, String password) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String query = "INSERT INTO Users (name, email, phone, password) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, password);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new RegisterForm();
    }
}
