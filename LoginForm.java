import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Railway System Management - Login");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(154, 156, 143));

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailField = new JTextField(20);
        emailField.setFont(fieldFont);
        emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordField = new JPasswordField(20);
        passwordField.setFont(fieldFont);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(buttonFont);
        registerButton.setBackground(new Color(255, 255, 255));
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Dimension fieldSize = new Dimension(200, 30);
        emailField.setMaximumSize(fieldSize);
        passwordField.setMaximumSize(fieldSize);

        Dimension buttonSize = new Dimension(80, 30);
        loginButton.setMaximumSize(buttonSize);
        registerButton.setMaximumSize(buttonSize);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(20));
        add(emailLabel);
        add(emailField);
        add(Box.createVerticalStrut(10));
        add(passwordLabel);
        add(passwordField);
        add(Box.createVerticalStrut(10));
        add(loginButton);
        add(Box.createVerticalStrut(10));
        add(registerButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = String.valueOf(passwordField.getPassword());
            int userId = authenticateUser(email, password);
            if (userId != -1) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                new Dashboard(userId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> new RegisterForm());

        setVisible(true);
    }

    private int authenticateUser(String email, String password) {
        int userId = -1;
        try {
            Connection con = DatabaseConnection.getConnection();
            String query = "SELECT user_id FROM Users WHERE email = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
